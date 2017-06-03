package com.voice.bbs;

import android.support.v4.app.Fragment;

/**
 * Created by EXcalibur on 2017/5/20.
 */

public class BBSActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {

        return BBSFragment.newInstance();
    }
}



