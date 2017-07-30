package com.perrygarg.khanapeena.common.network;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by perry.garg on 30/01/17.
 */

public class NetworkHandler {
    private static NetworkHandler networkHandler;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Context context;

    private final int TIMEOUT = 20000; //In Millis
    private final String TAG = "VolleyRequest";

    /**
     * Get Instance
     * @param context
     * @return singleton instance of NetworkHandler class
     */
    public static synchronized NetworkHandler getInstance(Context context)
    {
        if (networkHandler == null)
        {
            networkHandler = new NetworkHandler(context);
        }
        return networkHandler;
    }

    private NetworkHandler(Context context)
    {
        this.context = context;
        this.requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag((TextUtils.isEmpty(tag) ? TAG : tag));
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     */
    public void cancelRequests(Object tag)
    {
        if (requestQueue != null)
        {
            requestQueue.cancelAll(tag);
        }
    }

    public void getImageFromUrl(ImageView imageView, String imgUrl, int defualtImgRes, int errorImgRes)
    {
        imageLoader.get(imgUrl, ImageLoader.getImageListener(imageView, defualtImgRes, errorImgRes));
    }

}
