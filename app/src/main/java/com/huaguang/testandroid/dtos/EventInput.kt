package com.huaguang.testandroid.dtos

data class EventInput(
    val eventId: Long,
    val name: String,
    val remark: String,
    val mode: Mode
) {
    enum class Mode {
        INPUT, UPDATE
    }
}
