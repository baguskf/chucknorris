package com.example.chucknorris.api.response

sealed class Joke {
    data class Jokes(val jokes: JokesResponse) : Joke()
    data class Result(val result: ResultItem) : Joke()
}