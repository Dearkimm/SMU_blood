package org.smu.blood.api.database

class Notification {
    var userId: String? = null
    var requestId: Int? = null
    var token: String? = null
    var notState: Boolean? = null

    constructor()
    constructor(userId: String?, requestId: Int?, token: String?, notState: Boolean?){
        this.userId = userId
        this.requestId = requestId
        this.token = token
        this.notState = notState
    }

    override fun toString(): String {
        return "Notification(userId: $userId, requestId: $requestId, token: $token, notState: $notState)"
    }
}