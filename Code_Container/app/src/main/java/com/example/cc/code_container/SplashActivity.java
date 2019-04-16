package com.example.cc.code_container;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //设置隐藏标题，全屏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //设置此界面为
            // 竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_splash);
            init();
        }

        private void init() {
            //利用timer让此界面延迟2.5秒后跳转，timer有一个线程，该线程不断执行task
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    //跳转后关闭当前欢迎页面
                    SplashActivity.this.finish();
                }
            };
            //调度执行timerTask，第二个参数传入延迟时间（毫秒）
            timer.schedule(timerTask,2500);
        }
    }