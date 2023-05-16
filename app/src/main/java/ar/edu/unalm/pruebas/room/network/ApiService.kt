package ar.edu.unalm.pruebas.room.network

import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.entities.SupportedLanguage
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("language")
    suspend fun getAllLenguages() : Response<SupportedLanguage>
}