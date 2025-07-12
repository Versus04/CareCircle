package com.example.carecircle.presentation.today

import androidx.lifecycle.ViewModel
import com.example.carecircle.data.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MedicineViewModel : ViewModel() {
    private val _meds = MutableStateFlow<List<Medication>>(getFakeMedicines())
    val medications: StateFlow<List<Medication>> = _meds

    fun getFakeMedicines() : List<Medication>
    {
        return listOf(
            Medication(name = "Paracetamol", dosage = "500mg", time = "08:00", taken = false),
            Medication(name = "Amoxicillin", dosage = "250mg", time = "12:00", taken = false),
            Medication(name = "Vitamin D3", dosage = "1000 IU", time = "18:00", taken = false),
            Medication(name = "Ibuprofen", dosage = "400mg", time = "21:00", taken = false),
            Medication(name = "Metformin", dosage = "850mg", time = "07:00", taken = false)
        )
    }
    fun addMedication(name:String , dosage : String , time :String)
    {
        val med = Medication(name = name , dosage = dosage , time = time )
        _meds.update { currentList ->
            currentList+med
        }
    }


    fun markAsTaken(med: Medication) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = formatter.format(Date())
        _meds.update { state ->
            state.map {
                if (it.id == med.id) it.copy(taken = true , takenAt = currentTime) else it
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