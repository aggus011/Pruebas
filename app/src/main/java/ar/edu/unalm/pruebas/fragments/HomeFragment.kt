package ar.edu.unalm.pruebas.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.edu.unalm.pruebas.databinding.FragmentHomeBinding
import ar.edu.unalm.pruebas.room.viewmodel.LanguageViewModel
import com.github.difflib.text.DiffRow
import dagger.hilt.android.AndroidEntryPoint
import difflib.Delta
import difflib.DiffUtils
import difflib.Patch
import org.apache.commons.text.diff.StringsComparator
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //view models
    private val languageViewModel by activityViewModels<LanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private lateinit var document1: String
    private lateinit var document2: String


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.voiceText.text = result!![0]
            }
        }

    override fun onStart() {
        super.onStart()

        binding.microphoneButton.setOnClickListener {
            binding.voiceText.text = ""
            heardVoice()
        }
        document1 =
            "En un rincón remoto del mundo, donde las montañas rugen y los ríos serpentean, vivía una joven arriesgada y valiente llamada Clara. Su espíritu aventurero la impulsaba a explorar cada rincón recóndito de la tierra. Recorría frondosos bosques, escalaba altas cumbres y navegaba en frágiles barcas por aguas bravías. Un día, mientras caminaba por un estrecho sendero, escuchó un suave rumor proveniente de una cueva. Intrigada, se adentró en ella sin dudarlo. Allí descubrió un tesoro resplandeciente, oculto durante siglos. Rodeada de joyas relucientes y monedas antiguas, su corazón saltaba de alegría. Sin embargo, una criatura formidable y misteriosa, conocida como el Dragón de las mil rocas, custodiaba celosamente aquel tesoro. Con su aliento de fuego y sus garras afiladas, desafiaba a cualquier intruso que osara acercarse. Clara, sin retroceder, decidió enfrentarse al temible dragón. Con su ingenio y coraje, trazó un plan para distraerlo y arrebatar el tesoro. Usó sus habilidades persuasivas y logró que el dragón se rindiera ante su astucia. Con el tesoro en sus manos, Clara regresó a su hogar, convertida en una leyenda viviente. Su valentía y determinación la convirtieron en un ejemplo para todos aquellos que anhelaban superar los obstáculos y alcanzar sus sueños. Así, con cada aventura, Clara demostraba que no había reto demasiado grande ni peligro demasiado fuerte para una persona decidida. Su historia resonaba en los corazones de aquellos que escuchaban, inspirando a todos a perseguir sus propias metas y desafiar los límites impuestos. Y así, el nombre de Clara se propagó de boca en boca, llevando consigo el mensaje de que solo aquellos dispuestos a enfrentar los desafíos con audacia y resiliencia podrían alcanzar la grandeza."

        document2 =
            "En un rincón lejano del mundo, donde las montañas ríen y los ríos serpentean, vivía una joven intrépida y valerosa llamada Ana. Su espíritu aventurero la impulsaba a explorar cada esquina oculta de la tierra. Recorría frondosos bosques, ascendía altas cumbres y navegaba en frágiles botes por aguas turbulentas. Un día, mientras caminaba por un estrecho camino, escuchó un suave susurro proveniente de una gruta. Intrigada, se adentró en ella sin dudarlo. Allí descubrió un tesoro deslumbrante, escondido durante décadas. Rodeada de joyas brillantes y monedas antiguas, su corazón latía de felicidad. Sin embargo, una criatura formidable y misteriosa, conocida como el Dragón de las rocas eternas, custodiaba celosamente aquel tesoro. Con su aliento de fuego y sus garras afiladas, desafiaba a cualquier intruso que osara acercarse. Ana, sin retroceder, decidió enfrentarse al temido dragón. Con su ingenio y coraje, ideó un plan para distraerlo y arrebatar el tesoro. Utilizó sus habilidades persuasivas y logró que el dragón se rindiera ante su astucia. Con el tesoro en sus manos, Ana regresó a su hogar, convertida en una leyenda viva. Su valentía y determinación la convertían en un ejemplo para todos aquellos que deseaban superar los obstáculos y alcanzar sus sueños. Así, con cada hazaña, Ana demostraba que no había desafío demasiado grande ni peligro demasiado intimidante para una persona decidida. Su historia resonaba en los corazones de quienes escuchaban, inspirando a todos a perseguir sus propias metas y desafiar los límites establecidos. Y así, el nombre de Ana se difundía de boca en boca, llevando consigo el mensaje de que solo aquellos dispuestos a enfrentar los desafíos con audacia y resiliencia podrían alcanzar la grandeza."

        languageViewModel.getDifference(document1, document2)

        languageViewModel.difference.observe(viewLifecycleOwner) {
            val textRevised = languageViewModel.convertToText(document1)
            binding.comparedTV.text =
                HtmlCompat.fromHtml(textRevised, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    @Suppress("DEPRECATION")
    private fun heardVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH)
        } catch (e: Error) {
            Log.e(TAG, "Error!")
        }
    }

    companion object {
        const val TAG = "HomeFragment"
        private const val REQ_CODE_SPEECH = 100
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SPEECH && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.voiceText.text = result?.get(0).toString()
        }
    }

}