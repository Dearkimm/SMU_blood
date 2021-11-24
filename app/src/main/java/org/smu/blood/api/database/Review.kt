package org.smu.blood.api.database

class Review {
    var id: Int? = null
    var userId: String? = null
    var title: String? = null
    var writeDate: String? = null
    var writeTime: String? = null
    var contents: String? = null
    var likeNum: Int? = null

    constructor()

    constructor(
        id: Int?,
        userId: String?,
        title: String?,
        writeDate: String?,
        writeTime: String?,
        contents: String?,
        likeNum: Int?
    ) {
        this.id = id
        this.userId = userId
        this.title = title
        this.writeDate = writeDate
        this.writeTime = writeTime
        this.contents = contents
        this.likeNum = likeNum
    }

    override fun toString(): String {
        return "Review(id=$id, userId=$userId, title=$title, writeDate=$writeDate, writeTime=$writeTime, contents=$contents, likeNum=$likeNum)"
    }


}