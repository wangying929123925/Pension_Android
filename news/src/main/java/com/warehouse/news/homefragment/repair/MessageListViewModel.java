package com.warehouse.news.homefragment.repair;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.warehouse.base.mvvm.viewmodel.BaseMvvmListViewModel;
import com.warehouse.news.homefragment.beans.MessageContent;

public class MessageListViewModel extends BaseMvvmListViewModel<MessageListModel, MessageContent>{
    private String topicType;

    public MessageListViewModel(String topicType) {
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
            return (T)new MessageListViewModel(topicType);
        }
    }
    @Override
    public MessageListModel createModel() {
        return new MessageListModel(topicType);
    }
}
