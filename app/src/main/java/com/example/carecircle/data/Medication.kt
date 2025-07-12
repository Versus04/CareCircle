package com.example.carecircle.data

import java.util.UUID

data class Medication(
    val id: String = UUID.randomUUID().toString(),
    val name : String,
    val dosage : String,
    val time : String ="",
    var taken : Boolean= false,
    val takenAt: String? = null

)
