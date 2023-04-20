package ar.edu.unalm.pruebas.room.network

import ar.edu.unalm.pruebas.entities.Language
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("lenguage")
    suspend fun getAllLenguages() : Response<List<Language>>
}