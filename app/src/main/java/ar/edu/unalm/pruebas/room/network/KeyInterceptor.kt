package ar.edu.unalm.pruebas.room.network

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()

        val newUrl = url.newBuilder()
            .addQueryParameter("x-access-token", "20230418130730-fQaj7AdeKhp-67524")
            .addQueryParameter("Authorization", "Basic YWdndXMwMTE6VHQ0Mi44NjEuNzU4")
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}