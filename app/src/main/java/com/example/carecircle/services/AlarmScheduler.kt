package com.example.carecircle.services

import com.example.carecircle.data.Medication

interface AlarmScheduler {
    fun schedule(medication: Medication)
    fun cancel(medication: Medication)
}