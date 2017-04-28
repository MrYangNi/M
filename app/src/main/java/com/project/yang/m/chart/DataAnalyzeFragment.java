package com.project.yang.m.chart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.yang.m.R;
import com.project.yang.m.apriori.Apriori;
import com.project.yang.m.databinding.FragmentDataAnalyzeViewBinding;
import com.project.yang.m.db.DBManager;
import com.project.yang.m.db.beans.AsocciationRulesDataBeans;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiYang on 2017/4/28.
 */

public class DataAnalyzeFragment extends Fragment implements View.OnClickListener {
    private FragmentDataAnalyzeViewBinding binding = null;
    private Apriori apriori = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_data_analyze_view, container, false);
        fakeData();
        initView();
        return this.binding.getRoot();
    }

    private void fakeData() {
        AsocciationRulesDataBeans bean1 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 07:13:52"), "thirdMess", "unusedApp");
        AsocciationRulesDataBeans bean2 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 08:13:52"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean3 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 09:13:49"), "library", "wangyiyunyinyue");
        AsocciationRulesDataBeans bean4 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 12:13:49"), "mess", "unusedApp");
        AsocciationRulesDataBeans bean5 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 12:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean6 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 08:13:52"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean7 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 08:13:52"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean8 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 15:13:49"), "BasketballCourt", "unusedApp");
        AsocciationRulesDataBeans bean9 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 15:13:49"), "BasketballCourt", "unusedApp");
        AsocciationRulesDataBeans bean10 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean11 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean12 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean13 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean14 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "weixin");
        AsocciationRulesDataBeans bean15 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean16 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean17 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean18 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean19 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "weixin");
        AsocciationRulesDataBeans bean20 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean21 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "weixin");
        AsocciationRulesDataBeans bean22 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "thirdMess", "zhifubao");
        AsocciationRulesDataBeans bean23 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean24 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean25 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        AsocciationRulesDataBeans bean26 = new AsocciationRulesDataBeans(null, Utils.transformDate("2017-4-1 19:13:49"), "15dorm", "meituan");
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean1);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean2);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean3);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean4);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean5);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean6);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean7);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean8);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean9);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean10);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean11);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean12);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean13);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean14);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean15);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean16);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean17);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean18);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean19);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean20);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean21);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean22);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean23);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean24);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean25);
        DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().insert(bean26);
    }

    private void initView() {
        this.binding.btnAnalyze.setOnClickListener(this);
    }

    private void setParams() {
        if (!TextUtils.isEmpty(this.binding.etSetSupport.getText()) && !TextUtils.isEmpty(this.binding.etSetConfidence.getText())) {
            this.apriori.setMinSupport(Double.valueOf(this.binding.etSetSupport.getText().toString()));
            this.apriori.setMinConfidence(Double.valueOf(this.binding.etSetConfidence.getText().toString()));
        }
    }

    private List<List<String>> getData() {
        List<List<String>> datas = new ArrayList<>();
        QueryBuilder qb = DBManager.getDbManager().getDaoSession().getAsocciationRulesDataBeansDao().queryBuilder();
        List<AsocciationRulesDataBeans> originalData = qb.list();
        if (originalData.size() == 0) {
            ToastUtil.showToast("您还没有数据！");
            return datas;
        }
        for (AsocciationRulesDataBeans beans : originalData) {
            List<String> itemData = new ArrayList<>();
            itemData.add(Utils.judgeTimePeriod(beans.getTime()));
            itemData.add(beans.getLocation());
            itemData.add(beans.getAppName());
            datas.add(itemData);
        }
        return datas;
    }

    private void relevanceAnalyze() {
        this.apriori = new Apriori();
        setParams();
        this.apriori.setRecord(getData());
        System.out.println("读取数据集record成功===================================");
        this.apriori.calculate();
        System.out.println("频繁模式挖掘完毕。\n\n\n\n\n进行关联度挖掘，最小支持度百分比为："+ apriori.getMinSupport()+"  最小置信度为："+ apriori.getMinConfidence());
        this.apriori.associationRulesMining();
        this.binding.txtShowInfo.setText("关联度挖掘完毕\n" + "最小支持度百分比为：" + apriori.getMinSupport() + "  最小置信度为：" + apriori.getMinConfidence() + "\n");
        for (int i=0;i<this.apriori.getItem1().size();i++) {
            this.binding.txtShowInfo.append(this.apriori.getItem1().get(i).toString() + "=>" + this.apriori.getItem2().get(i).toString() + "\n");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_analyze:
                relevanceAnalyze();
                break;
            default:break;
        }
    }
}
