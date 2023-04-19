package ar.edu.unalm.pruebaspeechtotext.room.network

import ar.edu.unalm.pruebaspeechtotext.entities.Language
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("lenguage")
    suspend fun getAllLenguages() : Response<List<Language>>
}