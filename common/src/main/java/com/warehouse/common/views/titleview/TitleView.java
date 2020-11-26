package com.warehouse.common.views.titleview;

import android.content.Context;
import android.view.View;

import com.warehouse.base.customview.BaseCustomView;
import com.warehouse.common.R;
import com.warehouse.common.databinding.TitleViewBinding;
import com.warehouse.webview.WebviewActivity;

public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void onRootClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(), "News", data.jumpUri);
    }

    @Override
    protected void setDataToView(TitleViewModel titleViewModel) {
        binding.setViewModel(titleViewModel);
    }
}
