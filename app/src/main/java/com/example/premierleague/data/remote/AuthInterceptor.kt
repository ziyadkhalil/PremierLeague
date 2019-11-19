package com.example.premierleague.data.remote

import com.example.premierleague.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Ziyad on Nov, 2019
 */
class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        req = req.newBuilder()
            .addHeader(Constants.AUTH_HEADER_NAME,Constants.AUTH_HEADER_VALUE)
            .build()
        return chain.proceed(req)
    }
}