package ar.edu.unalm.pruebas.room.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FluentAPI {



    companion object{
        private const val baseUrl = "https://api.diffchecker.com/public/"
        private fun buildService(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(KeyInterceptor())
                .build()
            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit: Retrofit = builder.build()
            return retrofit.create(ApiService::class.java)
        }


        @Volatile
        private var INSTANCE: ApiService? = null

        fun getInstance(): ApiService =
            INSTANCE ?: buildService().also {
                INSTANCE = it
            }
    }
}