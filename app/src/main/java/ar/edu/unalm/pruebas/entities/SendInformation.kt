package ar.edu.unalm.pruebas.entities

data class SendInformation(
    val left: String,
    val right: String,
    val diff_level: String = "words"
)
