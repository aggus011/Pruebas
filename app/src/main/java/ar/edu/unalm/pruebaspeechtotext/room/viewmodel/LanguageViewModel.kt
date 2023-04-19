package ar.edu.unalm.pruebaspeechtotext.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.unalm.pruebaspeechtotext.entities.Language
import ar.edu.unalm.pruebaspeechtotext.room.repository.LanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
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