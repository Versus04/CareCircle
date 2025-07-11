package com.example.carecircle.Presentation.Pills

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carecircle.Presentation.Today.MedicineViewModel
import com.example.carecircle.Services.DialWithDialogExample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPillScreen(
    viewModel: MedicineViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00") } // default value
    var showTimeDialog by remember { mutableStateOf(false) }

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
                enabled = false, // optional: makes it look more like a display field
                modifier = Modifier.fillMaxWidth()
            )
        }


        // 👇 Show your custom dialog here
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
                    viewModel.addMedication(name, dosage, time)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Pill")
        }
    }
}
