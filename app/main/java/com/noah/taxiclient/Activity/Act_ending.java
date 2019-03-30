package com.noah.taxiclient.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.noah.taxiclient.R;

/**
 * Created by Noah on 2017-09-30.
 */

public class Act_ending extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finish();
    }
}
