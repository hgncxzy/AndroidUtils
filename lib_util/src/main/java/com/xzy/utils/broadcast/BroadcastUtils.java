package com.xzy.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xzy.utils.UtilsApp;
import com.xzy.utils.intent.IntentUtils;

import java.util.List;
import java.util.Map;


/**
 * 广播工具类
 *
 * @author xzy
 * 部分参考 https://github.com/xuexiangjys/XUtil
 */
@SuppressWarnings("all")
public final class BroadcastUtils {

    //============================getBroadCastIntent=============================//
    private BroadcastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取广播意图
     *
     * @param action 广播动作
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(String action) {
        return getBroadCastIntent(UtilsApp.INSTANCE, null, action);
    }

    /**
     * 获取广播意图
     *
     * @param context
     * @param action  广播动作
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(Context context, String action) {
        return getBroadCastIntent(context, null, action);
    }

    /**
     * 获取广播意图
     *
     * @param cls 广播接收器
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(Class<? extends BroadcastReceiver> cls) {
        return getBroadCastIntent(UtilsApp.INSTANCE, cls, null);
    }

    /**
     * 获取广播意图
     *
     * @param context
     * @param cls     广播接收器
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(Context context, Class<? extends BroadcastReceiver> cls) {
        return getBroadCastIntent(context, cls, null);
    }

    /**
     * 获取广播意图
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(Class<? extends BroadcastReceiver> cls, String action) {
        return IntentUtils.getIntent(UtilsApp.INSTANCE, cls, action);
    }

    /**
     * 获取广播意图
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @return
     */
    @NonNull
    public static Intent getBroadCastIntent(Context context, Class<? extends BroadcastReceiver> cls,
                                            String action) {
        return IntentUtils.getIntent(context, cls, action);
    }


    /**
     * 获取广播意图
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param map    携带的数据
     * @return
     */
    public static Intent getBroadCastIntent(Context context, Class<? extends BroadcastReceiver> cls,
                                            String action, Map<String, Object> map) {
        Intent intent = getBroadCastIntent(context, cls, action);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        return intent;
    }

    /**
     * 获取广播意图
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param map    携带的数据
     * @return
     */
    public static Intent getBroadCastIntent(Class<? extends BroadcastReceiver> cls, String action,
                                            Map<String, Object> map) {
        Intent intent = getBroadCastIntent(cls, action);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        return intent;
    }

    //==========================发送广播: 不携带数据==============================//

    /**
     * 发广播（不带数据）
     *
     * @param cls 广播接收器
     */
    public static void sendBroadCast(Class<? extends BroadcastReceiver> cls) {
        sendBroadCast(cls, null);
    }

    /**
     * 发广播（不带数据）
     *
     * @param action 广播动作
     */
    public static void sendBroadCast(String action) {
        sendBroadCast(null, action);
    }

    /**
     * 发广播（不带数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     */
    public static void sendBroadCast(Class<? extends BroadcastReceiver> cls, String action) {
        Intent intent = getBroadCastIntent(UtilsApp.INSTANCE, cls, action);
        UtilsApp.INSTANCE.sendBroadcast(intent);
    }

    //==========================发送广播: intent携带数据==============================//

    /**
     * 发广播（单数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param key
     * @param param
     */
    public static void sendBroadCast(Class<? extends BroadcastReceiver> cls, String action,
                                     String key, Object param) {
        sendBroadCast(UtilsApp.INSTANCE, cls, action, key, param);
    }

    /**
     * 发广播（单数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @param key
     * @param param
     */
    public static void sendBroadCast(Context context, Class<? extends BroadcastReceiver> cls,
                                     String action, String key, Object param) {
        Intent intent = getBroadCastIntent(context, cls, action);
        intent = IntentUtils.putExtra(intent, key, param);
        context.sendBroadcast(intent);
    }

