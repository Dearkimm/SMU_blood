package org.smu.blood.api.database

class Apply {
    var applyId: Int? = null
    var userId: String? = null
    var requestId: Int? = null
    var applyDate: String? = null

    constructor()

    constructor(id: Int?, userId: String?, requestId: Int?, applyDate: String?) {
        this.applyId = id
        this.userId = userId
        this.requestId = requestId
        this.applyDate = applyDate
    }

    override fun toString(): String {
        return "apply(id=$applyId, userId=$userId, requestId=$requestId, applyDate=$applyDate)"
    }


}