package org.smu.blood.ui.Notice

data class NoticeData(
    val noticeId: Int,
    val requestId: Int,
    val userId : String,
    val alert_time : String, // 알림 날짜
    val noticeState: Boolean
)
