package com.itandrew.androidlab3.data

import android.hardware.lights.LightState
import androidx.lifecycle.LiveData
import com.itandrew.androidlab3.data.model.ColorInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface BulbApiService {

    @GET("state/")
    suspend fun getLightState(): Response<Boolean>

    @POST("state/on")
    suspend fun turnLightOn(): Response<Boolean>

    @POST("state/off")
    suspend fun turnLightOff(): Response<Boolean>

    @GET("color/")
    suspend fun getColors(): Response<List<ColorInfo>>

    @POST("color/")
    suspend fun setColor(): Response<Boolean>

    @GET("color/current")
    suspend fun getCurrentColor(): Response<ColorInfo>

    @GET("brightness/")
    suspend fun getBrignessLevels(): Response<List<Int>>

    @POST("brightness/")
    suspend fun setBrightness(): Response<Boolean>

    @GET("brightness/current")
    suspend fun getCurrentBrightness(): Response<Int>



}