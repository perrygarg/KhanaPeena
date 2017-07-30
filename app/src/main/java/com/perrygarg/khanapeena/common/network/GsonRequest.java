package com.perrygarg.khanapeena.common.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.perrygarg.khanapeena.common.util.AppLogs;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by perry.garg on 30/01/17.
 */

public class GsonRequest<T, K> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> classResponse;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final K classRequest;

    //** Default charset for JSON request. */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param classResponse Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, K classReq, Class<T> classResponse, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);

        this.classRequest = classReq;
        this.classResponse = classResponse;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        return headers != null ? headers : super.getHeaders();
    }

    public String getBodyContentType()
    {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        byte requestBytes[] = null;

        try
        {
            String request = (classRequest == null) ? null : gson.toJson(classRequest);

            //TODO Log
            AppLogs.d(AppLogs.TAG, "REQUEST :-\n "+request);

            requestBytes = (request == null) ? null : request.getBytes(PROTOCOL_CHARSET);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return requestBytes;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            //TODO Log
            AppLogs.d(AppLogs.TAG, "RESPONSE :-\n " + json);

            return Response.success(gson.fromJson(json, classResponse), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
        catch(Exception e)
        {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }

}
