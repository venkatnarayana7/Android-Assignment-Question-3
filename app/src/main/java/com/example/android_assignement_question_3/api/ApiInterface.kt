package com.example.android_assignement_question_3.api

import com.example.android_assignement_question_3.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("current.json")
    fun getWeather(

        @Query("key") apiKey: String,

        @Query("q") city: String,

        @Query("aqi") aqi: String = "no"

    ): Call<WeatherResponse>

}