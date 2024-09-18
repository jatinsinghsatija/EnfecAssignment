package com.assignment.demoproject.listeners

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred

interface CommonListener {

    fun onAPISuccess(result: JsonArray){}

    fun onAPIFailure(e:Exception){}
}