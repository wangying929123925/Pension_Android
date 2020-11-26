package com.warehouse.base.mvvm.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.warehouse.base.mvvm.model.BaseMvvmModel;
import com.warehouse.base.mvvm.model.IBaseModelListener;
import com.warehouse.base.mvvm.model.PagingResult;
import com.warehouse.base.mvvm.model.SuperBaseMvvmViewModel;

import java.util.List;

public abstract class BaseMvvmListViewModel<MODEL extends BaseMvvmModel, DATA> extends SuperBaseMvvmViewModel <MODEL, DATA>implements IBaseModelListener<List<DATA>> {
    public MutableLiveData<List<DATA>> dataList = new MutableLiveData<>();

    public BaseMvvmListViewModel() {

    }

    public abstract MODEL createModel();


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


    public void loadNextPage() {
        createAndRegisterModel();
        if (model != null) {
            model.loadNextPage();
        }
    }


    @Override
    public void onLoadSuccess(BaseMvvmModel model, List<DATA> data, PagingResult... pagingResult) {
            if (model.isPaging()) {
                if (pagingResult[0].isEmpty) {
                    if (pagingResult[0].isFirstPage) {
                        viewStatusLiveData.postValue(ViewStatus.EMPTY);
                    } else {
                        viewStatusLiveData.postValue(ViewStatus.NO_MORE_DATA);
                    }
                } else {
                    if (pagingResult[0].isFirstPage) {
                        dataList.postValue(data);
                    } else {
                        dataList.getValue().addAll(data);
                        dataList.postValue(dataList.getValue());
                    }
                    viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
                }
            } else {
                dataList.postValue(data);
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
        if(dataList == null || dataList.getValue() == null || dataList.getValue().size() == 0) {
            createAndRegisterModel();
            model.getCachedDataAndLoad();
        } else {
            dataList.postValue(dataList.getValue());
            viewStatusLiveData.postValue(viewStatusLiveData.getValue());
        }
    }
}
