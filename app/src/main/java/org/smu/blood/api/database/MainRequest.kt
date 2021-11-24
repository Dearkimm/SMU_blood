package org.smu.blood.api.database

data class MainRequest(
    val hospitalId: String,
    val rhType: Boolean,
    val bloodType: Int,
    val startDate: String,
    val endDate: String,
    val count: Int,
)

