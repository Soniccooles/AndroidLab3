package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import com.itandrew.androidlab3.data.model.BrightnessLevel
import javax.inject.Inject

interface GetBrightnessLevelsUseCase {
    suspend operator fun invoke(): Result<BrightnessLevel?>
}

class GetBrightnessLevelsUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : GetBrightnessLevelsUseCase {
    override suspend fun invoke(): Result<BrightnessLevel?> {
        return repository.getBrightnessLevels()
    }
}