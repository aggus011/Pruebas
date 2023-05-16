package ar.edu.unlam.pruebas.room.repository

import ar.edu.unlam.pruebas.entities.SupportedLanguage
import retrofit2.Response
import ar.edu.unlam.pruebas.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): Response<SupportedLanguage> {
        return FluentAPI.getInstance().getAllLenguages()
    }
}