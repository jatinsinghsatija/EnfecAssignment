package com.assignment.demoproject.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.math.roundToInt

object ApiHolder {

    val apiService = createBaseApiService()
    fun getAPIService(): Deferred<JsonArray> {
        return apiService.commonGetAPI()

    }

    fun createBaseApiService(): APIInterface {
        val requestTimeout = getRequestTimeout()
        return getRetrofitClient(
            getOkhttpClientBuilder(
                requestTimeout,
                requestTimeout,
                60
            ), "https://jsonplaceholder.typicode.com/"
        ).create(APIInterface::class.java)
    }

    fun getRequestTimeout(): Long {
        return 60L
    }

    private fun getOkhttpClientBuilder(
        connectTimeoutInSec: Long,
        readTimeoutInSec: Long,
        writeTimeoutInSec: Long
    ): OkHttpClient.Builder {
        val trustAllCertificates = object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(
            null,
            arrayOf<TrustManager>(trustAllCertificates),
            java.security.SecureRandom()
        )
        val okHttpClientBuilder =
            OkHttpClient.Builder().sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
                .hostnameVerifier { _, _ -> true }


        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.connectTimeout(connectTimeoutInSec, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(readTimeoutInSec, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(writeTimeoutInSec, TimeUnit.SECONDS)
        return okHttpClientBuilder
    }

    private fun getGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.registerTypeAdapter(
            Double::class.java,
            JsonSerializer<Double?> { src, typeOfSrc, context ->
                val value = (src!!).roundToInt()
                JsonPrimitive(value)
            })

        return gsonBuilder.create()
    }

    private fun getRetrofitClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClientBuilder.build())
        .baseUrl(baseUrl)
        .build()
}