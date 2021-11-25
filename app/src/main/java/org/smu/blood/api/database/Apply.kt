package org.smu.blood.api.database

class Apply {
    var id: Int? = null
    var userId: String? = null
    var requestId: Int? = null
    var applyDate: String? = null

    constructor()

    constructor(id: Int?, userId: String?, requestId: Int?, applyDate: String?) {
        this.id = id
        this.userId = userId
        this.requestId = requestId
        this.applyDate = applyDate
    }

    override fun toString(): String {
        return "apply(id=$id, userId=$userId, requestId=$requestId, applyDate=$applyDate)"
    }


}