package com.warehouse.news.homefragment.headlinenews;

import com.warehouse.base.mvvm.viewmodel.BaseMvvmListViewModel;
import com.warehouse.news.homefragment.beans.NewsChannelsBean;

public class HeadlineNewsViewModel extends BaseMvvmListViewModel<NewsChannelModel, NewsChannelsBean.ChannelList> {

    @Override
    public NewsChannelModel createModel() {
        return new NewsChannelModel();
    }
}
