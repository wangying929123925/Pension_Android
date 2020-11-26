package com.warehouse.news.homefragment.login;

import com.warehouse.base.mvvm.model.BaseMvvmModel;
import com.warehouse.network.TecentNetworkApi;
import com.warehouse.network.observer.BaseObserver;
import com.warehouse.news.homefragment.api.LoginResponse;
import com.warehouse.news.homefragment.api.NewsApiInterface;

public class LoginModel extends BaseMvvmModel <LoginResponse,LoginResponse>{
    public LoginModel() {
        super(true,  "login_preference_key", null,1);
    }

    @Override
    public void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .login("孔刘","123456","imageType","grant_type","1","anan",12345563422345L)
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<LoginResponse>(this,this)));
    }

    @Override
    public void onSuccess(LoginResponse t, boolean isFromCache) {

    }

    @Override
    public void onFailure(Throwable e) {

    }
}
