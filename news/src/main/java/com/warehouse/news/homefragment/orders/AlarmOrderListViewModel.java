package com.warehouse.news.homefragment.orders;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.warehouse.base.mvvm.viewmodel.BaseMvvmListViewModel;
import com.warehouse.news.homefragment.beans.MessageContent;
import com.warehouse.news.homefragment.beans.OrderListResponse;

public class AlarmOrderListViewModel extends BaseMvvmListViewModel<AlarmOrderListModel, OrderListResponse.AlarmOrderBean>{
    private String topicType;

    public AlarmOrderListViewModel(String topicType) {
        this.topicType = topicType;
    }

    public static class MessageListViewModelFactory implements ViewModelProvider.Factory {
        private String topicType;

        public MessageListViewModelFactory(String topicType) {
            this.topicType = topicType;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)new AlarmOrderListViewModel(topicType);
        }
    }
    @Override
    public AlarmOrderListModel createModel() {
        return new AlarmOrderListModel(topicType);
    }
}
