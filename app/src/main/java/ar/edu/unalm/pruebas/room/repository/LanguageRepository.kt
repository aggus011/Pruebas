package ar.edu.unalm.pruebas.room.repository

import ar.edu.unalm.pruebas.entities.Rows
import ar.edu.unalm.pruebas.entities.SendInformation
import ar.edu.unalm.pruebas.entities.SupportedLanguage
import retrofit2.Response
import ar.edu.unalm.pruebas.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): Response<SupportedLanguage> {
        return FluentAPI.getInstance().getAllLanguages()
    }

    suspend fun getDifference(originalText: String, revisedText: String) : Response<Rows>{
        val sendInformation = SendInformation(originalText, revisedText)
        return FluentAPI.getInstance().getDifference(sendInformation)
    }
}