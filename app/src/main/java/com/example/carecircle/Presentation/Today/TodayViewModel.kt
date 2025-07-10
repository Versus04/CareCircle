package com.example.carecircle.Presentation.Today

import androidx.lifecycle.ViewModel
import com.example.carecircle.data.Medication
import com.example.carecircle.data.TodayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodayViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TodayUiState(
        user = "Shubham",
        isLoading = false,
        medications =getFakeMedicines(),
        errorMessage = "no error"
    ))
    val uiState: StateFlow<TodayUiState> = _uiState
    fun getFakeMedicines() : List<Medication>
    {
        return listOf(
            Medication(name = "Paracetamol", dosage = "500mg", time = "8:00 AM"),
            Medication(name = "Amoxicillin", dosage = "250mg", time = "12:00 PM"),
            Medication(name = "Vitamin D3", dosage = "1000 IU", time = "6:00 PM"),
            Medication(name = "Ibuprofen", dosage = "400mg", time = "9:00 PM"),
            Medication(name = "Metformin", dosage = "850mg", time = "7:00 AM")
        )
    }

}