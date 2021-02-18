package com.example.assignmentapp.api_service

import androidx.annotation.Keep
import com.example.assignmentapp.main.Episode
import com.example.assignmentapp.main.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    fun getMovieList(
        @Query("apikey") apikey: String,
        @Query("season") season: String,
        @Query("i") i: String
    ): Call<MovieListResponse>
}