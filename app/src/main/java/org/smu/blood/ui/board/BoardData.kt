package org.smu.blood.ui.board

data class BoardData (
    var boardId : String,   //게시글 id (댓글과 공동 키)
    var title : String,
    var nickname : String,
    var time : String,      //게시글 작성 시간
    var boardtext : String,  //게시글 본문
    var heartcount : String,    //하트 개수
    var commentcount : String    //댓글 개수
)