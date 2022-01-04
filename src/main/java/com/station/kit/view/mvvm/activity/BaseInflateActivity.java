package com.station.kit.view.mvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;

import com.station.kit.view.mvvm.ViewDataBindingHelper;
import com.station.kit.view.mvvm.ViewModelHelper;


/**
 * author: Sam Leung
 * date:  2021/10/28
 */
public abstract class BaseInflateActivity<VM extends AndroidViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {
    protected VDB mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ViewDataBindingHelper.inflateVDB(this,this.getClass());
        setContentView(mBinding.getRoot());
        mBinding.setLifecycleOwner(this);
        mViewModel= ViewModelHelper.createAndroidViewModel(this,this.getClass());
    }

    public VDB getBinding() {
        return mBinding;
    }

    public VM getViewModel() {
        return mViewModel;
    }
}
