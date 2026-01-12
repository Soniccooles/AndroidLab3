package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface TurnLightOffUseCase {
    suspend operator fun invoke(): Result<Boolean?>
}

class TurnLightOffUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : TurnLightOffUseCase {
    override suspend fun invoke(): Result<Boolean?> {
        return repository.turnLightOff()
    }
}