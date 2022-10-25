package com.example.uksivtcompanion.di

import android.content.Context
import androidx.room.Room
import com.example.uksivtcompanion.data.DiaryDataBase
import com.example.uksivtcompanion.data.dao.DiaryDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): DiaryDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            DiaryDataBase::class.java,
            "diary_date_base"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideDiaryDao(diaryDataBase: DiaryDataBase): DiaryDAO = diaryDataBase.diaryDAO()
}