package com.warehouse.base.mvvm.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.warehouse.base.R;
import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.loadsir.CustomCallback;
import com.warehouse.base.loadsir.EmptyCallback;
import com.warehouse.base.loadsir.ErrorCallback;
import com.warehouse.base.loadsir.LoadingCallback;
import com.warehouse.base.loadsir.TimeoutCallback;
import com.warehouse.base.mvvm.model.SuperBaseMvvmViewModel;
import com.warehouse.base.mvvm.viewmodel.ViewStatus;
import com.warehouse.base.utils.ToastUtil;

import java.util.List;


public abstract class BaseMvvmActivity<VIEW extends ViewDataBinding, VIEWMODEL extends SuperBaseMvvmViewModel, DATA> extends BaseActivity implements Observer {
    protected VIEWMODEL viewModel;
    protected VIEW viewDataBinding;
    public abstract @LayoutRes
    int getLayoutId();
    public abstract VIEWMODEL getViewModel();
    private LoadService mLoadService;

    protected abstract View getLoadSirView();
    public abstract void onNetworkResponded(List<DATA> dataList, boolean isDataUpdated);
    public abstract void onNetworkObjectResponded(DATA data, boolean isDataUpdated);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initView
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .build();
        mLoadService = loadSir.register(getLoadSirView(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewModel.refresh();
            }
        });
        //initModel
        viewModel = getViewModel();
        getLifecycle().addObserver(viewModel);
        // viewModel.dataList.observe(this, this);
        viewModel.viewStatusLiveData.observe(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:
                    ToastUtil.show(getString(R.string.no_more_data));
                    break;
                case REFRESH_ERROR:
                    //刷新失败
//                    if (((ObservableArrayList) viewModel.dataList.getValue()).size() == 0) {
                    mLoadService.showCallback(ErrorCallback.class);
//                    } else {
//                        ToastUtil.show(viewModel.errorMessage.getValue().toString());
//                    }
                    break;
                case LOAD_MORE_FAILED:
                    ToastUtil.show(viewModel.errorMessage.getValue().toString());
                    break;
            }
            onNetworkResponded(null, false);
        } else if (o instanceof List) {
            onNetworkResponded((List<DATA>) o, true);
        } else {
            onNetworkObjectResponded((DATA) o,true);
        }
    }

    protected void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
}
