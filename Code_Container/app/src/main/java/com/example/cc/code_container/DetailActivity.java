package com.example.cc.code_container;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cc.code_container.greendao.CodeDao;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "user";
    private static final String DETAIL_MODE = "MODE_OF_ACTIVITY";
    private static final String DETAIL_NO = "item_id";
    private String[] pinyin;
    private TextView textView;
    private String key;
    private Long id = 0L;
    private String mode;
    private String username;
    private String mTitle;
    private String mName;
    private String mPassword;
    private String mDescription;

    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.pwd_et)
    EditText pwdEt;
    @BindView(R.id.save_bt)
    Button saveBt;
    @BindView(R.id.del_bt)
    Button delBt;
    @BindView(R.id.description_et)
    EditText descriptionEt;
    @BindView(R.id.getKeyBt)
    ImageButton getKeyBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        username = intent.getSerializableExtra(TAG).toString();
        mode = intent.getSerializableExtra(DETAIL_MODE).toString();
        id = intent.getLongExtra(DETAIL_NO, 0);
        if (mode.equals("add")) {
            delBt.setVisibility(View.GONE);
        } else {
            CodeDao codedao = MyApplication.getInstance().getDaoSession().getCodeDao();
            Code code = codedao.queryBuilder().where(CodeDao.Properties.MId.eq(id)).unique();
            if (code != null) {
                titleEt.setText(code.getmTitle());
                nameEt.setText(code.getMName());
                pwdEt.setText(code.getMCode());
                descriptionEt.setText(code.getMDescription());
            }
        }

    }

    private void saveBtClick() {
        mTitle = titleEt.getText().toString();
        mName = nameEt.getText().toString();
        mPassword = pwdEt.getText().toString();
        mDescription = descriptionEt.getText().toString();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

        Code code = new Code();
        code.setMTitle(mTitle);
        code.setMCode(mPassword);
        code.setMDescription(mDescription);
        code.setMName(mName);
        code.setMMainName(username);
        if (mTitle.charAt(0) > 127) {
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(mTitle.charAt(0), format);
            }
            catch (Exception error) {
                Log.e("Converse", "转换失败");
            }
            code.setLetter(String.valueOf(pinyin[0].charAt(0)));
        }
        else if ('a' <= mTitle.charAt(0) && mTitle.charAt(0) <= 'z') {
            code.setLetter(String.valueOf(mTitle.charAt(0)).toUpperCase());
        }
        else {
            code.setLetter(String.valueOf(mTitle.charAt(0)));
        }
        CodeDao codedao = MyApplication.getInstance().getDaoSession().getCodeDao();
        codedao.insert(code);
        finish();
    }

    private void update() {
        mTitle = titleEt.getText().toString();
        mName = nameEt.getText().toString();
        mPassword = pwdEt.getText().toString();
        mDescription = descriptionEt.getText().toString();

        CodeDao codeDao = MyApplication.getInstance().getDaoSession().getCodeDao();
        Code code = codeDao.queryBuilder().where(CodeDao.Properties.MId.eq(id)).unique();
        code.setMTitle(mTitle);
        code.setMCode(mPassword);
        code.setMDescription(mDescription);
        code.setMName(mName);

        codeDao.update(code);
        finish();
    }

    private void deleteBtClick() {
        CodeDao codeDao = MyApplication.getInstance().getDaoSession().getCodeDao();
        Code code = codeDao.queryBuilder().where(CodeDao.Properties.MId.eq(id)).unique();
        if (code != null) {
            codeDao.delete(code);
            finish();
        }
    }

    @OnClick({R.id.save_bt, R.id.del_bt, R.id.getKeyBt})
    public void onViewClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (view.getId()) {
            case R.id.save_bt:
                if (mode.equals("seek")) {
                    builder.setTitle(R.string.notice);
                    builder.setMessage("是否更改");
                    builder.setPositiveButton("否", null);
                    builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            update();
                        }
                    }).create();
                    builder.show();

                } else {
                    saveBtClick();
                    Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.del_bt:
                builder.setTitle(R.string.notice);
                builder.setMessage("是否删除");
                builder.setNegativeButton("否", null);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBtClick();
                    }
                }).create().show();
                break;
            case R.id.getKeyBt:
                key = GenerateKey.getKey();
                View v = LayoutInflater.from(this).inflate(R.layout.random_key, null);
                textView = (TextView)v.findViewById(R.id.random_et);
                textView.setText(key);
                builder.setView(v);
                builder.setTitle(R.string.notice);
                builder.setNegativeButton("粘贴到剪切板", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Cc", key);
                        clipboardManager.setPrimaryClip(clipData);
                    }
                });
                builder.setPositiveButton("取消", null);
                builder.show();
        }
    }

}
