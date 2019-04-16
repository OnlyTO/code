package com.example.cc.code_container;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class setting extends AppCompatActivity {
    private static final String TAG = "user";
    private String username;
    private String oldpwd;
    private String newpwd1;
    private String newpwd2;
    @BindView(R.id.oldPwd_et)
    EditText oldPwdEt;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.new_pwd_again)
    EditText newPwdAgain;
    @BindView(R.id.save_bt)
    Button saveBt;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        username = getIntent().getSerializableExtra(TAG).toString();
    }

    @OnClick(R.id.save_bt)
    public void onViewClicked() {
        oldpwd = oldPwdEt.getText().toString();
        newpwd1 = newPwd.getText().toString();
        newpwd2 = newPwdAgain.getText().toString();
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        if (MD5Utils.md5(oldpwd).equals(sp.getString(username, ""))) {
            if (RegisterActivity.isPasswordValid(newpwd1)) {
                newPwd.requestFocus();
                Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            }
            else if (RegisterActivity.isPasswordSeem(newpwd1, newpwd2)) {
                newPwdAgain.requestFocus();
                Toast.makeText(this, R.string.error_seem_password, Toast.LENGTH_SHORT).show();
            }
            else {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(username, MD5Utils.md5(newpwd1));
                editor.commit();
                Intent intent = new Intent(setting.this, MainActivity.class);
                intent.putExtra(TAG, username);
                startActivity(intent);
            }
        }
        else {
            oldPwdEt.requestFocus();
            Toast.makeText(this, R.string.error_incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }

}
