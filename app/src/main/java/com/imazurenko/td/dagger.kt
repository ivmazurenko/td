package com.imazurenko.td

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(noteFragment: NoteFragment)
    fun inject(noteListFragment: NoteListFragment)
    fun inject(mainActivity: MainActivity)
}

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    private val appDatabase: AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }
}

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteListFragmentViewModel::class)
    internal abstract fun noteListFragmentViewModel(viewModel: NoteListFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteFragmentViewModel::class)
    internal abstract fun noteFragmentViewModel(viewModel: NoteFragmentViewModel): ViewModel
}



