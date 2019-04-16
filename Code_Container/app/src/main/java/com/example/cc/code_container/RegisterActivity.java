package com.example.cc.code_container;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private static final String PHONE = "phone";
    private String username;
    private String firstpwd;
    private String secondpwd;
    private String phone;

    @BindView(R.id.user_register)
    EditText mUsername;
    @BindView(R.id.first_pwd_register)
    EditText mFirstPwdView;
    @BindView(R.id.second_pwd_register)
    EditText mSecondPwdView;
    @BindView(R.id.register_bt)
    Button mRegisterBt;
    @BindView(R.id.user_phone)
    EditText mUserPhone;
    /*@BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.first_pwd_register)
    EditText mFirstPwdView;

    @BindView(R.id.second_pwd_register)
    EditText mSecondPwdView;

    @BindView(R.id.register_bt)
    Button mRegisterButton; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        /*@OnClick(R.id.user_register)
        public void onViewClicked() {
            private String username = mUsername.getText().toString();
            private String password = mFirstPwdView
            if (attemptLogin() && !isExistUserName(username)) {
                saveRegisterInfo(mUsername.getText().toString().trim(), mFirstPwdView.getText().toString().trim());
                Intent data = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(data);
                finish();
            }
        }*/

    }

    //验证用户名是否存在
    private boolean isExistUserName(String userName) {
        boolean isExist = true;

        //MODE_PRIVATE：默认操作模式，和直接传0效果相同，表示只有当前应用程序才可以对这个SharedPreferences文件进行读写
        //"loginInfo" : 数据存放文件，不存在则创建
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw = sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if (!TextUtils.isEmpty(spPsw)) {
            Toast.makeText(this, "该用户已存在", Toast.LENGTH_SHORT).show();
            isExist = false;
        }
        return isExist;
    }

    //将注册用户信息保存在sharedprefrences中
    private void saveRegisterInfo(String userName, String psw, String phone) {
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor = sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        editor.putString(userName + PHONE, phone);
        //提交修改 editor.commit();
        editor.commit();
    }

    /**
     * 验证登录或注册用户信息正确性
     */
    public boolean attemptLogin(String username, String firstpwd, String secondpwd) {

        // Store values at the time of the login attempt.
        //String username = mUsername.getText().toString();
        //String firstpwd = mFirstPwdView.getText().toString();
        //String secondpwd = mSecondPwdView.getText().toString();

        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(username) || !isUserValid(username)) {
            //mUsername.setError(getString(R.string.error_field_required));
            Toast.makeText(RegisterActivity.this, R.string.error_invalid_user, Toast.LENGTH_SHORT).show();
            focusView = mUsername;
            return false;
        }

        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(firstpwd) || !isPasswordValid(firstpwd)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            Toast.makeText(RegisterActivity.this, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            focusView = mFirstPwdView;
            return false;
        } else if (TextUtils.isEmpty(secondpwd) || !isPasswordSeem(firstpwd, secondpwd)) {
            Toast.makeText(RegisterActivity.this, R.string.error_seem_password, Toast.LENGTH_LONG).show();
            focusView = mSecondPwdView;
            return false;
        } else {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
            return true;
        }
        //return true;
    }

    private boolean isUserValid(String user) {
        for (int i = 0; i < user.length(); i++) {
            char str = user.charAt(i);
            if (str < 48 || (str > 57 && str < 65) || (str > 90 && str < 97) || str > 122) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    public static boolean isPasswordSeem(String firstpwd, String secondpwd) {
        return firstpwd != secondpwd;
    }

    @OnClick(R.id.register_bt)
    public void onClick() {
        username = mUsername.getText().toString().trim();
        firstpwd = mFirstPwdView.getText().toString().trim();
        secondpwd = mSecondPwdView.getText().toString();
        phone = mUserPhone.getText().toString().trim();
        if (attemptLogin(username, firstpwd, secondpwd) && isExistUserName(username)) {
            saveRegisterInfo(username, firstpwd, phone);
            Intent data = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(data);
            finish();
        }
    }
}
