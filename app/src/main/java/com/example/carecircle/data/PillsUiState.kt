package com.example.carecircle.data

data class PillsUiState(
    val medications: List<Medication> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
