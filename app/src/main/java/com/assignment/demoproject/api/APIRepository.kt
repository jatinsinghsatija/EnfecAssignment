package com.assignment.demoproject.api

import com.assignment.demoproject.listeners.CommonListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object APIRepository {
    private var globalScope: Job? = null


    fun sendOtpAPI(listener: CommonListener) {

        globalScope = CoroutineScope(Dispatchers.IO).launch {

            when (val result = APICalls.demoAPI()) {
                is UseCaseResult.Success -> {
                    withContext(Dispatchers.Main) {
                        listener.onAPISuccess(result.data)
                    }
                }

                is UseCaseResult.Error -> {
                    withContext(Dispatchers.Main) {
                        listener.onAPIFailure(result.exception)
                    }
                }

                else -> UseCaseResult.Empty
            }
        }
    }
}