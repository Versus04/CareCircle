package com.example.carecircle.data

data class TodayUiState( val user : String ,val isLoading: Boolean = false,val medications: List<Medication> = emptyList(),
                         val errorMessage: String? = null )
