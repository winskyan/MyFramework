package com.my.myframework.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.my.library_base.constants.IntervalTimeConstants;
import com.my.library_base.constants.MMKVConstants;
import com.my.library_base.utils.MMKVUtils;
import com.my.myframework.MainActivity;
import com.my.myframework.R;

import butterknife.BindView;


public class SplashActivity extends Activity {

    @BindView(R.id.splash_image)
    ImageView mSplashImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int intervalTime = MMKVUtils.getInstance().getIntValue(MMKVConstants.SPLASH_INTERVAL_TIME);
        if (-1 == intervalTime) {
            intervalTime = IntervalTimeConstants.SPLASH_INTERVAL_TIME;
        }
        mHandler.sendEmptyMessageDelayed(0, intervalTime);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
