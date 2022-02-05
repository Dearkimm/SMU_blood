package org.smu.blood.api.database

class Review {
    var reviewId: Int? = null
    var userId: String? = null
    var userNickname: String? = null
    var title: String? = null
    var writeDate: String? = null
    var writeTime: String? = null
    var contents: String? = null
    var likeNum: Int? = null

    constructor()

    constructor(
        reviewId: Int?,
        userId: String?,
        userNickname: String?,
        title: String?,
        writeDate: String?,
        writeTime: String?,
        contents: String?,
        likeNum: Int?
    ) {
        this.reviewId = reviewId
        this.userId = userId
        this.userNickname = userNickname
        this.title = title
        this.writeDate = writeDate
        this.writeTime = writeTime
        this.contents = contents
        this.likeNum = likeNum
    }

    override fun toString(): String {
        return "Review(id=$reviewId, userId=$userId, userNickname=$userNickname, title=$title, writeDate=$writeDate, writeTime=$writeTime, contents=$contents, likeNum=$likeNum)"
    }


}