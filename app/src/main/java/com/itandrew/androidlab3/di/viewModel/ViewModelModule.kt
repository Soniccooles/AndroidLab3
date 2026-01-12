package com.itandrew.androidlab3.di.viewModel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import androidx.lifecycle.ViewModelProvider
import com.itandrew.androidlab3.presenter.MainViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract  fun  bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}