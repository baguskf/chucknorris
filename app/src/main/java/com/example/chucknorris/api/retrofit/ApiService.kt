package com.example.chucknorris.api.retrofit

import com.example.chucknorris.api.response.JokesResponse
import com.example.chucknorris.api.response.QueryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("jokes/random")
    fun getJokes(
    ):  Call<JokesResponse>



    @GET("jokes/search")
    fun searchJokes(
        @Query("query") jokesQuery: String
    ): Call<QueryResponse>

}