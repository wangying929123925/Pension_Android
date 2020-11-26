package com.warehouse.base.mvvm.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LifecycleObserver;

import com.warehouse.base.mvvm.viewmodel.ViewStatus;

public abstract class SuperBaseMvvmViewModel<MODEL extends BaseMvvmModel, DATA> extends ViewModel implements LifecycleObserver {
    protected MODEL model;
    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public SuperBaseMvvmViewModel() {

    }
    public abstract MODEL createModel();
    public void refresh() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegisterModel();
        if (model != null) {
            model.refresh();
        }
    }
    protected abstract void createAndRegisterModel() ;
    //{
//        if (model == null) {
//            model = createModel();
//            if (model != null) {
//                model.register(this);
//            } else {
//                // Throw exception.
//            }
//        }
   // }

    public void getCachedDataAndLoad() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegisterModel();
        if (model != null) {
            model.getCachedDataAndLoad();
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.cancel();
        }
    }
}
