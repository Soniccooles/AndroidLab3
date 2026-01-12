package com.itandrew.androidlab3.data

import com.itandrew.androidlab3.data.model.BrightnessLevel
import com.itandrew.androidlab3.data.model.ColorInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    suspend fun setColor(@Query("color") color: String): Response<Boolean>

    @GET("color/current")
    suspend fun getCurrentColor(): Response<ColorInfo>

    @GET("brightness/")
    suspend fun getBrignessLevels(): Response<BrightnessLevel>

    @POST("brightness/")
    suspend fun setBrightness(@Query("level") brightnessLevel: Int): Response<Boolean>

    @GET("brightness/current")
    suspend fun getCurrentBrightness(): Response<Int>
}