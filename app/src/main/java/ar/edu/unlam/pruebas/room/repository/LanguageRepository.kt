package ar.edu.unlam.pruebas.room.repository

<<<<<<< HEAD:app/src/main/java/ar/edu/unalm/pruebas/room/repository/LanguageRepository.kt
import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.entities.SupportedLanguage
import ar.edu.unalm.pruebas.room.network.FluentAPI
import retrofit2.Response
=======
import ar.edu.unlam.pruebas.entities.Language
import ar.edu.unlam.pruebas.room.network.FluentAPI
>>>>>>> 3bdd2e347d2be9af111238832daff77adb652f0e:app/src/main/java/ar/edu/unlam/pruebas/room/repository/LanguageRepository.kt
import javax.inject.Inject

class LanguageRepository @Inject constructor() {


    suspend fun getAllLanguages(): Response<SupportedLanguage> {
        return FluentAPI.getInstance().getAllLenguages()
    }
}