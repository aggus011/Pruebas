package ar.edu.unalm.pruebas.room.network

import ar.edu.unalm.pruebas.entities.Rows
import ar.edu.unalm.pruebas.entities.SendInformation
import ar.edu.unalm.pruebas.entities.SupportedLanguage
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("language")
    suspend fun getAllLanguages(): Response<SupportedLanguage>

    @POST("text?output_type=json&email=abmaldonadoarce@gmail.com")
    suspend fun getDifference(@Body SendInformation: SendInformation): Response<Rows>
}