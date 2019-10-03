package me.arthurc.ntub_class.model

import com.google.gson.annotations.SerializedName

data class TimeModel(
    @SerializedName("class_no")
    val classNo: String,

    @SerializedName("start_at")
    val startAt: String,

    @SerializedName("end_at")
    val endAt: String
)