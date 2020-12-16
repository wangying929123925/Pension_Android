package com.warehouse.news.homefragment.login;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.warehouse.base.mvvm.view.BaseMvvmFragment;
import com.warehouse.news.databinding.FragmentNewsBinding;
import com.warehouse.news.homefragment.beans.LoginResponse;

import java.util.List;

public class LoginFragment extends BaseMvvmFragment<FragmentNewsBinding,LoginViewModel, LoginResponse> {
    @Override
    protected String getFragmentTag() {
        return "LoginFragment";
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public LoginViewModel getViewModel() {
        return new ViewModelProvider(getActivity(),new LoginViewModel.LoginViewModelFactory()).get(getArguments().getString("LoginFragment"),LoginViewModel.class);
    }

    @Override
    protected View getLoadSirView() {
        return null;
    }

    @Override
    public void onNetworkResponded(List list, boolean isDataUpdated) {

    }

    @Override
    public void onNetworkObjectResponded(LoginResponse loginResponse, boolean isDataUpdated) {

    }

    @Override
    protected void onViewCreated() {

    }
}
