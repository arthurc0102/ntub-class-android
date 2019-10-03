package me.arthurc.ntub_class.model

import com.google.gson.annotations.SerializedName

data class ClassScheduleModel(
    @SerializedName("class")
    val klass: List<List<ClassModel?>>,

    val time: List<TimeModel>
)