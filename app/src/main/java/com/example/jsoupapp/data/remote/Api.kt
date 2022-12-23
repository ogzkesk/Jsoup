package com.example.jsoupapp.data.remote

interface Api {


    suspend fun searchOnUnsplash(query:String) : List<Source>

    suspend fun checkEuro() : Euro?

}