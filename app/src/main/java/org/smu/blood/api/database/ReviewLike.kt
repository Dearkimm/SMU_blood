package org.smu.blood.api.database

class ReviewLike {
    var id: Int? = null
    var reviewId: Int? = null
    var userId: String? = null
    var heartState: Boolean? = false

    constructor()
    constructor(id: Int?, reviewId: Int?, userId: String?, heartState: Boolean?) {
        this.id = id
        this.userId = userId
        this.reviewId = reviewId
        this.heartState = heartState
    }

    override fun toString(): String {
        return "ReviewLike(id=$id, reviewId=$reviewId, userId=$userId, heartState=$heartState)"
    }


}