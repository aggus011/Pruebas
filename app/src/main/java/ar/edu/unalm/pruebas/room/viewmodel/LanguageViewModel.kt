package ar.edu.unalm.pruebas.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ar.edu.unalm.pruebas.entities.Language
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.text.diff.StringsComparator
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val languageRepository: ar.edu.unalm.pruebas.room.repository.LanguageRepository) :
    ViewModel() {

    val allLanguage: MutableLiveData<MutableList<Language>> = MutableLiveData(mutableListOf())

    fun getAllLanguage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val call = languageRepository.getAllLanguages()
            Log.d(TAG, "entra ${call.body()}")
            Log.d(TAG, "a ${call.code()}")
            Log.d(TAG, "a ${call.headers()}")
            if (call.body() != null)
                allLanguage.postValue(call.body()!!.supported_languages.toMutableList())
        } catch (e: NullPointerException) {
            Log.e(TAG, "NullPointerException $e")
        } catch (e: Exception) {
            Log.e(TAG, "Error $e")
        }
    }

/*
    fun encontrarDiferencias(texto1: String, texto2: String): Pair<List<String>, List<String>> {
        val comparator = StringsComparator()
        val diff = comparator.diffMain(texto1, texto2)

        val palabrasTexto1 = mutableListOf<String>()
        val palabrasTexto2 = mutableListOf<String>()

        for (delta in diff.deltas) {
            when (delta.operation) {
                StringsComparator.DeltaOperation.DELETE -> {
                    val palabrasBorradas = delta.original.lines.joinToString(" ").split(" ")
                    palabrasTexto1.addAll(palabrasBorradas)
                }
                StringsComparator.DeltaOperation.INSERT -> {
                    val palabrasInsertadas = delta.revised.lines.joinToString(" ").split(" ")
                    palabrasTexto2.addAll(palabrasInsertadas)
                }
                else -> Unit
            }
        }

        return Pair(palabrasTexto1, palabrasTexto2)
    }
*/
    companion object {
        const val TAG = "LanguageViewModel"
    }
}