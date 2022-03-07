package org.smu.blood.api.database

class Notification {
    var noticeId: Int? = null
    var requestId: Int? = null
    var userId: String? = null
    var notTime: String? = null
    var notState: Boolean? = null
    var deleteState: Boolean? = null

    constructor()
    constructor(noticeId: Int?, requestId: Int?, userId: String?, notTime: String?, notState: Boolean?, deleteState: Boolean?){
        this.noticeId = noticeId
        this.requestId = requestId
        this.userId = userId
        this.notTime = notTime
        this.notState = notState
        this.deleteState = deleteState
    }

    override fun toString(): String {
        return "Notification(noticeId:$noticeId, requestId: $requestId, userId: $userId, notTime:$notTime, notState: $notState, deleteState: $deleteState)"
    }
}