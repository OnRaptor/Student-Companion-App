package com.example.uksivtcompanion.di;

import android.content.Context
import com.example.uksivtcompanion.data.dao.ScheduleDAO
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ScheduleModule {
    @Provides
    @Singleton
    fun provideScheduleDAO(@ApplicationContext context: Context) : ScheduleDAO =
        ScheduleDAO(Gson(), context)

}
