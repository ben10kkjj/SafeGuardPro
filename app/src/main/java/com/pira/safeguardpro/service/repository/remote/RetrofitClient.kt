package com.pira.safeguardpro.service.repository.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){
    companion object{
        private lateinit var retrofit: Retrofit

        private fun getRetrofitInstace(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()

            if (!::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun<S> createService(serviceClass: Class<S>): S{
            return getRetrofitInstace().create(serviceClass)
        }
    }
}