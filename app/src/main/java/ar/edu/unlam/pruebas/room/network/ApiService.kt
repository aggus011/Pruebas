package ar.edu.unlam.pruebas.room.network


import ar.edu.unlam.pruebas.entities.SupportedLanguage
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("language")
    suspend fun getAllLenguages() : Response<SupportedLanguage>
}