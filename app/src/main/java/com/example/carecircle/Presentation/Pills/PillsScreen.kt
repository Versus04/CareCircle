package com.example.carecircle.Presentation.Pills

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.carecircle.Presentation.Today.MedicineCard
import com.example.carecircle.Presentation.Today.MedicineViewModel
import com.example.carecircle.R
import com.example.carecircle.data.Medication

@Composable
fun PillsScreen(medicineViewModel: MedicineViewModel)
{
    val curr by medicineViewModel.medications.collectAsState()
    LazyColumn {
        items(curr)
        { it->
            MedicineCard(medicine = it, {medicineViewModel.removeMed(it)} ,"Delete")

        }
    }
}
