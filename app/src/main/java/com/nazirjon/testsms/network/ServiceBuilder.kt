package com.desiredsoftware.currencywatcher.data.api

import com.nazirjon.testsms.model.AuthResponse
import com.nazirjon.testsms.model.CodeResponse
import com.nazirjon.testsms.model.InfoResponse
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ServiceBuilder {

    @GET("requestSMSCodeClient")
    fun requestSMSCodeClient(@Query("phone_number") phone_number: String): Single<CodeResponse>

    @POST("authenticateClients")
    fun authenticateClients(@Query("phone_number") phone_number: String,
                            @Query("password") password: String): Single<AuthResponse>

    @GET("client/getInfo")
    fun getInfo(@Query("token") token: String): Single<InfoResponse>

    companion object ServiceFactory {

        // Создание Logger
        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Создание Custom Interceptor применять Headers
        val headerInterceptor = object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = request.newBuilder()
                    .build()
                val response = chain.proceed(request)
                return response
            }
        }

        // Создание OkHttp Client
        private val okHttp = OkHttpClient.Builder()
            .callTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(logger)

        // Создание Retrofit
        fun create(baseUrl: String): ServiceBuilder {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttp.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ServiceBuilder::class.java)
        }
    }
}