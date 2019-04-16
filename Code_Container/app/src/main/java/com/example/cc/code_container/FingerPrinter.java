package com.example.cc.code_container;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

public class FingerPrinter {
    private static final String DETAIL_MODE = "MODE_OF_ACTIVITY";
    private static final String DETAIL_NO = "item_id";
    private CryptoObjectHelper cryptoObjectHelper;
    private static CancellationSignal cancellationSignal;
    private FingerprintManagerCompat.CryptoObject cryptoObject;
    private final String log = "FingerPrinter";
    private final String TAG = "user";
    private Context mContext;
    private final FingerprintManagerCompat managerCompat;

    public FingerPrinter() {
        mContext = MyApplication.getsContext();
        managerCompat = FingerprintManagerCompat.from(mContext);
    }

    public boolean hasFingerHard() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        if (managerCompat == null) {
            return false;
        }

        return managerCompat.isHardwareDetected();
    }

    public boolean hasFingerInput() {
        return managerCompat.hasEnrolledFingerprints();
    }

    public void authenticate(final Context context, final String name, final Long id) {
        cancellationSignal = new CancellationSignal();
        try {
            cryptoObjectHelper = new CryptoObjectHelper();
            cryptoObject = cryptoObjectHelper.buildCryptoObject();
        }
        catch (Exception e) {
            Log.e(TAG, "failed");
        }
        managerCompat.authenticate(cryptoObject,//用于通过指纹验证取出AndroidKeyStore中key的值
                                    0,//系统建议为0,其他值，谷歌占位用于其他生物验证
                                    cancellationSignal,//强制取消指纹验证
                                    //FingerPrintManager.AuthenticationCallback—在验证时传入该接口，通过该接口来返回验证指纹的结果
                                    new FingerprintManagerCompat.AuthenticationCallback() {
                                        @Override
                                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                                            super.onAuthenticationSucceeded(result);
                                            Log.d(log, "验证成功");
                                            Intent intent = new Intent(context, DetailActivity.class);
                                            intent.putExtra(TAG, name);
                                            intent.putExtra(DETAIL_NO, id);
                                            intent.putExtra(DETAIL_MODE, "seek");
                                            context.startActivity(intent);
                                        }

                                        //验证成功任务调用这个方法，errorcode为5
                                        @Override
                                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                                            super.onAuthenticationError(errMsgId, errString);
                                            Log.d(TAG, errString.toString());
                                        }

                                        @Override
                                        public void onAuthenticationFailed() {
                                            super.onAuthenticationFailed();
                                            Log.d(TAG, "指纹不匹配");
                                            Toast.makeText(mContext, "指纹不匹配", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                                            super.onAuthenticationHelp(helpMsgId, helpString);
                                            Log.d(TAG, "请再次验证");
                                            Toast.makeText(mContext, "请再次验证", Toast.LENGTH_SHORT).show();
                                        }
                                    }, null);
    }

    public void authenticate(final Context context, final String name) {
        cancellationSignal = new CancellationSignal();
        try {
            cryptoObjectHelper = new CryptoObjectHelper();
            cryptoObject = cryptoObjectHelper.buildCryptoObject();
        }
        catch (Exception e) {
            Log.e(TAG, "failed");
        }
        managerCompat.authenticate(cryptoObject,//用于通过指纹验证取出AndroidKeyStore中key的值
                0,//系统建议为0,其他值，谷歌占位用于其他生物验证
                cancellationSignal,//强制取消指纹验证
                //FingerPrintManager.AuthenticationCallback—在验证时传入该接口，通过该接口来返回验证指纹的结果
                new FingerprintManagerCompat.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Log.d(log, "验证成功");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra(TAG, name);
                        intent.putExtra(DETAIL_MODE, "seek");
                        context.startActivity(intent);
                    }

                    //验证成功任务调用这个方法，errorcode为5
                    @Override
                    public void onAuthenticationError(int errMsgId, CharSequence errString) {
                        super.onAuthenticationError(errMsgId, errString);
                        Log.d(TAG, errString.toString());
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Log.d(TAG, "指纹不匹配");
                        Toast.makeText(mContext, "指纹不匹配", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                        super.onAuthenticationHelp(helpMsgId, helpString);
                        Log.d(TAG, "请再次验证");
                        Toast.makeText(mContext, "请再次验证", Toast.LENGTH_SHORT).show();
                    }
                }, null);
    }

    public void cancel() {
        if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
            cancellationSignal.cancel();
        }
    }
}
