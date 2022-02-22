package com.ordinaryhuman.selfmadearoma.di

import android.content.Context
import com.ordinaryhuman.selfmadearoma.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.getInstance(context)
    //Room.databaseBuilder(context, AppDatabase::class.java, "recipe_entity").build()

    @Singleton
    @Provides
    fun provideMyAromaDao(db: AppDatabase) = db.myAromaDao()

    @Singleton
    @Provides
    fun provideMyAromaOilInfoDao(db: AppDatabase) = db.myAromaOilInfoDao()

    @Singleton
    @Provides
    fun provideAromaOilDao(db: AppDatabase) = db.aromaOilDao()
}