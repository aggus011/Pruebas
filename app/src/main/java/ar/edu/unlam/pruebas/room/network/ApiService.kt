package ar.edu.unlam.pruebas.room.network

<<<<<<< HEAD:app/src/main/java/ar/edu/unalm/pruebas/room/network/ApiService.kt
import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.entities.SupportedLanguage
=======
import ar.edu.unlam.pruebas.entities.Language
>>>>>>> 3bdd2e347d2be9af111238832daff77adb652f0e:app/src/main/java/ar/edu/unlam/pruebas/room/network/ApiService.kt
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("language")
    suspend fun getAllLenguages() : Response<SupportedLanguage>
}