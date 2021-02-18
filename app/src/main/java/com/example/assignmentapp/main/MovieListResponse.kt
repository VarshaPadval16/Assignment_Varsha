package com.example.assignmentapp.main

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("Title") val title : String,
    @SerializedName("Season") val season : Int,
    @SerializedName("totalSeasons") val totalSeasons : Int,
    @SerializedName("Episodes") val episodes : ArrayList<Episode>,
    @SerializedName("Response") val response : Boolean
)

data class Episode(
    @SerializedName("Title") val title : String,
    @SerializedName("Released") val released : String,
    @SerializedName("Episode") val episode : Int,
    @SerializedName("imdbRating") val imdbRating : Double,
    @SerializedName("imdbID") val imdbID : String
)


data class Ratings(
    val source: String,
    val value: String
)