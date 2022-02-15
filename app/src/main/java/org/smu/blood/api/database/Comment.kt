package org.smu.blood.api.database

class Comment {
    var commentId: Int? = null
    var reviewId: Int? = null
    var nickname: String? = null
    var time: String? = null
    var comment: String? = null

    constructor()

    constructor(
        commentId: Int?,
        reviewId: Int?,
        nickname: String?,
        time: String?,
        comment: String?
    ) {
        this.commentId = commentId
        this.reviewId = reviewId
        this.nickname = nickname
        this.time = time
        this.comment = comment
    }

    override fun toString(): String {
        return "Comment(commentId=$commentId, reviewId=$reviewId, nickname=$nickname, time=$time, comment=$comment)"
    }
}