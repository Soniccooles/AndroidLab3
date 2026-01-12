package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface SetBrightnessUseCase {
    suspend operator fun invoke(brightnessLevel: Int): Result<Boolean?>
}

class SetBrightnessUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : SetBrightnessUseCase {
    override suspend fun invoke(brightnessLevel: Int): Result<Boolean?> {
        return repository.setBrightness(brightnessLevel)
    }
}