package ar.edu.unalm.pruebas.entities

data class DifferenceInformation(
    val end: Boolean,
    val left: Chunks,
    val right: Chunks,
    val insideChanged: Boolean,
    var start: Boolean
    )
