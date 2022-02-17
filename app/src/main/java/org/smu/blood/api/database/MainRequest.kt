package org.smu.blood.api.database

import org.smu.blood.util.DateString
import java.util.*

data class MainRequest(
    val hospitalId: Int,
    val rhType: Boolean,
    val bloodType: Int,
    var donationType: String,
    val startDate: String,
    val endDate: String,
    val count: Int,
    val content: String,
    val updatedDate: DateString,
){

}