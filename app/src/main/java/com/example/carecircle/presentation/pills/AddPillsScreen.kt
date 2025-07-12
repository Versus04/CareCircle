package com.example.carecircle.presentation.pills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carecircle.data.Medication
import com.example.carecircle.presentation.today.MedicineViewModel
import com.example.carecircle.services.AlarmScheduleImpl
import com.example.carecircle.services.DialWithDialogExample
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPillScreen(
    viewModel: MedicineViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00") }
    var showTimeDialog by remember { mutableStateOf(false) }
    val sch = remember { AlarmScheduleImpl(context) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Medicine", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Medicine Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage (e.g. 500mg)") },
            modifier = Modifier.fillMaxWidth()
        )

        // 👇 Time selector field
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { showTimeDialog = true }) {

            OutlinedTextField(
                value = time,
                onValueChange = {},
                readOnly = true,
                label = { Text("Dosage Time") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }


        if (showTimeDialog) {
            DialWithDialogExample(
                onConfirm = { timeState ->
                    val hour = timeState.hour
                    val minute = timeState.minute
                    time = String.format("%02d:%02d", hour, minute) // 24hr format
                    showTimeDialog = false
                },
                onDismiss = {
                    showTimeDialog = false
                }
            )
        }

        Button(
            onClick = {
                if (name.isNotBlank() && dosage.isNotBlank() && time.isNotBlank()) {
                    val medication = Medication(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        dosage = dosage,
                        time = time,
                        taken = false,
                        takenAt = null
                    )
                    viewModel.addMedication(dosage,name,time)
                    sch.schedule(medication)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Pill")
        }
    }
}
