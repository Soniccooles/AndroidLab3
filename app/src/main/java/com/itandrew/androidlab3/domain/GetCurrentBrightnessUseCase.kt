package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import javax.inject.Inject

interface GetCurrentBrightnessUseCase {
    suspend operator fun invoke(): Result<Int?>
}

class GetCurrentBrightnessUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : GetCurrentBrightnessUseCase {
    override suspend fun invoke(): Result<Int?> {
        return repository.getCurrentBrightness()
    }
}