package com.example.carecircle.Presentation.History

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.carecircle.Presentation.Today.MedicineViewModel
import com.example.carecircle.data.Medication

@Composable
fun HistoryScreen(medicineViewModel: MedicineViewModel) {
    val meds by medicineViewModel.medications.collectAsState()

    val takenMeds = meds.filter { it.taken }

    LazyColumn {
        items(takenMeds) { med ->
            HistoryCard(
                med,

            )
        }
    }
}
@Composable
fun HistoryCard(medicine: Medication) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("💊 ${medicine.name}", fontWeight = FontWeight.Bold)
            Text("Dosage: ${medicine.dosage}")
            Text("Time Taken: ${medicine.takenAt}")
        }
    }
}

