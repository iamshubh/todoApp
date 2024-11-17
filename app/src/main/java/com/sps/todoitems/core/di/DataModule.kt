package com.sps.todoitems.core.di

import android.content.Context
import androidx.room.Room
import com.sps.todoitems.data.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        TodoDatabase.databaseName,
    ).build()

    @Provides
    fun provideUserDao(database: TodoDatabase) = database.todoDao()
}
