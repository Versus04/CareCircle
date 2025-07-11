package com.example.carecircle.Presentation.Today

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.carecircle.data.Medication
import com.example.carecircle.data.TodayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MedicineViewModel : ViewModel() {
    private val _meds = MutableStateFlow<List<Medication>>(getFakeMedicines())
    val medications: StateFlow<List<Medication>> = _meds

    fun getFakeMedicines() : List<Medication>
    {
        return listOf(
            Medication(name = "Paracetamol", dosage = "500mg", time = "8:00 AM", taken = false),
            Medication(name = "Amoxicillin", dosage = "250mg", time = "12:00 PM",taken = false),
            Medication(name = "Vitamin D3", dosage = "1000 IU", time = "6:00 PM",taken = false),
            Medication(name = "Ibuprofen", dosage = "400mg", time = "9:00 PM",taken = false),
            Medication(name = "Metformin", dosage = "850mg", time = "7:00 AM",taken = false)
        )
    }
    fun addmedications(med: Medication)
    {
        _meds.update { currentList ->
            currentList+med
        }
    }


    fun markAsTaken(med: Medication) {
        _meds.update { state ->
            state.map {
                if (it.id == med.id) it.copy(taken = true) else it
            }
        }
    }
    fun removeMed(med: Medication)
    {
    _meds.update { currentList ->
        currentList.filter { it.id != med.id }

    }
    }



}