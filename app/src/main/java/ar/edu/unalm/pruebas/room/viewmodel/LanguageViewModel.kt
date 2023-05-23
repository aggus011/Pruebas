package ar.edu.unalm.pruebas.room.viewmodel

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.core.graphics.red
import androidx.core.text.backgroundColor
import androidx.core.text.color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import ar.edu.unalm.pruebas.R
import dagger.hilt.android.lifecycle.HiltViewModel
import ar.edu.unalm.pruebas.entities.Language
import ar.edu.unalm.pruebas.entities.Rows
import com.google.android.material.tabs.TabLayout.TabGravity
import difflib.Delta
import difflib.DiffUtils
import difflib.Patch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.Diff
import org.apache.commons.text.diff.EditScript
import org.apache.commons.text.diff.StringsComparator
import java.lang.Error
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@HiltViewModel
class LanguageViewModel @Inject constructor(private val languageRepository: ar.edu.unalm.pruebas.room.repository.LanguageRepository) :
    ViewModel() {

    val allLanguage: MutableLiveData<MutableList<Language>> = MutableLiveData(mutableListOf())
    val difference: MutableLiveData<Rows> = MutableLiveData()

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
    fun getDifference(originalText: String, revisedText: String) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = languageRepository.getDifference(originalText, revisedText)
                if (result.body() != null)
                    difference.postValue(result.body()!!)
            }catch (e: NullPointerException){
                Log.e(TAG, "Call is null $e")
            }
            catch (e: Error){
                Log.e(TAG, "Error! $e")
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


    @SuppressLint("ResourceAsColor")
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

        for (i in 0 until diffCallback.oldListSize) {
            diffLines.add(
                if (!diffCallback.areItemsTheSame(i, i)) {
                    diffResult.convertNewPositionToOld(i)
                    revisedLines[i]
                } else {
                    "<android:textColor='#FF0000'>" + originalLines[i]
                }
            )
        }

        val diffBuilder = SpannableStringBuilder()
        diffLines.forEachIndexed { index, line ->
            diffBuilder.append(line)
            if (index != diffLines.size - 1) {
                diffBuilder.append("\n")
            }
        }

        return diffBuilder
    }

    fun findTextDifferences2(originalText: String, revisedText: String): String {
        val originalLines = originalText.lines()
        val revisedLines = revisedText.lines()

        val patch: Patch<String> = DiffUtils.diff(originalLines, revisedLines)

        val diffBuilder = StringBuilder()

        patch.deltas.forEach { delta ->
            when (delta.type) {
                Delta.TYPE.CHANGE -> {
                    val originalLinesRange =
                        "${delta.original.lines.size} line(s) starting at line ${delta.original.position + 1}"
                    val revisedLinesRange =
                        "${delta.revised.lines.size} line(s) starting at line ${delta.revised.position + 1}"
                    diffBuilder.append("Changed: Original ($originalLinesRange) -> Revised ($revisedLinesRange)\n")
                    diffBuilder.append(delta.original.lines.joinToString("\n"))
                    diffBuilder.append("\n---\n")
                    diffBuilder.append(delta.revised.lines.joinToString("\n"))
                    diffBuilder.append("\n\n")
                }

                Delta.TYPE.DELETE -> {
                    val linesRange =
                        "${delta.original.lines.size} line(s) starting at line ${delta.original.position + 1}"
                    diffBuilder.append("Deleted: $linesRange\n")
                    diffBuilder.append(delta.original.lines.joinToString("\n"))
                    diffBuilder.append("\n\n")
                }

                Delta.TYPE.INSERT -> {
                    val linesRange =
                        "${delta.revised.lines.size} line(s) starting at line ${delta.revised.position + 1}"
                    diffBuilder.append("Inserted: $linesRange\n")
                    diffBuilder.append(delta.revised.lines.joinToString("\n"))
                    diffBuilder.append("\n\n")
                }
            }
        }

        return diffBuilder.toString()
    }

    fun findTextDifferences3(originalText: String, revisedText: String): String {
        val originalWords = originalText.split("\\s+".toRegex())
        val revisedWords = revisedText.split("\\s+".toRegex())

        val patch: Patch<String> = DiffUtils.diff(originalWords, revisedWords)

        val diffBuilder = StringBuilder()

        patch.deltas.forEach { delta ->
            when (delta.type) {
                Delta.TYPE.CHANGE -> {
                    val originalWordsRange =
                        "${delta.original.lines.size} word(s) starting at position ${delta.original.position + 1}"
                    val revisedWordsRange =
                        "${delta.revised.lines.size} word(s) starting at position ${delta.revised.position + 1}"
                    diffBuilder.append("Changed: Original ($originalWordsRange) -> Revised ($revisedWordsRange)\n")

                    val originalWordsList = delta.original.lines.joinToString(" ")
                    val revisedWordsList = delta.revised.lines.joinToString(" ")

                    diffBuilder.append(
                        originalWordsList.highlightDifferences(
                            originalText,
                            revisedText
                        )
                    )
                    diffBuilder.append("\n\n")
                }

                Delta.TYPE.DELETE -> {
                    val wordsRange =
                        "${delta.original.lines.size} word(s) starting at position ${delta.original.position + 1}"
                    diffBuilder.append("Deleted: $wordsRange\n")

                    val deletedWordsList = delta.original.lines.joinToString(" ")
                    diffBuilder.append(deletedWordsList.highlightDeletedWords())
                    diffBuilder.append("\n\n")
                }

                Delta.TYPE.INSERT -> {
                    val wordsRange =
                        "${delta.revised.lines.size} word(s) starting at position ${delta.revised.position + 1}"
                    diffBuilder.append("Inserted: $wordsRange\n")

                    val insertedWordsList = delta.revised.lines.joinToString(" ")
                    diffBuilder.append(insertedWordsList.highlightInsertedWords())
                    diffBuilder.append("\n\n")
                }
            }
        }

        return diffBuilder.toString()
    }

    fun String.highlightDifferences(originalText: String, revisedText: String): String {
        Log.d(TAG, "pasa por aca")
        val originalWords = originalText.split("\\s+".toRegex())
        val revisedWords = revisedText.split("\\s+".toRegex())

        val diffBuilder = StringBuilder()

        var originalIndex = 0
        var revisedIndex = 0

        while (originalIndex < originalWords.size && revisedIndex < revisedWords.size) {
            val originalWord = originalWords[originalIndex]
            val revisedWord = revisedWords[revisedIndex]

            if (originalWord == revisedWord) {
                diffBuilder.append("$originalWord ")
                originalIndex++
                revisedIndex++
            } else {
                diffBuilder.append("<font color='#FF0000'>$originalWord</font> ")
                originalIndex++
            }
        }

        while (originalIndex < originalWords.size) {
            diffBuilder.append("<font color='#FF0000'>$originalWords[originalIndex]</font> ")
            originalIndex++
        }

        return diffBuilder.toString()
    }

    fun String.highlightDeletedWords(): String {
        val words = this.split("\\s+".toRegex())

        val diffBuilder = StringBuilder()

        words.forEach { word ->
            diffBuilder.append("<font color='#FF0000'>$word</font> ")
        }

        return diffBuilder.toString()
    }

    fun String.highlightInsertedWords(): String {
        val words = this.split("\\s+".toRegex())

        val diffBuilder = StringBuilder()

        words.forEach { word ->
            diffBuilder.append("<font color='#FF0000'>$word</font> ")
        }

        return diffBuilder.toString()
    }

    fun findTextDifferences4(originalText: String, revisedText: String): String {
        val originalWords = originalText.split("\\s+".toRegex())
        val revisedWords = revisedText.split("\\s+".toRegex())

        val diffBuilder = StringBuilder()

        var revisedIndex = 0

        while (revisedIndex < revisedWords.size) {
            val revisedWord = revisedWords[revisedIndex]

            if (originalWords.contains(revisedWord)) {
                diffBuilder.append("$revisedWord ")
            } else {
                diffBuilder.append("<font color='#FF0000'>$revisedWord</font> ")

                // Verificar palabras siguientes en caso de ser iguales
                var nextIndex = revisedIndex + 1
                while (nextIndex < revisedWords.size && !originalWords.contains(revisedWords[nextIndex])) {
                    diffBuilder.append("<font color='#FF0000'>${revisedWords[nextIndex]}</font> ")
                    nextIndex++
                }

                revisedIndex = nextIndex
                continue
            }

            revisedIndex++
        }

        return diffBuilder.toString()
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