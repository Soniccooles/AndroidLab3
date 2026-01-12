package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import com.itandrew.androidlab3.data.model.ColorInfo
import javax.inject.Inject

interface GetCurrentColorUseCase {
    suspend operator fun invoke(): Result<ColorInfo?>
}

class GetCurrentColorUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : GetCurrentColorUseCase {
    override suspend fun invoke(): Result<ColorInfo?> {
        return repository.getCurrentColor()
    }
}