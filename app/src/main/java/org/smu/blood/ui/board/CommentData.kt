package org.smu.blood.ui.board

data class CommentData( //댓글 데이터
    val commentId : Int,
    val reviewId : Int,
    val userId: String,
    val nickname : String,
    val time : String, //댓글 쓴 날짜
    val comment : String
)
