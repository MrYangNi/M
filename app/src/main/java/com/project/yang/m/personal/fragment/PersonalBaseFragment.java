package com.project.yang.m.personal.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.project.yang.m.R;

/**
 * Created by NiYang on 2017/4/5.
 */

public class PersonalBaseFragment extends Fragment {
    protected void returnLastStep() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.push_in, R.anim.push_out);
        transaction.remove(this);
        transaction.commit();
    }
}
