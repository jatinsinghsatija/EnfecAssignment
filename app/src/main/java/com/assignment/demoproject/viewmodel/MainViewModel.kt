package com.assignment.demoproject.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.assignment.demoproject.api.APIRepository
import com.assignment.demoproject.listeners.CommonListener
import com.assignment.demoproject.models.ResponseItem
import com.assignment.demoproject.utils.Extensions.transform
import com.google.gson.JsonArray

class MainViewModel : ViewModel() {
    val data = mutableStateListOf<ResponseItem?>()
    fun callDemoAPI(ctx: Context) {
        if(data.isEmpty()) {
            APIRepository.sendOtpAPI(object : CommonListener {
                override fun onAPISuccess(result: JsonArray) {
                    result.forEach {
                        data.add(it.transform(ResponseItem::class.java))
                    }
                }

                override fun onAPIFailure(e: Exception) {
                    Toast.makeText(
                        ctx,
                        e.localizedMessage ?: "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}