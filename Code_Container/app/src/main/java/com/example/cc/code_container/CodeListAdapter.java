package com.example.cc.code_container;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;



import static android.content.Context.MODE_PRIVATE;

public class CodeListAdapter extends RecyclerView.Adapter<CodeListAdapter.CodeHolder> {
    private static final String TAG = "user";
    private static final String DETAIL_MODE = "MODE_OF_ACTIVITY";
    private static final String DETAIL_NO = "item_id";
    private Button cancleBt;
    private FingerPrinter fingerPrinter;
    private ImageView fingerView;
    private EditText et;
    private Context mContext;
    private List<Code> mCodes;

    public CodeListAdapter(List<Code> codes, Context context) {
        mCodes = codes;
        mContext = context;
    }

    @Override
    public CodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CodeHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final CodeHolder holder, int position) {
        Code code = mCodes.get(position);
        holder.bind(code);

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.mTagView.setVisibility(View.VISIBLE);
            holder.mTagView.setText(code.getLetter());
        }
        else {
            holder.mTagView.setVisibility(View.GONE);
        }
        holder.mTagView.setEnabled(false);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fingerPrinter = new FingerPrinter();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                if (fingerPrinter.hasFingerHard() && fingerPrinter.hasFingerInput()) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.fingerprinter, null);

                   /* builder.setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            fingerPrinter.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setLayout(700, 800); */
                    final AlertDialog dialog = builder.create();
                    cancleBt = (Button)view.findViewById(R.id.cancle_bt);
                    cancleBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            fingerPrinter.cancel();
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setContentView(view);
                    dialog.getWindow().setLayout(600, 600);

                    fingerPrinter.authenticate(mContext, holder.mCode.getMMainName(), holder.mCode.getmId());
                }
                else {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.verification, null);
                    et = (EditText) view.findViewById(R.id.vert_et);
                    builder.setView(view).setTitle("密码验证").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //非activity类里不能调用sharedpreferences
                            SharedPreferences sp = MyApplication.getInstance().getSharedPreferences("loginInfo", MODE_PRIVATE);
                            String spPwd = sp.getString(holder.mCode.getMMainName(), "");
                            if ((MD5Utils.md5(et.getText().toString())).equals(spPwd)) {
                                Intent intent = new Intent(mContext, DetailActivity.class);
                                intent.putExtra(TAG, holder.mCode.getMMainName());
                                intent.putExtra(DETAIL_NO, holder.mCode.getmId());
                                intent.putExtra(DETAIL_MODE, "seek");
                                mContext.startActivity(intent);  //非activity类不能直接调用startactivity
                            } else {
                                Toast.makeText(mContext, R.string.error_incorrect_password, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setPositiveButton("取消", null).create();
                    builder.show();
                }
            }
        });
    }

    public class CodeHolder extends RecyclerView.ViewHolder{
        private TextView mTextView, mTagView;
        private Code mCode;

        public CodeHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.key_item);
            mTagView = (TextView) itemView.findViewById(R.id.tag);
        }

        public void bind(Code code) {
            mCode = code;
            mTextView.setText(mCode.getmTitle());
        }

        /*
        @Override
        public void onClick(View v) { */
            /*
            View view = LayoutInflater.from(mContext).inflate(R.layout.verification, null);
            et = (EditText) view.findViewById(R.id.vert_et);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setView(view);
            builder.setTitle("提示");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //非activity类里不能调用sharedpreferences
                    SharedPreferences sp = MyApplication.getInstance().getSharedPreferences("loginInfo", MODE_PRIVATE);
                    String spPwd = sp.getString(mCode.getMMainName(), "");
                    if ((MD5Utils.md5(et.getText().toString())).equals(spPwd)) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra(TAG, mCode.getMMainName());
                        intent.putExtra(DETAIL_NO, mCode.getmId());
                        intent.putExtra(DETAIL_MODE, "seek");
                        mContext.startActivity(intent);  //非activity类不能直接调用startactivity
                    } else {
                        Toast.makeText(mContext, R.string.error_incorrect_password, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setPositiveButton("取消", null);
            builder.create();
            builder.show(); */
/*
            fingerPrinter = new FingerPrinter();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            if (fingerPrinter.hasFingerHard() && fingerPrinter.hasFingerInput()) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.fingerprinter, null);
                fingerView = (ImageView) view.findViewById(R.id.finger_authen);

                TextView title = new TextView(mContext);
                title.setText("验证指纹");
                title.setGravity(Gravity.CENTER);
                builder.setView(view).setCustomTitle(title);

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setLayout(700, 800);

                if (fingerPrinter.authenticate()) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(TAG, mCode.getMMainName());
                    intent.putExtra(DETAIL_NO, mCode.getmId());
                    intent.putExtra(DETAIL_MODE, "seek");
                    mContext.startActivity(intent);
                }
            }
            else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.verification, null);
                et = (EditText) view.findViewById(R.id.vert_et);
                builder.setView(view).setTitle("密码验证").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //非activity类里不能调用sharedpreferences
                        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences("loginInfo", MODE_PRIVATE);
                        String spPwd = sp.getString(mCode.getMMainName(), "");
                        if ((MD5Utils.md5(et.getText().toString())).equals(spPwd)) {
                            Intent intent = new Intent(mContext, DetailActivity.class);
                            intent.putExtra(TAG, mCode.getMMainName());
                            intent.putExtra(DETAIL_NO, mCode.getmId());
                            intent.putExtra(DETAIL_MODE, "seek");
                            mContext.startActivity(intent);  //非activity类不能直接调用startactivity
                        } else {
                            Toast.makeText(mContext, R.string.error_incorrect_password, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setPositiveButton("取消", null).create();
                builder.show();
            }
        } */
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mCodes.get(position).getLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mCodes.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mCodes.size();
    }

    public void setCodes(List<Code> codes) {
        mCodes = codes;
    }
}
