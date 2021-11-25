package org.smu.blood.model

enum class BloodType (
    val id: Int,
    val bloodType: String,
) {
    BLOOD_TYPE_A(
        1,
        "A"
    ),
    BLOOD_TYPE_B(
        2,
        "B"
    ),
    BLOOD_TYPE_O(
        3,
        "O"
    ),
    BLOOD_TYPE_AB(
        4,
        "AB"
    ),
}