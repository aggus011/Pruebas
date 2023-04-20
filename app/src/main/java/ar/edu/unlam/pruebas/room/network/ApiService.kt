package ar.edu.unlam.pruebas.room.network

import ar.edu.unlam.pruebas.entities.Language
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("lenguage")
    suspend fun getAllLenguages() : Response<List<Language>>
}