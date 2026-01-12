package com.itandrew.androidlab3.di

import com.itandrew.androidlab3.di.viewModel.ViewModelModule
import com.itandrew.androidlab3.presenter.MainFragment
import dagger.Component
import dagger.Module

@Component( modules = [AppModule::class] )
interface AppComponent {
    abstract fun inject(fragment: MainFragment)
}

@Module(
    includes = [
        NetworkModule::class,
        AppBindsModule::class,
        ViewModelModule::class
    ]
)
class AppModule