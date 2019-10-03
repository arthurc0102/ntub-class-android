package me.arthurc.ntub_class.network

import me.arthurc.ntub_class.model.ClassScheduleModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ClassScheduleClient {
    companion object {
        private const val BASE_URL = "https://ntub-class.herokuapp.com"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: Service by lazy {
            retrofit.create(Service::class.java)
        }

        interface Service {
            @GET("/personal/{std_id}")
            fun getClassSchedule(@Path("std_id") stdId: String): Call<ClassScheduleModel>
        }
    }
}