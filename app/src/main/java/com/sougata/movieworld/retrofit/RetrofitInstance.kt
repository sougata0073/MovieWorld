package com.sougata.movieworld.retrofit

import com.google.gson.GsonBuilder
import com.sougata.movieworld.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        private const val BASE_URL = "https://moviesdatabase.p.rapidapi.com/"
        private val API_KEY = BuildConfig.MOVIES_DATABASE_API_KEY
        private const val API_HOST = "moviesdatabase.p.rapidapi.com"

        fun getInstance(): Retrofit {
            // Interceptor to add headers to every request
            val headerInterceptor = Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-key", API_KEY)
                    .addHeader("x-rapidapi-host", API_HOST)
                    .build()
                chain.proceed(request)
            }

            // OkHttpClient with Interceptor
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // Increase connection timeout
                .readTimeout(30, TimeUnit.SECONDS)     // Increase read timeout
                .writeTimeout(30, TimeUnit.SECONDS)    // Increase write timeout
                .addInterceptor(headerInterceptor)     // Add headers
                .build()

            // Retrofit instance
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .build()

        }
    }
}