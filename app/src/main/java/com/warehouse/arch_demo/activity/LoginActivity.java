package com.warehouse.arch_demo.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.api.MainApiInterface;
import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.preference.BasicDataPreferenceUtil;
import com.warehouse.base.utils.CommUtil;
import com.warehouse.network.TecentNetworkApi;
import com.warehouse.network.observer.BaseObjectObserver;
import com.warehouse.news.homefragment.beans.LoginForm;
import com.warehouse.news.homefragment.beans.LoginResponse;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mLoginButton;                      //登录按钮
    private CheckBox mRememberCheck;
    private CheckBox check_password_cb;
    private EditText validate_input;
    private ImageView validate_img;
    private ImageView refresh_button;
    private Long deviceId;
    private Context mContext;
    Bitmap bitmap;
    private CompositeDisposable co = new CompositeDisposable();

    //  private SharedPreferences login_sp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   ActivityManager.getInstance().addActivity(this);
        mContext = this;
        setContentView(R.layout.activity_login);
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);
        validate_input = findViewById(R.id.validate_input);
        validate_img = findViewById(R.id.validate_img);
        refresh_button = findViewById(R.id.refresh_button);
        check_password_cb = findViewById(R.id.check_password_cb);
        String name = BasicDataPreferenceUtil.getInstance().getString("USER_NAME", "");
        String pwd = BasicDataPreferenceUtil.getInstance().getString("PASSWORD", "");
        boolean choseRemember = BasicDataPreferenceUtil.getInstance().getBoolean("mRememberCheck", false);
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }
        mLoginButton.setOnClickListener(mListener);
        refresh_button.setOnClickListener(mListener);
        check_password_cb.setOnCheckedChangeListener(this);
    }


    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;
                default:
                    break;
            }
        }};
    @SuppressLint("CheckResult")
    public void login()
    {
        if (isUserNameAndPwdValid()) {
            final String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            final String userPwd = mPwd.getText().toString().trim();
            BasicDataPreferenceUtil.getInstance().setString("USER_NAME",userName);
            BasicDataPreferenceUtil.getInstance().setString("PASSWORD",userPwd);
            //是否记住密码
            if(mRememberCheck.isChecked()){
                BasicDataPreferenceUtil.getInstance().setBoolean("mRememberCheck", true);
            }else{
                BasicDataPreferenceUtil.getInstance().setBoolean("mRememberCheck", false);
            }
            LoginForm loginForm = new LoginForm();
            loginForm.setUsername(userName);
            loginForm.setPassword(userPwd);

           TecentNetworkApi.getService(MainApiInterface.class)
                    .login1(loginForm)
                    .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObjectObserver<LoginResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginResponse loginResponse) {
                            if(TextUtils.equals(loginResponse.getCode(),"0")) {
                                String TOKEN = loginResponse.getToken();
                                if (TOKEN != null && TOKEN.length()!=0) {
                                    Toast.makeText(getApplicationContext(), loginResponse.getUserId(), Toast.LENGTH_SHORT).show();
                                    BasicDataPreferenceUtil.getInstance().setString("user_id", loginResponse.getUserId());
                                    Log.v("Token_", loginResponse.getToken());
                                    BasicDataPreferenceUtil.getInstance().setString("Token", TOKEN);
                                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.v("LoginTime", System.currentTimeMillis() + "");
                            Log.v("deviceIdLogin", deviceId + "");
                            if (e instanceof HttpException) {
                                HttpException httpException = (HttpException) e;
                                try{
                                    String error = httpException.response().errorBody().string();
                                    Log.v("getGroupIdError", error);

                                }catch(IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            Toast.makeText(getApplicationContext(), "用户名密码错误或验证码错误", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 显示和隐藏密码
        if (isChecked) {
            // 显示密码
            mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // 隐藏密码
            mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // 光标移动到最后面
        CommUtil.cursorToEnd(mPwd);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //系统服务类，用于管理网络连接
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is aviliable", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unaviliable", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }  return true;
    }



}
