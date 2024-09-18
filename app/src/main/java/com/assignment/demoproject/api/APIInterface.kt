package com.assignment.demoproject.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface APIInterface {
    @GET("users")
    fun commonGetAPI(): Deferred<JsonArray>
}