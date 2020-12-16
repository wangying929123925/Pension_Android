package com.warehouse.news.homefragment.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.warehouse.base.mvvm.viewmodel.BaseMvvmObjectViewModel;
import com.warehouse.news.homefragment.beans.LoginResponse;

public class LoginViewModel extends BaseMvvmObjectViewModel<LoginModel, LoginResponse> {
   public static class LoginViewModelFactory implements ViewModelProvider.Factory{

       @NonNull
       @Override
       public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
           return (T)new LoginViewModel();
       }
   }
    @Override
    public LoginModel createModel() {
        return new LoginModel();
    }
}
