package com.msarangal.runningtracker.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.msarangal.runningtracker.db.RunDao
import com.msarangal.runningtracker.db.RunningDatabase
import com.msarangal.runningtracker.network.FileApi
import com.msarangal.runningtracker.other.Constants.RUNNING_DATABASE_NAME
import com.msarangal.runningtracker.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * [SingletonComponent] was previously called as ApplicationComponent
 *
 * In the old Dagger, we needed to create these components by ourselves, which was a complicated process.
 * In Hilt, they are created for us.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        context = app, klass = RunningDatabase::class.java, name = RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesRunningDao(db: RunningDatabase): RunDao {
        return db.getRunDao()
    }

    @Singleton
    @Provides
    fun providesMainRepository(runDao: RunDao): MainRepository = MainRepository(runDao = runDao)

    @Singleton
    @Provides
    fun providesRetrofit(): FileApi = Retrofit.Builder()
        .baseUrl("https://pl-coding.com")
        .build()
        .create(FileApi::class.java)

    @Singleton
    @Provides
    fun providesWorkManager(
        @ApplicationContext app: Context
    ) = WorkManager.getInstance(app)
}