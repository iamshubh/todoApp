package com.sps.todoitems.core.di

import com.sps.todoitems.domain.TodoRepository
import com.sps.todoitems.data.TodoRepositoryImpl
import com.sps.todoitems.data.db.TodoEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

}