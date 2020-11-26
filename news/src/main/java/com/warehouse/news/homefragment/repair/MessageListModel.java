package com.warehouse.news.homefragment.repair;

import com.warehouse.base.mvvm.model.BaseMvvmModel;
import com.warehouse.base.preference.BasicDataPreferenceUtil;
import com.warehouse.network.TecentNetworkApi;
import com.warehouse.network.observer.BaseObserver;
import com.warehouse.news.homefragment.api.MessageContent;
import com.warehouse.news.homefragment.api.MessageListRequest;
import com.warehouse.news.homefragment.api.MessageListResponse;
import com.warehouse.news.homefragment.api.NewsApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MessageListModel extends BaseMvvmModel<MessageListResponse, List<MessageContent>> {
    private String topicType;
    private int curPage = 1;//当前页
    private int pageSize = 10;
    private int totalPage = 1;
    public MessageListModel(String topicType) {
        super(true, topicType+"_preference_key", null, 1);
        this.topicType = topicType;
    }

    @Override
    public void load() {
        MessageListRequest messageListRequest = new MessageListRequest();
        if (topicType.equals("ALL")) {
            messageListRequest.setMessageTopic(null);
        } else {
            messageListRequest.setMessageTopic(topicType);
        }
        messageListRequest.setStatus(null);
        messageListRequest.setPageNum(curPage);
        messageListRequest.setPageSize(pageSize);
        messageListRequest.setUserId(Long.valueOf( BasicDataPreferenceUtil.getInstance().getString("user_id", "")));
        TecentNetworkApi.getService(NewsApiInterface.class).getMessageList(messageListRequest,BasicDataPreferenceUtil.getInstance().getString("Token"," "))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<MessageListResponse>(this,this)));
    }

    @Override
    public void refresh() {
        curPage=1;
        super.refresh();

    }

    @Override
    public void loadNextPage() {
        curPage++;
        if (curPage <= totalPage) {
            super.loadNextPage();
            //  more
        }

    }

    @Override
    public void addDisposable(Disposable d) {
        super.addDisposable(d);
    }

    @Override
    public void onSuccess(MessageListResponse messageListResponse, boolean isFromCache) {
        List<MessageContent> messageContentsAdd = new ArrayList<>();
        messageContentsAdd.addAll(messageListResponse.getResult().getList());
        totalPage = messageListResponse.getResult().getPages();
        notifyResultToListener(messageListResponse, messageContentsAdd, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
