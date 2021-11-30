package org.smu.blood.api.database

data class MyCardItem (
    val hospitalId: Int,
    val rhType: Boolean,
    val bloodType: Int,
    val startDate: String,
    val endDate: String,
    val count: Int,
    val content: String,
)