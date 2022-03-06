package org.smu.blood.api.database

class Notification {
    var noticeId: Int? = null
    var requestId: Int? = null
    var userId: String? = null
    var notTime: String? = null
    var notState: Boolean? = null

    constructor()
    constructor(noticeId: Int?, requestId: Int?, userId: String?, notTime: String?, notState: Boolean?){
        this.noticeId = noticeId
        this.requestId = requestId
        this.userId = userId
        this.notTime = notTime
        this.notState = notState
    }

    override fun toString(): String {
        return "Notification(noticeId:$noticeId, requestId: $requestId, userId: $userId, notTime:$notTime, notState: $notState)"
    }
}