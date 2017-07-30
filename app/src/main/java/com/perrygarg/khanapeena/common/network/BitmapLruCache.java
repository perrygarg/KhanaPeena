package com.perrygarg.khanapeena.common.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by perry.garg on 30/01/17.
 */

public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{

    public BitmapLruCache()
    {
        this(getDefaultLruCacheSize());
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BitmapLruCache(int maxSize)
    {
        super(maxSize);
    }

    public static int getDefaultLruCacheSize()
    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }
}
