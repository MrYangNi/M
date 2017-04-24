package com.project.yang.m.login;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.project.yang.m.R;
import com.project.yang.m.databinding.DialogGenderSelectorViewBinding;
import com.project.yang.m.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiYang on 2017/4/24.
 */

public class GenderSelectorDialog extends Dialog implements View.OnClickListener {
    private DialogGenderSelectorViewBinding binding = null;
    private String gender = "男";
    private OnGenderChangedListener listener = null;

    public GenderSelectorDialog(Context context) {
        super(context);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_gender_selector_view, null, false);
        setContentView(this.binding.getRoot());
        initGenderSelectorWheelView();
        initView();
    }

    private void initGenderSelectorWheelView() {
        List<String> genderList = new ArrayList<String>(){{
            add("男");
            add("女");
        }};
        this.binding.genderSelectorWheelView.setItems(genderList);
        this.binding.genderSelectorWheelView.setOffset(1);
        this.binding.genderSelectorWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                gender = item;
            }
        });
    }

    private void initView() {
        this.binding.txtCancel.setOnClickListener(this);
        this.binding.txtConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_cancel:
                dismiss();
                break;
            case R.id.txt_confirm:
                listener.onGenderChanged(this.gender);
                dismiss();
                break;
            default:break;
        }
    }

    public interface OnGenderChangedListener {
        void onGenderChanged(String gender);
    }

    public void registerListener(OnGenderChangedListener listener) {
        this.listener = listener;
    }

}
