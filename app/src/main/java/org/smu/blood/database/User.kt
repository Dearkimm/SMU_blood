package org.smu.blood.database

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class User {
    var id: String? = null
    var password: String? = null
    var nickname: String? = null
    var bloodType: Int? = null
    var rhType: Boolean? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(
        id: String?,
        password: String?,
        nickname: String?,
        bloodType: Int?,
        rhType: Boolean?
    ) {
        this.id = id
        this.password = password
        this.nickname = nickname
        this.bloodType = bloodType
        this.rhType = rhType
    }

    override fun toString(): String {
        return "User(id=$id, password=$password, nickname=$nickname, bloodType=$bloodType, rhType=$rhType)"
    }


}