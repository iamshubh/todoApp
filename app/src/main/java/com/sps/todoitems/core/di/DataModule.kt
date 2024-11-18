package com.sps.todoitems.core.di

import android.content.Context
import androidx.room.Room
import com.sps.todoitems.data.TodoRepositoryImpl
import com.sps.todoitems.data.db.TodoDatabase
import com.sps.todoitems.data.db.TodoEntityDao
import com.sps.todoitems.domain.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        TodoDatabase.databaseName,
    ).build()

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao())
    }

    @Provides
    fun provideUserDao(database: TodoDatabase) = database.todoDao()
}
