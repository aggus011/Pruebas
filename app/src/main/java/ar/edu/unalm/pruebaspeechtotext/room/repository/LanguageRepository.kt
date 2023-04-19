package ar.edu.unalm.pruebaspeechtotext.room.repository

import ar.edu.unalm.pruebaspeechtotext.entities.Language
import ar.edu.unalm.pruebaspeechtotext.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): List<Language>? {
        val call = FluentAPI.getInstance().getAllLenguages()
        return call.body()
    }
}