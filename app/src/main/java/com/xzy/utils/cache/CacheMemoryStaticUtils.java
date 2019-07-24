package com.xzy.utils.cache;


import androidx.annotation.NonNull;

/**
 * 内存缓存相关。
 * 参考 https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/
 * java/com/blankj/utilcode/util/CacheMemoryStaticUtils.java
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public final class CacheMemoryStaticUtils {

    private static CacheMemoryUtils sDefaultCacheMemoryUtils;

    /**
     * Set the default instance of {@link CacheMemoryUtils}.
     *
     * @param cacheMemoryUtils The default instance of {@link CacheMemoryUtils}.
     */
    public static void setDefaultCacheMemoryUtils(final CacheMemoryUtils cacheMemoryUtils) {
        sDefaultCacheMemoryUtils = cacheMemoryUtils;
    }

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Object value) {
        put(key, value, getDefaultCacheMemoryUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Object value, int saveTime) {
        put(key, value, saveTime, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key The key of cache.
     * @param <T> The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key) {
        return get(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key, final T defaultValue) {
        return get(key, defaultValue, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the count of cache.
     *
     * @return the count of cache
     */
    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheMemoryUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key) {
        return remove(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Clear all of the cache.
     */
    public static void clear() {
        clear(getDefaultCacheMemoryUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value);
    }

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           int saveTime,
                           @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value, saveTime);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param <T>              The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key,
                            @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.get(key);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param <T>              The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key,
                            final T defaultValue,
                            @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.get(key, defaultValue);
    }

    /**
     * Return the count of cache.
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @return the count of cache
     */
    public static int getCacheCount(@NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.getCacheCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key,
                                @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void clear(@NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.clear();
    }

    private static CacheMemoryUtils getDefaultCacheMemoryUtils() {
        return sDefaultCacheMemoryUtils != null ? sDefaultCacheMemoryUtils
                : CacheMemoryUtils.getInstance();
    }
}