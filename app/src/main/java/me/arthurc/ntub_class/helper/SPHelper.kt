package me.arthurc.ntub_class.helper

import android.content.Context
import com.google.gson.Gson
import me.arthurc.ntub_class.model.ClassScheduleModel

class SPHelper {
    companion object {
        private const val STD_ID = "STD_ID"
        private const val CLASS_SCHEDULE = "CLASS_SCHEDULE"

        fun setStdId(context: Context, stdId: String) {
            context.getSharedPreferences(STD_ID, Context.MODE_PRIVATE).edit().putString(STD_ID, stdId).apply()
        }

        fun getStdId(context: Context) : String? {
            return context.getSharedPreferences(STD_ID, Context.MODE_PRIVATE).getString(STD_ID, null)
        }

        fun setClassSchedule(context: Context, classSchedule: ClassScheduleModel) {
            context
                .getSharedPreferences(CLASS_SCHEDULE, Context.MODE_PRIVATE)
                .edit()
                .putString(CLASS_SCHEDULE, Gson().toJson(classSchedule))
                .apply()
        }

        fun getClassSchedule(context: Context) : ClassScheduleModel? {
            val sharedPreferences = context.getSharedPreferences(CLASS_SCHEDULE, Context.MODE_PRIVATE)
            val data = sharedPreferences.getString(CLASS_SCHEDULE, null) ?: return null
            return Gson().fromJson(data, ClassScheduleModel::class.java)
        }

        fun clearAll(context: Context) {
            context.getSharedPreferences(STD_ID, Context.MODE_PRIVATE).edit().clear().apply()
            context.getSharedPreferences(CLASS_SCHEDULE, Context.MODE_PRIVATE).edit().clear().apply()
        }
    }
}