package com.example.mtstest1.network

import com.example.mtstest1.Models.*
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("authors")
    fun getAuthorList(@Query("lastName") lastName: String): Call<AuthorList>

    @GET("authors/{authorId}")
    fun getAuthorById(@Path("authorId") authorId: String): Call<Author>
}
