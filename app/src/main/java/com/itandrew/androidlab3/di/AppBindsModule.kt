package com.itandrew.androidlab3.di

import com.itandrew.androidlab3.data.BulbRepository
import com.itandrew.androidlab3.data.BulbRepositoryImpl
import com.itandrew.androidlab3.domain.GetBrightnessLevelsUseCase
import com.itandrew.androidlab3.domain.GetBrightnessLevelsUseCaseImpl
import com.itandrew.androidlab3.domain.GetColorsUseCase
import com.itandrew.androidlab3.domain.GetColorsUseCaseImpl
import com.itandrew.androidlab3.domain.GetCurrentBrightnessUseCase
import com.itandrew.androidlab3.domain.GetCurrentBrightnessUseCaseImpl
import com.itandrew.androidlab3.domain.GetCurrentColorUseCaseImpl
import com.itandrew.androidlab3.domain.GetLightStateUseCase
import com.itandrew.androidlab3.domain.GetLightStateUseCaseImpl
import com.itandrew.androidlab3.domain.SetBrightnessUseCase
import com.itandrew.androidlab3.domain.SetBrightnessUseCaseImpl
import com.itandrew.androidlab3.domain.SetColorUseCase
import com.itandrew.androidlab3.domain.SetColorUseCaseImpl
import com.itandrew.androidlab3.domain.TurnLightOffUseCase
import com.itandrew.androidlab3.domain.TurnLightOffUseCaseImpl
import com.itandrew.androidlab3.domain.TurnLightOnUseCase
import com.itandrew.androidlab3.domain.TurnLightOnUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Binds
    fun bindBulbRepository(repository: BulbRepositoryImpl): BulbRepository

    @Binds
    fun bindGetBrightnessLevelsUseCase(useCase: GetBrightnessLevelsUseCaseImpl) : GetBrightnessLevelsUseCase

    @Binds
    fun bindGetColorsUseCase(useCase: GetColorsUseCaseImpl) : GetColorsUseCase

    @Binds
    fun bindGetCurrentBrightnessUseCase(useCaseImpl: GetCurrentBrightnessUseCaseImpl) : GetCurrentBrightnessUseCase

    @Binds
    fun bindGetLightStateUseCase(useCase: GetLightStateUseCaseImpl) : GetLightStateUseCase

    @Binds
    fun bindSetBrightnessUseCase(useCase: SetBrightnessUseCaseImpl) : SetBrightnessUseCase

    @Binds
    fun bindSetColorUseCase(useCase: SetColorUseCaseImpl) : SetColorUseCase

    @Binds
    fun bindGetCurrentColorUseCase(useCase: GetCurrentColorUseCaseImpl): GetColorsUseCase

    @Binds
    fun bindTurnLightOffUseCase(useCaseImpl: TurnLightOffUseCaseImpl) : TurnLightOffUseCase

    @Binds
    fun bindTurnLightOnUseCase(useCaseImpl: TurnLightOnUseCaseImpl) : TurnLightOnUseCase
}