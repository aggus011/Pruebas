package ar.edu.unalm.pruebas.room.network

import ar.edu.unalm.pruebas.entities.SupportedLanguage
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("language")
    suspend fun getAllLanguages() : Response<SupportedLanguage>
}