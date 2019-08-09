package com.xzy.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xzy.utils.common.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * sp 工具类。
 */
@SuppressWarnings("all")
public class SPUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context context
     * @param key     context
     * @param object  object
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，
     * 然后调用相对于的方法获取值
     *
     * @param context       context
     * @param key           key
     * @param defaultObject defaultObject
     * @return Object 和传入的类型一样
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个 key 值和对应的 value 值
     *
     * @param context context
     * @param key     key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个 key 是否已经存在
     *
     * @param context context
     * @param key     key
     * @return boolean
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context context
     * @return Map<String, ?>
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决 SharedPreferencesCompat.apply 方法的一个兼容类
     *
     * @author xzy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找 apply 的方法
         *
         * @return Method
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                Log.e("exception", e.getMessage());
            }
            return null;
        }

        /**
         * 如果找到则使用 apply 执行，否则使用 commit
         *
         * @param editor SharedPreferences.Editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                Log.e("exception", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("exception", e.getMessage());
            } catch (InvocationTargetException e) {
                Log.e("exception", e.getMessage());
            }
            editor.commit();
        }
    }

    private static SPUtils sDefaultSPUtils;

    /**
     * Set the default instance of {@link SPUtils}.
     *
     * @param SPUtils The default instance of {@link SPUtils}.
     */
    public static void setDefaultSPUtils(final SPUtils SPUtils) {
        sDefaultSPUtils = SPUtils;
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final String value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }


    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultSPUtils());
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultSPUtils());
    }


    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final int value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final int value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key) {
        return getInt(key, getDefaultSPUtils());
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue) {
        return getInt(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final long value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final long value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key) {
        return getLong(key, getDefaultSPUtils());
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue) {
        return getLong(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final float value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final float value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key) {
        return getFloat(key, getDefaultSPUtils());
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue) {
        return getFloat(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final boolean value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, getDefaultSPUtils());
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return getBoolean(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void put(@NonNull final String key, final Set<String> value) {
        put(key, value, getDefaultSPUtils());
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit) {
        put(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, getDefaultSPUtils());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue) {
        return getStringSet(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    public static Map<String, ?> getAll() {
        return getAll(getDefaultSPUtils());
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key) {
        return contains(key, getDefaultSPUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    public static void remove(@NonNull final String key) {
        remove(key, getDefaultSPUtils());
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void remove(@NonNull final String key, final boolean isCommit) {
        remove(key, isCommit, getDefaultSPUtils());
    }

    /**
     * Remove all preferences in sp.
     */
    public static void clear() {
        clear(getDefaultSPUtils());
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void clear(final boolean isCommit) {
        clear(isCommit, getDefaultSPUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final String value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }


    /**
     * Return the string value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.getString(key);
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SPUtils SPUtils) {
        return SPUtils.getString(key, defaultValue);
    }


    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final int value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final int value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }

    /**
     * Return the int value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.getInt(key);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue,
                             @NonNull final SPUtils SPUtils) {
        return SPUtils.getInt(key, defaultValue);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final long value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final long value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }

    /**
     * Return the long value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.getLong(key);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue,
                               @NonNull final SPUtils SPUtils) {
        return SPUtils.getLong(key, defaultValue);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final float value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final float value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }

    /**
     * Return the float value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.getFloat(key);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue,
                                 @NonNull final SPUtils SPUtils) {
        return SPUtils.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final boolean value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final boolean value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.getBoolean(key);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key,
                                     final boolean defaultValue,
                                     @NonNull final SPUtils SPUtils) {
        return SPUtils.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key, final Set<String> value,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit,
                           @NonNull final SPUtils SPUtils) {
        SPUtils.put(key, value, isCommit);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           @NonNull final SPUtils SPUtils) {
        return SPUtils.getStringSet(key);
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param SPUtils     The instance of {@link SPUtils}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue,
                                           @NonNull final SPUtils SPUtils) {
        return SPUtils.getStringSet(key, defaultValue);
    }

    /**
     * Return all values in sp.
     *
     * @param SPUtils The instance of {@link SPUtils}.
     * @return all values in sp
     */
    public static Map<String, ?> getAll(@NonNull final SPUtils SPUtils) {
        return SPUtils.getAll();
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        return SPUtils.contains(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void remove(@NonNull final String key, @NonNull final SPUtils SPUtils) {
        SPUtils.remove(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void remove(@NonNull final String key, final boolean isCommit,
                              @NonNull final SPUtils SPUtils) {
        SPUtils.remove(key, isCommit);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void clear(@NonNull final SPUtils SPUtils) {
        SPUtils.clear();
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param SPUtils The instance of {@link SPUtils}.
     */
    public static void clear(final boolean isCommit, @NonNull final SPUtils SPUtils) {
        SPUtils.clear(isCommit);
    }

    private static SPUtils getDefaultSPUtils() {
        return sDefaultSPUtils != null ? sDefaultSPUtils : SPUtils.getInstance();
    }

    private static final Map<String, com.xzy.utils.sp.SPUtils> SP_UTILS_MAP = new HashMap<>();
    private SharedPreferences sp;

    /**
     * Return the single {@link com.xzy.utils.sp.SPUtils} instance
     *
     * @return the single {@link com.xzy.utils.sp.SPUtils} instance
     */
    public static com.xzy.utils.sp.SPUtils getInstance() {
        return getInstance("", Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link com.xzy.utils.sp.SPUtils} instance
     *
     * @param mode Operating mode.
     * @return the single {@link com.xzy.utils.sp.SPUtils} instance
     */
    public static com.xzy.utils.sp.SPUtils getInstance(final int mode) {
        return getInstance("", mode);
    }

    /**
     * Return the single {@link com.xzy.utils.sp.SPUtils} instance
     *
     * @param spName The name of sp.
     * @return the single {@link com.xzy.utils.sp.SPUtils} instance
     */
    public static com.xzy.utils.sp.SPUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link com.xzy.utils.sp.SPUtils} instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single {@link com.xzy.utils.sp.SPUtils} instance
     */
    public static com.xzy.utils.sp.SPUtils getInstance(String spName, final int mode) {
        if (isSpace(spName)) spName = "SPUtils";
        com.xzy.utils.sp.SPUtils SPUtils = SP_UTILS_MAP.get(spName);
        if (SPUtils == null) {
            synchronized (com.xzy.utils.sp.SPUtils.class) {
                SPUtils = SP_UTILS_MAP.get(spName);
                if (SPUtils == null) {
                    SPUtils = new com.xzy.utils.sp.SPUtils(spName, mode);
                    SP_UTILS_MAP.put(spName, SPUtils);
                }
            }
        }
        return SPUtils;
    }

    private SPUtils(final String spName) {
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SPUtils(final String spName, final int mode) {
        sp = Utils.getApp().getSharedPreferences(spName, mode);
    }



    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}


