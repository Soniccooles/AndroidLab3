package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface GetLightStateUseCase {
    suspend operator fun invoke(): Result<Boolean?>
}

class GetLightStateUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : GetLightStateUseCase {
    override suspend operator fun invoke(): Result<Boolean?> {
        return repository.getLightState()
    }
}