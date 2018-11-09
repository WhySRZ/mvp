package com.example.weioule.mvpdemo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;

import com.example.weioule.mvpdemo.R;
import com.example.weioule.mvpdemo.base.BaseActivity;
import com.example.weioule.mvpdemo.base.BaseAttribute;
import com.example.weioule.mvpdemo.databinding.ActivityWelcomeBinding;
import com.example.weioule.mvpdemo.presenter.WelcomePresenter;
import com.example.weioule.mvpdemo.view.WelcomeMvpView;
import com.example.weioule.mvpdemo.widget.ProgressView;


/**
 * Author by weioule.
 * Date on 2018/10/29.
 */
public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding,WelcomePresenter> implements WelcomeMvpView, View.OnClickListener {


    private int AD_DISPLAY_TIME = 1750;
    private static Handler handler = new Handler();
    private Runnable mRunnable;

    @Override
    protected void onInitAttribute(BaseAttribute attribute) {
            attribute.mLayoutId = R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        mViewBinding.bannerImg.setOnClickListener(this);
        mViewBinding.rlEnter.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //模拟网络请求
        mViewBinding.bannerImg.postDelayed(new Runnable() {
            @Override
            public void run() {
               mPresenter.getAdInfo();
            }
        }, 1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                forwardAndFinish(HomeActivity.class);
            }
        };
        handler.postDelayed(mRunnable, AD_DISPLAY_TIME);
    }

    @Override
    public void showAdFormCache(Bitmap bitmap, int duration) {
        if (isFinishing() || isDestroyed()) return;
        handler.removeCallbacks(mRunnable);
        mViewBinding.bannerImg.setImageBitmap(bitmap);
        mViewBinding.rlEnter.setVisibility(View.VISIBLE);
        mViewBinding.progress.startDownTime(duration, new ProgressView.OnFinishListener() {
            @Override
            public void onFinish() {
                forwardAndFinish(HomeActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mViewBinding.progress.stopDownTime();
        switch (v.getId()) {
            case R.id.banner_img:
                forward(HomeActivity.class);
                mPresenter.jumpToAd();
                finish();
                break;
            case R.id.rl_enter:
                forwardAndFinish(HomeActivity.class);
                break;
        }
    }

    @Override
    public Context getContexts() {
        return this;
    }

    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter();
    }


}
