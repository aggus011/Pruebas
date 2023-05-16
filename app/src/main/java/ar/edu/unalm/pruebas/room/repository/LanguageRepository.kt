package ar.edu.unalm.pruebas.room.repository

import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.entities.SupportedLanguage
import ar.edu.unalm.pruebas.room.network.FluentAPI
import retrofit2.Response
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): Response<SupportedLanguage> {
        return FluentAPI.getInstance().getAllLenguages()
    }
}