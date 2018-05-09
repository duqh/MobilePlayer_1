package com.atguigu.mobileplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.base.BasePager;
import com.atguigu.mobileplayer.pager.AudioPager;
import com.atguigu.mobileplayer.pager.NetAudioPager;
import com.atguigu.mobileplayer.pager.NetVideoPager;
import com.atguigu.mobileplayer.pager.VideoPager;
import com.atguigu.mobileplayer.utils.LogUtil;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @data ${TODO}
 * @updataAuthor $Authoe$
 * @updataDes ${TODO}
 */

public class MainActivity extends FragmentActivity {


    private RadioGroup rg_bottom_tag;

    /**
     * 页面的集合
     */
    private ArrayList<BasePager> basePagers;

    /**
     * 选中的位置
     */
    private int position;
    public static View pFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);


        basePagers = new ArrayList<>();
        basePagers.add(new VideoPager(this));//添加本地视频页面-0
        basePagers.add(new AudioPager(this));//添加本地音乐页面-1
        basePagers.add(new NetVideoPager(this));//添加网络视频页面-2
        basePagers.add(new NetAudioPager(this));//添加网络音频页面-3


        //设置RadioGroup的监听
        rg_bottom_tag.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_bottom_tag.check(R.id.rb_video);//默认选中首页

    }

     class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                default:
                    position = 0;
                    break;
                case R.id.rb_audio://音频
                    position = 1;
                    break;
                case R.id.rb_net_video://网络视频
                    position = 2;
                    break;
                case R.id.rb_netaudio://网络音频
                    position = 3;
                    break;
            }

            setFragment();


        }
    }

    /**
     * 把页面添加到Fragment中
     */
    private void setFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换

          BasePager basePager = getBasePager();

              if(basePager != null){

                  pFragment = basePager.rootView;

                 //各个页面的视图
               Fragment fmt = new getFragmentB();

                  ft.replace(R.id.fl_main_content, fmt);

                        }
                      LogUtil.e("各个页面的视图。。。");


        //4.提交事务
        ft.commit();

    }

   public static class getFragmentB extends Fragment{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return pFragment;
        }
    }

    /**
     * 根据位置得到对应的页面
     * @return
     */
    private BasePager getBasePager() {
        BasePager basePager = basePagers.get(position);
        if(basePager != null&&!basePager.isInitData){
            basePager.initData();//联网请求或者绑定数据
            basePager.isInitData = true;
        }
        return basePager;
    }
}
