package org.smu.blood.database

class ReviewLike {
    var id: Int? = null
    var userId: String? = null
    var reviewId: String? = null

    constructor()
    constructor(id: Int?, userId: String?, reviewId: String?) {
        this.id = id
        this.userId = userId
        this.reviewId = reviewId
    }

    override fun toString(): String {
        return "ReviewLike(id=$id, userId=$userId, reviewId=$reviewId)"
    }


}