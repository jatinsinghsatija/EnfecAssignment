package com.assignment.demoproject.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

object Extensions {

    @JvmOverloads
    fun <T : Any> JsonObject.transform(classType: Class<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson(this, classType)
        }catch (e:Exception){
            return null
        }
    }
    @JvmOverloads
    fun <T : Any> JsonElement.transform(classType: Class<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson(this, classType)
        }catch (e:Exception){
            return null
        }
    }
}