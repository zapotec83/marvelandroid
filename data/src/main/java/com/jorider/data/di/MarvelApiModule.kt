package com.jorider.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jorider.data.common.Constants
import com.jorider.data.extensions.md5
import com.jorider.data.ws.MarvelAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
object MarvelApiModule {

    @Provides
    fun providesRetrofitService(): MarvelAPIService {

        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", Constants.API_KEY)
                .addQueryParameter("ts", ts)
                .addQueryParameter("hash", "$ts${Constants.PRIVATE_KEY}${Constants.API_KEY}".md5())
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(MarvelAPIService::class.java)
    }
}