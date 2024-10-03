package com.example.chucknorris

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chucknorris.api.response.JokesResponse
import com.example.chucknorris.api.response.QueryResponse
import com.example.chucknorris.api.response.ResultItem
import com.example.chucknorris.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _jokes = MutableLiveData<List<JokesResponse>>()
    val jokes: LiveData<List<JokesResponse>> get() = _jokes

    private val _query = MutableLiveData<List<ResultItem>>()
    val query: LiveData<List<ResultItem>> get() = _query

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val jokesList = mutableListOf<JokesResponse>()

    init {
        getJokes(15)
    }

    fun getJokes(count: Int) {
        _isLoading.value = true
        jokesList.clear()
        for (i in 1..count) {
            val client = ApiConfig.getApiService().getJokes()
            client.enqueue(object : Callback<JokesResponse> {
                override fun onResponse(call: Call<JokesResponse>, response: Response<JokesResponse>) {
                    if (response.isSuccessful) {
                        val joke = response.body()
                        if (joke != null) {
                            jokesList.add(joke)
                            _jokes.value = jokesList
                        }
                    } else {
                        Log.e("MainViewModel", "Error: ${response.message()}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<JokesResponse>, t: Throwable) {
                    Log.e("MainViewModel", "Failure: ${t.message}")
                    _isLoading.value = false
                }
            })
        }
    }

    fun searchQuery(jokesList : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchJokes(jokesList)
        client.enqueue(object : Callback<QueryResponse> {
            override fun onResponse(call: Call<QueryResponse>, response: Response<QueryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _query.value = responseBody.result
                        println("cek respon main ${responseBody}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<QueryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}




