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
                .addHeader("X-RapidAPI-Key", "aa80c24d7cmsha299a56a4e26655p1b619ejsn56d2467feec0")
                .addHeader("X-RapidAPI-Host", "thefluentme.p.rapidapi.com")
                //.addHeader("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFnZ3VzMDExIiwiZXhwIjoxNjg0MjkyNzg5fQ.zerj-WGUZxQzxL_QqiorB_5R0eoZT-77TdcLYcstI0I")
                .url(newUrl)
                .build()
        )
    }
}