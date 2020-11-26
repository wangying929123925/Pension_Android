package com.warehouse.arch_demo.application;

import android.content.Context;

import com.warehouse.base.BaseApplication;
import com.warehouse.base.preference.PreferencesUtil;
import com.warehouse.network.base.NetworkApi;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ArchDemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);

    }
}
