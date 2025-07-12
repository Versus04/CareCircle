package com.example.carecircle.presentation.pills

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carecircle.presentation.today.MedicineCard
import com.example.carecircle.presentation.today.MedicineViewModel
import com.example.carecircle.services.AlarmScheduleImpl

@Composable
fun PillsScreen(medicineViewModel: MedicineViewModel , navController: NavController)
{
    val context = LocalContext.current
    val sch = remember { AlarmScheduleImpl(context) }
    val curr by medicineViewModel.medications.collectAsState()
    Column(Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(curr , key ={it.id})
            { it ->
                MedicineCard(medicine = it, { medicineViewModel.removeMed(it)
                                            sch.cancel(it)
                                            }, "Delete")

            }
        }
        Button(onClick = {
            navController.navigate("addpill")

                         }, modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Text("Add Pill")
        }
    }
}
