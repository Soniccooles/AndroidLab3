package com.itandrew.androidlab3.domain

import com.itandrew.androidlab3.data.BulbRepository
import com.itandrew.androidlab3.data.model.ColorInfo
import javax.inject.Inject

interface GetColorsUseCase {
    suspend operator fun invoke(): Result<List<ColorInfo>?>
}

class GetColorsUseCaseImpl @Inject constructor(
    private val repository: BulbRepository
) : GetColorsUseCase {
    override suspend fun invoke(): Result<List<ColorInfo>?> {
        return repository.getColors()
    }
}