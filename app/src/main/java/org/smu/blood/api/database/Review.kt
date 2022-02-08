package org.smu.blood.api.database

class Review {
    var reviewId: Int? = null
    var userId: String? = null
    var nickname: String? = null
    var title: String? = null
    var writeTime: String? = null
    var contents: String? = null
    var likeNum: Int? = null

    constructor()

    constructor(
        reviewId: Int?,
        userId: String?,
        userNickname: String?,
        title: String?,
        writeTime: String?,
        contents: String?,
        likeNum: Int?
    ) {
        this.reviewId = reviewId
        this.userId = userId
        this.nickname = userNickname
        this.title = title
        this.writeTime = writeTime
        this.contents = contents
        this.likeNum = likeNum
    }

    override fun toString(): String {
        return "Review(id=$reviewId, userId=$userId, userNickname=$nickname, title=$title, writeTime=$writeTime, contents=$contents, likeNum=$likeNum)"
    }


}