package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface SetColorUseCase {
    suspend operator fun invoke(color: String): Result<Boolean?>
}

class SetColorUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : SetColorUseCase {
    override suspend fun invoke(color: String): Result<Boolean?> {
        return repository.setColor(color)
    }
}