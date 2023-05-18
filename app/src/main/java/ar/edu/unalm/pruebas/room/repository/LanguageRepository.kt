package ar.edu.unalm.pruebas.room.repository

import ar.edu.unalm.pruebas.entities.SupportedLanguage
import retrofit2.Response
import ar.edu.unalm.pruebas.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): Response<SupportedLanguage> {
        return FluentAPI.getInstance().getAllLanguages()
    }
}