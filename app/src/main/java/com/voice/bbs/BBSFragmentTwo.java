package com.voice.bbs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voice.R;

/**
 * Created by tangyubao on 2017/10/11.
 */

public class BBSFragmentTwo extends Fragment {

    private Context mContext;
    private View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.bbs_list_2,null);
        return v;
    }
}
