package ar.edu.unalm.pruebas.entities

data class Rows(
    val rows: List<DifferenceInformation>,
    val added: Int,
    val removed: Int
)
