package me.arthurc.ntub_class.network

import me.arthurc.ntub_class.model.ErrorModel
import retrofit2.Response
import java.io.IOException


class ErrorHandler {
    companion object {
        fun parse(response: Response<*>): ErrorModel {
            val converter = ClassScheduleClient
                .retrofit
                .responseBodyConverter<ErrorModel>(ErrorModel::class.java, arrayOfNulls<Annotation>(0))

            return try {
                converter.convert(response.errorBody())
            } catch (e: IOException) {
                ErrorModel()
            }
        }
    }
}