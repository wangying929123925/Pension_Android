package com.warehouse.news.homefragment.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.warehouse.base.mvvm.view.BaseMvvmFragment;
import com.warehouse.base.recyclerview.CommonAdapter;
import com.warehouse.base.recyclerview.MultiItemTypeAdapter;
import com.warehouse.base.recyclerview.base.ViewHolder;
import com.warehouse.news.R;
import com.warehouse.news.databinding.FragmentNewsBinding;
import com.warehouse.news.homefragment.beans.MessageBodyGson;
import com.warehouse.news.homefragment.beans.MessageContent;
import com.warehouse.news.homefragment.beans.OrderListResponse;

import java.util.ArrayList;
import java.util.List;

public class AlarmOrderListFragment extends BaseMvvmFragment<FragmentNewsBinding, AlarmOrderListViewModel, OrderListResponse.AlarmOrderBean> {
    protected final static String BUNDLE_KEY_PARAM_TOPIC_TYPE = "topicType";
    private CommonAdapter<OrderListResponse.AlarmOrderBean> mAdapter;
    List<OrderListResponse.AlarmOrderBean> alarmOrderBeans = new ArrayList<>();
    public static AlarmOrderListFragment newInstance(String topicType) {
        AlarmOrderListFragment messageListFragment= new AlarmOrderListFragment();
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
    public AlarmOrderListViewModel getViewModel() {
        return new ViewModelProvider(getActivity(),new AlarmOrderListViewModel.MessageListViewModelFactory(getArguments().getString(BUNDLE_KEY_PARAM_TOPIC_TYPE)))
                .get(getArguments().getString(BUNDLE_KEY_PARAM_TOPIC_TYPE), AlarmOrderListViewModel.class);
    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.refreshLayout;
    }

    @Override
    public void onNetworkResponded(List<OrderListResponse.AlarmOrderBean> messageContents, boolean isDataUpdated) {
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();
        if(isDataUpdated) {
           alarmOrderBeans.addAll(messageContents);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNetworkObjectResponded(OrderListResponse.AlarmOrderBean messageContent, boolean isDataUpdated) {

    }

    @Override
    protected void onViewCreated() {
        viewModel.dataList.observe(this, this);
        mAdapter = new CommonAdapter<OrderListResponse.AlarmOrderBean>(getActivity(),R.layout.item_alarm_order,alarmOrderBeans) {
            @Override
            protected void convert(ViewHolder viewHolder, OrderListResponse.AlarmOrderBean alarmOrderBean, int position) {
                viewHolder.setText(R.id.item_tv_order_number, String.valueOf(alarmOrderBean.getId()));//编号
                viewHolder.setText(R.id.item_tv_order_address_content,String.valueOf(alarmOrderBean.getCreateTime()));//创建时间
                viewHolder.setText(R.id.item_tv_order_emergency_type,String.valueOf(alarmOrderBean.getTaskType()));//紧急程度
                viewHolder.setText(R.id.item_tv_order_status,String.valueOf(alarmOrderBean.getTaskState()));//状态
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String orderId = alarmOrderBeans.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("orderId",orderId);
                Intent intent = new Intent(getContext(), AlarmOrderDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
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
