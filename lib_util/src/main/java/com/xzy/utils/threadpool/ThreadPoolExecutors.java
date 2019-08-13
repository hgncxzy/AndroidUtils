/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xzy.utils.threadpool;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 应用的全局线程池 （包括单线程池的磁盘io，多线程池的网络io和主线程）
 *
 * @author xzy
 * 部分参考 https://github.com/xuexiangjys/XUtil
 */
@SuppressWarnings("all")
public class ThreadPoolExecutors {

    private static ThreadPoolExecutors sInstance;

    /**
     * 单线程池
     */
    private final ExecutorService mSingleIO;

    /**
     * 多线程池
     */
    private final ExecutorService mPoolIO;

    /**
     * 主线程
     */
    private final Executor mMainThread;

    private ThreadPoolExecutors(ExecutorService singleIO, ExecutorService poolIO,
                                Executor mainThread) {
        mSingleIO = singleIO;
        mPoolIO = poolIO;
        mMainThread = mainThread;
    }

    private ThreadPoolExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(Runtime.getRuntime()
                        .availableProcessors()),
                new MainThreadExecutor());
    }

    /**
     * 获取线程管理实例
     *
     * @return
     */
    public static ThreadPoolExecutors get() {
        if (sInstance == null) {
            synchronized (ThreadPoolExecutors.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolExecutors();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取单线程池
     *
     * @return
     */
    public ExecutorService singleIO() {
        return mSingleIO;
    }

    /**
     * 获取磁盘单线程池
     *
     * @return
     */
    public ExecutorService diskIO() {
        return mSingleIO;
    }

    /**
     * 获取多线程池
     *
     * @return
     */
    public ExecutorService poolIO() {
        return mPoolIO;
    }

    /**
     * 获取网络请求多线程池
     *
     * @return
     */
    public ExecutorService networkIO() {
        return mPoolIO;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
