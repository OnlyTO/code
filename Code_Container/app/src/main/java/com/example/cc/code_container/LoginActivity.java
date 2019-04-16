package com.example.cc.code_container;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String spPhone;
    private FingerPrinter fingerPrinter;
    private static final String TAG = "user";
    private static final String PHONE = "phone";
    private EditText checkEt;
    private Button cancleBt;

    @BindView(R.id.username)
    EditText mUserView;
    @BindView(R.id.password)
    EditText mPwdView;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    @BindView(R.id.register_button)
    Button mRegisterButton;
    @BindView(R.id.forget_pwd)
    TextView forgetPwd;
    @BindView(R.id.finger_login)
    TextView fingerLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }


    @OnClick({R.id.sign_in_button, R.id.register_button, R.id.forget_pwd, R.id.finger_login})
    public void onClick(View view) {
        username = mUserView.getText().toString().trim();
        password = mPwdView.getText().toString().trim();
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (view.getId()) {
            case R.id.sign_in_button:
                //获取密码
                final String spPsw = sp.getString(username, "");//传入用户名获取密码
                //如果密码不为空则确实保存过这个用户名
                if (spPsw.equals(MD5Utils.md5(password))) {
                    Intent data = new Intent(LoginActivity.this, MainActivity.class);
                    data.putExtra(TAG, username);
                    startActivity(data);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.error_incorrect_password, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register_button:
                Intent data = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(data);
                break;
            case R.id.forget_pwd:
                //SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
                spPhone = sp.getString(username + PHONE, "");

                View v1 = LayoutInflater.from(this).inflate(R.layout.verification, null);
                checkEt = (EditText) v1.findViewById(R.id.vert_et);
                builder.setView(v1);
                builder.setTitle("请输入手机号码");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (spPhone.equals(checkEt.getText().toString().trim())) {
                            Toast.makeText(LoginActivity.this, password, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "验证错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.finger_login:
                fingerPrinter = new FingerPrinter();
                if (fingerPrinter.hasFingerHard() && fingerPrinter.hasFingerInput()) {
                    View v2 = LayoutInflater.from(this).inflate(R.layout.fingerprinter, null);
                    /* builder.setView(v2).setTitle("指纹验证");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            fingerPrinter.cancel();
                        }
                    }); */
                    final AlertDialog dialog = builder.create();
                    cancleBt = (Button)v2.findViewById(R.id.cancle_bt);
                    cancleBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            fingerPrinter.cancel();
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setContentView(v2);
                    dialog.getWindow().setLayout(600, 600);

                    fingerPrinter.authenticate(this, username);
                }
        }
    }

}