    /**
     * 发广播（多数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @param keys
     * @param params
     * @throws Exception
     */
    public static void sendBroadCast(Context context, Class<? extends BroadcastReceiver> cls,
                                     String action, String[] keys, Object... params)
            throws Exception {
        Intent intent = getBroadCastIntent(context, cls, action);
        if (keys.length != params.length) {
            throw new Exception("the number of keys must be equal to params");
        }
        for (int i = 0; i < params.length; i++) {
            intent = IntentUtils.putExtra(intent, keys[i], params[i]);
        }
        context.sendBroadcast(intent);
    }

    /**
     * 发广播（多数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param map    数据
     */
    public static void sendBroadCast(Class<? extends BroadcastReceiver> cls, String action,
                                     Map<String, Object> map) {
        sendBroadCast(UtilsApp.INSTANCE, cls, action, map);
    }

    /**
     * 发广播（多数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @param map     数据
     */
    public static void sendBroadCast(Context context, Class<? extends BroadcastReceiver> cls,
                                     String action, Map<String, Object> map) {
        Intent intent = getBroadCastIntent(context, cls, action, map);
        context.sendBroadcast(intent);
    }

    //==========================发送广播: Bundle携带数据==============================//

    /**
     * 发广播（多数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     */
    public static void sendBroadCastWithBundle(Class<? extends BroadcastReceiver> cls,
                                               String action, String key, Object param) {
        sendBroadCastWithBundle(UtilsApp.INSTANCE, cls, action, key, param);
    }

    /**
     * 发广播（多数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     */
    public static void sendBroadCastWithBundle(Context context, Class<? extends BroadcastReceiver>
            cls, String action, String key, Object param) {
        Intent intent = getBroadCastIntent(context, cls, action);
        Bundle bundle = new Bundle();
        bundle = IntentUtils.putBundle(bundle, key, param);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    /**
     * 发广播（多数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param map    数据
     */
    public static void sendBroadCastWithBundle(Class<? extends BroadcastReceiver> cls,
                                               String action, Map<String, Object> map) {
        sendBroadCastWithBundle(UtilsApp.INSTANCE, cls, action, map);
    }

    /**
     * 发广播（多数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @param map     数据
     */
    public static void sendBroadCastWithBundle(Context context, Class<? extends
            BroadcastReceiver> cls, String action, Map<String, Object> map) {
        Intent intent = getBroadCastIntent(context, cls, action);
        if (map != null && !map.isEmpty()) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                bundle = IntentUtils.putBundle(bundle, entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }
        context.sendBroadcast(intent);
    }

    /**
     * 发广播（多数据）
     *
     * @param cls    广播接收器
     * @param action 广播动作
     * @param bundle 数据
     */
    public static void sendBroadCast(Class<? extends BroadcastReceiver> cls, String action,
                                     Bundle bundle) {
        sendBroadCast(UtilsApp.INSTANCE, cls, action, bundle);
    }

    /**
     * 发广播（多数据）
     *
     * @param context
     * @param cls     广播接收器
     * @param action  广播动作
     * @param bundle  数据
     */
    public static void sendBroadCast(Context context, Class<? extends BroadcastReceiver> cls,
                                     String action, Bundle bundle) {
        Intent intent = getBroadCastIntent(context, cls, action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.sendBroadcast(intent);
    }


    //=======================广播注册、注销=================================//

    /**
     * 注册广播接收器
     *
     * @param context
     * @param receiver
     * @param actionList
     */
    public static void registerReceiver(Context context, BroadcastReceiver receiver,
                                        List<String> actionList) {
        IntentFilter intentFilter = new IntentFilter();
        if (actionList != null && actionList.size() > 0) {
            for (String action : actionList) {
                intentFilter.addAction(action);
            }
        }
        context.registerReceiver(receiver, intentFilter);
    }

    /**
     * 注册广播接收器
     *
     * @param context
     * @param receiver
     * @param actions
     */
    public static void registerReceiver(Context context, BroadcastReceiver receiver,
                                        String... actions) {
        IntentFilter iFilter = new IntentFilter();
        if (actions != null && actions.length > 0) {
            for (int i = 0; i < actions.length; i++) {
                iFilter.addAction(actions[i]);
            }
        }
        context.registerReceiver(receiver, iFilter);
    }

    /**
     * 注销广播接收器
     *
     * @param context
     * @param receiver
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
