package com.example.lenovo.wenews;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity_ extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private View view_status;
    private ImageView iv_title_news;
    private ImageView iv_title_movie;
    private ImageView iv_title_video;
    private ViewPager vp_content;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#ffce3d3a"));
        }

        initView();
        initContentFragment();
    }

    private void initView(){
        view_status = (View) findViewById(R.id.view_status);
        iv_title_news = (ImageView) findViewById(R.id.iv_title_news);
        iv_title_movie = (ImageView) findViewById(R.id.iv_title_movie);
        iv_title_video = (ImageView) findViewById(R.id.iv_title_video);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        toolbar = (Toolbar) findViewById(R.id.toolbars);

        iv_title_video.setOnClickListener(this);
        iv_title_movie.setOnClickListener(this);
        iv_title_news.setOnClickListener(this);
    }

    private void initContentFragment(){
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new FgNewsFragment());
        mFragmentList.add(new FgMovieFragment());
        mFragmentList.add(new FgVideoFragment());
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),
                mFragmentList);
        vp_content.setAdapter(adapter);
        vp_content.setOffscreenPageLimit(2);
        vp_content.addOnPageChangeListener(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        setCurrentItem(0);
    }
    //子页面
    private void setCurrentItem(int i){
        vp_content.setCurrentItem(i);
        iv_title_movie.setSelected(false);
        iv_title_news.setSelected(false);
        iv_title_video.setSelected(false);
        switch (i) {
            case 0:
                iv_title_news.setSelected(true);
                break;
            case 1:
                iv_title_movie.setSelected(true);
            case 2:
                iv_title_video.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_news:
                if (vp_content.getCurrentItem() !=0){
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_title_movie:
                if (vp_content.getCurrentItem() !=1){
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_title_video:
                if (vp_content.getCurrentItem() !=2){
                    setCurrentItem(2);
                }
                break;
        }
    }
}
