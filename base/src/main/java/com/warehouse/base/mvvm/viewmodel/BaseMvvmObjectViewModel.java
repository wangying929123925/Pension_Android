package com.warehouse.base.mvvm.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.warehouse.base.mvvm.model.BaseMvvmModel;
import com.warehouse.base.mvvm.model.PagingResult;
import com.warehouse.base.mvvm.model.SuperBaseMvvmViewModel;
import com.warehouse.base.mvvm.model.IBaseModelListener;

public abstract class BaseMvvmObjectViewModel<MODEL extends BaseMvvmModel, DATA >extends SuperBaseMvvmViewModel<MODEL, DATA> implements IBaseModelListener<DATA> {
    public MutableLiveData<DATA> dataResponse = new MutableLiveData<>();

    public BaseMvvmObjectViewModel() {
    }
    @Override
    protected void createAndRegisterModel() {
        if (model == null) {
            model = createModel();
            if (model != null) {
                model.register(this);
            } else {
                // Throw exception.
            }
        }
    }
    @Override
    public void onLoadSuccess(BaseMvvmModel model, DATA data, PagingResult... pagingResult) {
        if (model.isPaging()) {

            dataResponse.postValue(data);
            viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
        }
    }

    @Override
    public void onLoadFail(BaseMvvmModel model, String message, PagingResult... result) {
        errorMessage.postValue(message);
        if (result != null && result.length > 0 && result[0].isFirstPage) {
            viewStatusLiveData.postValue(ViewStatus.REFRESH_ERROR);
        } else {
            viewStatusLiveData.postValue(ViewStatus.LOAD_MORE_FAILED);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        if(dataResponse == null || dataResponse.getValue() == null ) {
            createAndRegisterModel();
            model.getCachedDataAndLoad();
        } else {
            dataResponse.postValue(dataResponse.getValue());
            viewStatusLiveData.postValue(viewStatusLiveData.getValue());
        }
    }
}
