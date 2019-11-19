package com.example.premierleague.di

import com.example.premierleague.data.remote.ApiService
import com.example.premierleague.data.remote.AuthInterceptor
import com.example.premierleague.util.Constants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ziyad on Nov, 2019
 */

var retrofitModule = module {
    factory {
        AuthInterceptor()
    }

    factory {
        provideClient(get())
    }

    single {
        provideRetrofit(get())
    }

    factory {
        provideApiService(get())
    }
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun provideClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit{
    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(Constants.BASE_URL)
        .build()
}