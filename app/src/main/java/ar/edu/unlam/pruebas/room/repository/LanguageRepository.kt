package ar.edu.unlam.pruebas.room.repository

import ar.edu.unlam.pruebas.entities.Language
import ar.edu.unlam.pruebas.room.network.FluentAPI
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): List<Language>? {
        val call = FluentAPI.getInstance().getAllLenguages()
        return call.body()
    }
}