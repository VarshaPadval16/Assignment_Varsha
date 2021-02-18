package com.example.assignmentapp.api_service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

interface ApiClient {
    companion object Factory {
        const val BASE_URL = "http://www.omdbapi.com"

        private val gson : Gson by lazy {
            GsonBuilder().excludeFieldsWithModifiers(
                Modifier.FINAL,
                Modifier.TRANSIENT,
                Modifier.STATIC
            ).create()
        }

        private val httpClient : OkHttpClient by lazy {
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()
        }

        private val retrofit : Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        val apiService : ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}