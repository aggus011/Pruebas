package ar.edu.unalm.pruebas.room.repository

import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): List<Language>? {
        val call = FluentAPI.getInstance().getAllLenguages()
        return call.body()
    }
}