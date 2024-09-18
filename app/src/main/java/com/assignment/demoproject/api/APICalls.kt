package com.assignment.demoproject.api

import com.assignment.demoproject.api.ApiHolder.getAPIService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody

object APICalls {

    suspend fun demoAPI(): UseCaseResult<JsonArray> {
        return try {
            val result = getAPIService().await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}