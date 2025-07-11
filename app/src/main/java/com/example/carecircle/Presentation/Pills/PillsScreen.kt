package com.example.carecircle.Presentation.Pills

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carecircle.Presentation.Today.MedicineCard
import com.example.carecircle.Presentation.Today.MedicineViewModel

@Composable
fun PillsScreen(medicineViewModel: MedicineViewModel , navController: NavController)
{
    val curr by medicineViewModel.medications.collectAsState()
    Column(Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(curr , key ={it.id})
            { it ->
                MedicineCard(medicine = it, { medicineViewModel.removeMed(it) }, "Delete")

            }
        }
        Button(onClick = { navController.navigate("addpill") }, modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Text("Add Pill")
        }
    }
}
