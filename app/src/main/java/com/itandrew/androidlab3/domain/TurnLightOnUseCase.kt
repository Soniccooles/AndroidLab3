package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface TurnLightOnUseCase {
    suspend operator fun invoke(): Result<Boolean?>
}

class TurnLightOnUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : TurnLightOnUseCase {
    override suspend fun invoke(): Result<Boolean?> {
        return repository.turnLightOn()
    }
}