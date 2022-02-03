package org.smu.blood.ui.board

data class BoardData (
    val id : String,   //게시글 id (댓글과 공동 키)
    val title : String,
    val nickname : String,
    val time : String,      //게시글 작성 시간
    val boardtext : String,  //게시글 본문
    val heartcount : Int,    //하트 개수
    val commentcount : Int    //댓글 개수
)