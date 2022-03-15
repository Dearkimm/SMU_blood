package org.smu.blood.api.database

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

    //혈액형: 1 -> a, 2 -> b, 3-> o, 4-> ab
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