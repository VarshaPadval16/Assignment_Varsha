package com.example.assignmentapp.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.assignmentapp.api_service.ApiClient
import com.example.assignmentapp.utils.NetworkUtils
import com.example.assignmentapp.utils.NetworkUtils.showErrorToast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(val context: Context) : ViewModel() {

    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var movieListResponseMutableLiveData = MutableLiveData<MovieListResponse>()

    fun getMovieListResponse(): MutableLiveData<MovieListResponse> {
        if (movieListResponseMutableLiveData == null) {
            movieListResponseMutableLiveData = MutableLiveData()
        }
        return movieListResponseMutableLiveData
    }


    fun getMovieList() {
        val apiService = ApiClient.apiService

        var call: Call<MovieListResponse>? =
            apiService.getMovieList("665cb4a6","1", "tt0944947")

        call!!.enqueue(object : Callback<MovieListResponse> {
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.d(TAG, "...onFailure ${t.message}")
                NetworkUtils.hideProgressBar()
                context.showErrorToast(t.message)
            }

            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                NetworkUtils.hideProgressBar()
                Log.d(TAG, "onResponse: " + response.body())
                if (null == response.body()) {
                    Log.d(TAG, "onResponse: " + response.message())
                }else{
                    if (response.isSuccessful) {
                        movieListResponseMutableLiveData.value = response.body()
                    }
                }
            }

        })
    }

}