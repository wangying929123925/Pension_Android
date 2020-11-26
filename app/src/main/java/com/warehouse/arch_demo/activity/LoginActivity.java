package com.warehouse.arch_demo.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.api.GroupIdResponse;
import com.warehouse.arch_demo.api.MainApiInterface;
import com.warehouse.arch_demo.api.PostResponse;
import com.warehouse.arch_demo.api.UserInformation;
import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.preference.BasicDataPreferenceUtil;
import com.warehouse.base.utils.BaseUtils;
import com.warehouse.base.utils.CommUtil;
import com.warehouse.network.TecentNetworkApi;
import com.warehouse.network.observer.BaseObjectObserver;
import com.warehouse.news.homefragment.api.LoginResponse;

import java.io.IOException;
import java.util.Date;

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
        getImg();
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
                case R.id.refresh_button:
                    getImg();
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
            final String imgCode = validate_input.getText().toString().trim();
            BasicDataPreferenceUtil.getInstance().setString("USER_NAME",userName);
            BasicDataPreferenceUtil.getInstance().setString("PASSWORD",userPwd);
            //是否记住密码
            if(mRememberCheck.isChecked()){
                BasicDataPreferenceUtil.getInstance().setBoolean("mRememberCheck", true);
            }else{
                BasicDataPreferenceUtil.getInstance().setBoolean("mRememberCheck", false);
            }

           TecentNetworkApi.getService(MainApiInterface.class)
                    .login(userName,userPwd,imgCode,"password","ananops-client-gateway","",deviceId)
                    .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObjectObserver<LoginResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginResponse loginResponse) {
                            if(TextUtils.equals(loginResponse.getCode(),"200")) {
                                String TOKEN = loginResponse.getResult().getAccess_token();
                                if (TOKEN != null && TOKEN.length()!=0) {
                                    Toast.makeText(getApplicationContext(), loginResponse.getResult().getLoginName(), Toast.LENGTH_SHORT).show();
                                    BasicDataPreferenceUtil.getInstance().setString("LoginName", loginResponse.getResult().getLoginName());
                                    Log.v("Token_", loginResponse.getResult().getAccess_token());
                                    BasicDataPreferenceUtil.getInstance().setString("Token", "Bearer" + " " + TOKEN);
                                    getUserInfo();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                            getImg();
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
        } else if (validate_input.getText().toString().trim().equals("")) {
            Toast.makeText(this, "验证码为空，请重新输入！",
                    Toast.LENGTH_SHORT).show();
            return false;
        } return true;
    }
    @SuppressLint("CheckResult")
    private void getUserInfo(){
        String userName = BasicDataPreferenceUtil.getInstance().getString("LoginName", "0");
        TecentNetworkApi.getService(MainApiInterface.class)
                .getUserInfo(userName,BasicDataPreferenceUtil.getInstance().getString("Token"," "))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObjectObserver<UserInformation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInformation userInfo) {
                        if (TextUtils.equals(userInfo.getCode(), "200")) {
                            String USERID = userInfo.getResult().getId();
                            Log.v("user_id",USERID);
                            if (USERID != null && USERID.length() != 0) {
                                BasicDataPreferenceUtil.getInstance().setString("user_id", USERID);
                                if (!userInfo.getResult().getRoles().isEmpty()) {
                                    String roleName = userInfo.getResult().getRoles().get(0).getRoleName();
                                    Log.v("role_name",roleName);
                                    Log.v("role_code",userInfo.getResult().getRoles().get(0).getRoleCode());
                                    BasicDataPreferenceUtil.getInstance().setString("role_name", roleName);
                                    BaseUtils.getInstence().roleStringConvertNum(roleName,mContext);
                                    Log.v("role_num",BasicDataPreferenceUtil.getInstance().getInt("role_num",1)+"");
                                    BasicDataPreferenceUtil.getInstance().setString("role_code",userInfo.getResult().getRoles().get(0).getRoleCode());
                                    getGroupId();;
                                } else {
                                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_again), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "获取用户信息失败!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
    @SuppressLint("CheckResult")
    private void getImg(){
        deviceId=new Date().getTime();
        Log.v("deviceId", deviceId + "");
        TecentNetworkApi.getService(MainApiInterface.class)
                .getImage(deviceId)
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObjectObserver<PostResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostResponse postResponse) {
                        if (TextUtils.equals(postResponse.getCode(), "200")) {
                            //      Toast.makeText(LoginActivity.this," "+deviceId, Toast.LENGTH_SHORT).show();
                            byte[] input = Base64.decode(postResponse.getResult(), Base64.DEFAULT);
                            bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
                            validate_img.setImageBitmap(bitmap);
                        }else {
                            Toast.makeText(getApplicationContext(), postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    @SuppressLint("CheckResult")
    private void getGroupId() {
        TecentNetworkApi.getService(MainApiInterface.class)
                .getGroupId(Long.valueOf(BasicDataPreferenceUtil.getInstance().getString("user_id", "0")),BasicDataPreferenceUtil.getInstance().getString("Token", ""))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObjectObserver<GroupIdResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GroupIdResponse groupIdResponse) {
                        if (TextUtils.equals(groupIdResponse.getCode(), "200")) {
                            String groupId = groupIdResponse.getResult().getGroupId();
                            Log.v("group_id", groupId);
                            if (groupId != null && groupId.length() != 0) {
                                BasicDataPreferenceUtil.getInstance().setString("groupId", groupId);
                                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_again), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this,groupIdResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            try{
                                String error = httpException.response().errorBody().string();
                                Log.v("getGroupIdError", error);
                            }catch(IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
}
