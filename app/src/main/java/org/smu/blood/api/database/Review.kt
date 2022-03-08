package org.smu.blood.api.database

class Review {
    var reviewId: Int? = null
    var userId: String? = null
    var nickname: String? = null
    var title: String? = null
    var contents: String? = null
    var writeTime: String? = null
    var likeNum: Int? = null
    var commentCount: Int? = null
    var deleteState: Boolean? = false

    constructor()

    constructor(
        reviewId: Int?,
        userId: String?,
        userNickname: String?,
        title: String?,
        contents: String?,
        writeTime: String?,
        likeNum: Int?,
        commentCount: Int?,
        deleteState: Boolean?
    ) {
        this.reviewId = reviewId
        this.userId = userId
        this.nickname = userNickname
        this.title = title
        this.contents = contents
        this.writeTime = writeTime
        this.likeNum = likeNum
        this.commentCount = commentCount
        this.deleteState = deleteState
    }

    override fun toString(): String {
        return "Review(id=$reviewId, userId=$userId, userNickname=$nickname, title=$title, contents=$contents, writeTime=$writeTime, likeNum=$likeNum, commentCount=$commentCount, deleteState:$deleteState)"
    }


}