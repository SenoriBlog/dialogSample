package com.senori.dialogSample;

import android.app.Application;
import android.content.Context;

/**
 * アプリケーションサイクル
 */
public class MainApplication extends Application {
    private static MainApplication instance = null;

    /**
     * アプリケーションインスタンスのセット
     * @param application アプリケーション
     */
    private static void setInstance(MainApplication application) {
        instance = application;
    }

    /**
     * アプリ生成時
     */
    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.setInstance(this);

        // 現在設定されている UncaughtExceptionHandler を退避
        final Thread.UncaughtExceptionHandler savedUncaughtExceptionHandler =
                Thread.getDefaultUncaughtExceptionHandler();
        // キャッチされなかった例外発生時の処理を設定する
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            private volatile boolean mCrashing = false;
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                try {
                    if (!mCrashing) {
                        mCrashing = true;

                        for (StackTraceElement element : ex.getStackTrace()) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(element.getFileName());
                            builder.append(" ");
                            builder.append(element.getLineNumber());
                            builder.append(" ");
                            builder.append(element.getClass());
                            builder.append(" ");
                            builder.append(element.getMethodName());
                        }
                    }
                } finally {
                    savedUncaughtExceptionHandler.uncaughtException(thread, ex);
                }
            }
        });
    }

    /**
     * アプリケーションコンテキスト取得
     * @return アプリケーションコンテキスト
     */
    public static Context getContext() {
        return instance.getApplicationContext();
    }
}