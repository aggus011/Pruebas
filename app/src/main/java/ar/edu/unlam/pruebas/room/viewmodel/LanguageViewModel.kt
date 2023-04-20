package ar.edu.unlam.pruebas.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.unlam.pruebas.entities.Language
import ar.edu.unlam.pruebas.room.repository.LanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import java.util.Collections.emptyList
import javax.inject.Inject

class LanguageViewModel @Inject constructor(private val languageRepository: LanguageRepository) :
    ViewModel() {

    val allLanguage: MutableLiveData<List<Language>?> = MutableLiveData(emptyList())

    fun getAllLanguage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val call = languageRepository.getAllLanguages()
            allLanguage.postValue(call)

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