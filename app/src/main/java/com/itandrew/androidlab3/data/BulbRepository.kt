package com.itandrew.androidlab3.data

import retrofit2.HttpException
import com.itandrew.androidlab3.data.model.ColorInfo
import com.itandrew.androidlab3.data.model.BrightnessLevel
import retrofit2.Response
import retrofit2.http.POST
import javax.inject.Inject


interface BulbRepository {

    suspend  fun getLightState(): Result<Boolean?>

    suspend fun  getColors(): Result<List<ColorInfo>?>

    suspend fun  getCurrentColor(): Result<ColorInfo?>

    suspend fun  getBrightnessLevels(): Result<BrightnessLevel?>

    suspend fun  getCurrentBrightness(): Result<Int?>

    suspend fun  turnLightOn(): Result<Boolean?>

    suspend fun turnLightOff(): Result<Boolean?>

    suspend fun setColor(color: String): Result<Boolean?>

    suspend fun setBrightness(brightness: Int): Result<Boolean?>

}

class BulbRepositoryImpl @Inject constructor(
    private val service: BulbApiService
): BulbRepository {
    override suspend fun getLightState(): Result<Boolean?> {
        return makeApiCall { service.getLightState() }
    }

    override suspend fun getColors(): Result<List<ColorInfo>?> {
        return makeApiCall { service.getColors() }
    }

    override suspend fun getCurrentColor(): Result<ColorInfo?> {
        return makeApiCall { service.getCurrentColor() }
    }

    override suspend fun getBrightnessLevels(): Result<BrightnessLevel?> {
        return makeApiCall { service.getBrignessLevels() }
    }

    override suspend fun getCurrentBrightness(): Result<Int?> {
        return makeApiCall { service.getCurrentBrightness() }
    }

    override suspend fun turnLightOn(): Result<Boolean?> {
        return makeApiCall { service.turnLightOn() }
    }

    override suspend fun turnLightOff(): Result<Boolean?> {
        return makeApiCall { service.turnLightOff() }
    }

    override suspend fun setColor(color: String): Result<Boolean?> {
        return makeApiCall { service.setColor(color) }
    }

    override suspend fun setBrightness(brightness: Int): Result<Boolean?> {
        return makeApiCall { service.setBrightness(brightness) }
    }

    private suspend fun <T> makeApiCall(callApi: suspend() -> Response<T>) : Result<T?> {
        kotlin.runCatching {
            callApi()
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }
}