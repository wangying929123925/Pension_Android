package com.warehouse.news.homefragment.orders;

import com.warehouse.base.mvvm.model.BaseMvvmModel;
import com.warehouse.base.preference.BasicDataPreferenceUtil;
import com.warehouse.network.TecentNetworkApi;
import com.warehouse.network.observer.BaseObserver;
import com.warehouse.news.homefragment.beans.MessageContent;
import com.warehouse.news.homefragment.beans.MessageListRequest;
import com.warehouse.news.homefragment.beans.MessageListResponse;
import com.warehouse.news.homefragment.beans.NewsApiInterface;
import com.warehouse.news.homefragment.beans.OrderListResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlarmOrderListModel extends BaseMvvmModel<OrderListResponse, List<OrderListResponse.AlarmOrderBean>> {
    private String topicType;
    private int curPage = 1;//当前页
    private int pageSize = 10;
    private int totalPage = 1;
    public AlarmOrderListModel(String topicType) {
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
        TecentNetworkApi.getService(NewsApiInterface.class).
                getAlarmOrderList(BasicDataPreferenceUtil.getInstance().getString("user_id", ""),BasicDataPreferenceUtil.getInstance().getString("Token"," "))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<OrderListResponse>(this,this)));
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
    public void onSuccess(OrderListResponse messageListResponse, boolean isFromCache) {
        List<OrderListResponse.AlarmOrderBean> alarmOrderBeans = new ArrayList<>();
        alarmOrderBeans.addAll(messageListResponse.getRows());
        totalPage = messageListResponse.getPageNum();
        notifyResultToListener(messageListResponse, alarmOrderBeans, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
