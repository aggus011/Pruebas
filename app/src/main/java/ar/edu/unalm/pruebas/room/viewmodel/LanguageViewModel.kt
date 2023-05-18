package ar.edu.unalm.pruebas.room.viewmodel

import android.text.SpannableStringBuilder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import ar.edu.unalm.pruebas.entities.Language
import com.google.android.material.tabs.TabLayout.TabGravity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.Diff
import org.apache.commons.text.diff.EditScript
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


    fun findTextDifferences(originalText: String, revisedText: String): SpannableStringBuilder {
        val originalLines = originalText.lines()
        val revisedLines = revisedText.lines()

        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return originalLines.size
            }

            override fun getNewListSize(): Int {
                return revisedLines.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return originalLines[oldItemPosition] == revisedLines[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return originalLines[oldItemPosition] == revisedLines[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        val diffLines = mutableListOf<String>()

        /*for (i in 0 until diffResult.oldListSize) {
            diffLines.add(if (diffResult.areContentsTheSame(i, i)) {
                originalLines[i]
            } else {
                "<font color='#FF0000'>" + originalLines[i] + "</font>"
            })
        }*/

        val diffBuilder = SpannableStringBuilder()
        diffLines.forEachIndexed { index, line ->
            diffBuilder.append(line)
            if (index != diffLines.size - 1) {
                diffBuilder.append("\n")
            }
        }

        return diffBuilder
    }

fun main() {
    val texto1 = "Este es un ejemplo de texto"
    val texto2 = "Este es otro ejemplo de texto"

    val comparator = StringsComparator(texto1, texto2)



    //val diff = comparator.diffMain(texto1, texto2)

    /*for (delta in diff.deltas) {
        when (delta.operation) {
            DeltaOperation.DELETE -> {
                println("Palabras sobrantes en texto1: ${delta.original.lines.joinToString(" ")}")
            }
            DeltaOperation.INSERT -> {
                println("Palabras sobrantes en texto2: ${delta.revised.lines.joinToString(" ")}")
            }
            else -> Unit
        }
    }*/
}
    companion object {
        const val TAG = "LanguageViewModel"
    }
}