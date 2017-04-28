package com.project.yang.m.chart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.yang.m.R;
import com.project.yang.m.common.BaseActivity;
import com.project.yang.m.databinding.ActivityChartBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiYang on 2017/3/30.
 */

public class ChartActivity extends BaseActivity {
    private ActivityChartBinding binding = null;
    private MyViewPagerAdapter adapter = null;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<String>(){{
        add("图标");
        add("数据分析");
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("图表分析");
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_chart, null, false);
        setContentView(this.binding.getRoot());
        initViewPager();
    }

    private void initViewPager() {
        this.mFragments.add(new ChartFragment());
        this.mFragments.add(new DataAnalyzeFragment());
        this.adapter = new MyViewPagerAdapter(getSupportFragmentManager(), this.mFragments, this.mTitles);
        this.binding.viewPager.setAdapter(this.adapter);
        this.binding.viewPager.setCurrentItem(0);
        this.binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        this.binding.tabLayout.setupWithViewPager(this.binding.viewPager);
    }

    private static class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments,List<String> titles) {
            super(fragmentManager);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            this.fragments.get(position).onDestroy();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.titles.get(position);
        }
    }

}
