package ar.edu.unlam.pruebas.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
<<<<<<< HEAD:app/src/main/java/ar/edu/unalm/pruebas/room/viewmodel/LanguageViewModel.kt
import androidx.lifecycle.ViewModelProvider
import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.room.repository.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
=======
import ar.edu.unlam.pruebas.entities.Language
import ar.edu.unlam.pruebas.room.repository.LanguageRepository
>>>>>>> 3bdd2e347d2be9af111238832daff77adb652f0e:app/src/main/java/ar/edu/unlam/pruebas/room/viewmodel/LanguageViewModel.kt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val languageRepository: LanguageRepository) :
    ViewModel() {

    val allLanguage: MutableLiveData<MutableList<Language>> = MutableLiveData(mutableListOf())

    fun getAllLanguage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val call = languageRepository.getAllLanguages()
            if (call.body() != null)
                allLanguage.postValue(call.body()!!.supported_languages.toMutableList())
        } catch (e: NullPointerException) {
            Log.e(TAG, "NullPointerException $e")
        } catch (e: Exception) {
            Log.e(TAG, "Error $e")
        }
    }

    companion object {
        const val TAG = "LanguageViewModel"
    }
}