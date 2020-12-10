package com.warehouse.news.homefragment.repair;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.warehouse.base.mvvm.view.BaseMvvmFragment;
import com.warehouse.base.recyclerview.CommonAdapter;
import com.warehouse.base.recyclerview.base.ViewHolder;
import com.warehouse.news.R;
import com.warehouse.news.databinding.FragmentNewsBinding;
import com.warehouse.news.homefragment.api.MessageBodyGson;
import com.warehouse.news.homefragment.api.MessageContent;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends BaseMvvmFragment<FragmentNewsBinding,MessageListViewModel, MessageContent> {
    protected final static String BUNDLE_KEY_PARAM_TOPIC_TYPE = "topicType";
    private CommonAdapter<MessageContent> mAdapter;
    List<MessageContent> mMessageContents = new ArrayList<>();
    public static MessageListFragment newInstance(String topicType) {
        MessageListFragment messageListFragment= new MessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_TOPIC_TYPE,topicType);
        messageListFragment.setArguments(bundle);
        return messageListFragment;
    }
    @Override
    protected String getFragmentTag() {
        return getArguments().getString(BUNDLE_KEY_PARAM_TOPIC_TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public MessageListViewModel getViewModel() {
        return new ViewModelProvider(getActivity(),new MessageListViewModel.MessageListViewModelFactory(getArguments().getString(BUNDLE_KEY_PARAM_TOPIC_TYPE)))
                .get(getArguments().getString(BUNDLE_KEY_PARAM_TOPIC_TYPE),MessageListViewModel.class);
    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.refreshLayout;
    }

    @Override
    public void onNetworkResponded(List<MessageContent> messageContents, boolean isDataUpdated) {
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();
        if(isDataUpdated) {
           mMessageContents.addAll(messageContents);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNetworkObjectResponded(MessageContent messageContent, boolean isDataUpdated) {

    }

    @Override
    protected void onViewCreated() {
        viewModel.dataList.observe(this, this);
        mAdapter = new CommonAdapter<MessageContent>(getActivity(),R.layout.item_user_message,mMessageContents) {
            @Override
            protected void convert(ViewHolder viewHolder, MessageContent messageContent, int position) {
                Gson gson = new Gson();
                MessageBodyGson messageEntity = gson.fromJson(messageContent.getMessageBody(), MessageBodyGson.class);
                viewHolder.setText(R.id.message_item_text, String.valueOf(messageEntity.getMsgBodyDto().getStatusMsg()));
                if (messageContent.getMessageTopic().equals("MDMC_TOPIC")) {
                    viewHolder.setImageResource(R.id.order_message_img,R.drawable.ic_message_orange);
                    viewHolder.setText(R.id.message_item_title,"报警消息");
                } else if (messageContent.getMessageTopic().equals("IMC_TOPIC")) {
                    viewHolder.setText(R.id.message_item_title,"服务消息");
                    viewHolder.setImageResource(R.id.order_message_img,R.drawable.ic_message);
                } else {
                    viewHolder.setImageResource(R.id.order_message_img,R.drawable.ic_message_system);
                    viewHolder.setText(R.id.message_item_title,"支付消息");
                }
                if (messageContent.getStatus() == 0) {
                    viewHolder.setText(R.id.message_item_status, "未读");
                    viewHolder.setTextColor(R.id.message_item_status,getResources().getColor(R.color.red));
                } else {
                    viewHolder.setText(R.id.message_item_status, "已读");
                    viewHolder.setTextColor(R.id.message_item_status,getResources().getColor(R.color.blue));

                }
            }
        };
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.listview.setAdapter(mAdapter);
        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.refresh();
            }
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.loadNextPage();
            }
        });
        showLoading();
    }
}
