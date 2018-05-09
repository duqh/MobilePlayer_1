package com.atguigu.mobileplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.domain.MediaItem;
import com.atguigu.mobileplayer.utils.Utils;

import java.util.ArrayList;

import static android.media.ThumbnailUtils.OPTIONS_RECYCLE_INPUT;

/**
 * @author Administrator
 * @version $Rev$
 * @data ${TODO}
 * @updataAuthor $Authoe$
 * @updataDes ${TODO}
 */

public class VideoPagerAdapter extends BaseAdapter {

    private  Context context;
    private final ArrayList<MediaItem> mediaItems;
    private Utils utils;

    public VideoPagerAdapter(Context context,ArrayList<MediaItem> mediaItems){
        this.context = context;
        this.mediaItems = mediaItems;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.item_video_pager,null);
            viewHoder = new ViewHoder();
            viewHoder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHoder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHoder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHoder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);

            convertView.setTag(viewHoder);
        }else{
            viewHoder = (ViewHoder) convertView.getTag();
        }

        //根据position得到列表中对应位置的数据
        MediaItem mediaItem = mediaItems.get(position);
        viewHoder.tv_name.setText(mediaItem.getName());
        viewHoder.tv_size.setText(Formatter.formatFileSize(context, mediaItem.getSize()));
        viewHoder.tv_time.setText(utils.stringForTime((int) mediaItem.getDuration()));
        String path_tag = mediaItem.getData();
            if(path_tag!=null){
//                viewHoder.iv_icon.setTag(path_tag);
                new ImageLoader().showImageByThread(viewHoder.iv_icon, path_tag);
               }

        return convertView;
    }




    /**
     * 异步加载图片
     */
    class ImageLoader {
        private ImageView imageView;
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

             //TODO 这里可以通过判断是否已经有加载了图片,如果加载了就不用在加载了
             if(msg.obj!=null){
                 imageView.setImageBitmap((Bitmap) msg.obj);
             }

            }
        };


        /**
         * 使用多线程实现异步加载
         * @param imageView
         * @param mp
         */
        void showImageByThread(ImageView imageView, final String mp) {
            this.imageView = imageView;

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Message message = Message.obtain();
                    Bitmap bitmap = getBitmap(mp);
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
            }.start();
        }

        Bitmap getBitmap(String mp3Info) {
            return getVideoBitmap(mp3Info);
        }
    }

    /*
    * 缩略图
    * */
    private Bitmap getVideoBitmap(String path) {
        Log.e("Icon", "path:" + path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap frameAtTime = retriever.getFrameAtTime();

            return ThumbnailUtils.extractThumbnail(frameAtTime,60,60,OPTIONS_RECYCLE_INPUT);
        } catch (Exception e) {
            return null;
        } finally {
            retriever.release();
        }

    }




    static class ViewHoder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}
