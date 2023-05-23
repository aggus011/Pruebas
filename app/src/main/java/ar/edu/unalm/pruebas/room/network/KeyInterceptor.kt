package ar.edu.unalm.pruebas.room.network

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()

        val newUrl = url.newBuilder()
            .build()

        return chain.proceed(
            request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(newUrl)
                .build()
        )
    }
}