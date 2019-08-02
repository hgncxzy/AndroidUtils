package com.xzy.utils.clone;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 克隆工具类。
 * 参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/
 * java/com/blankj/utilcode/util/CloneUtils.java
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public final class CloneUtils {

    private CloneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
