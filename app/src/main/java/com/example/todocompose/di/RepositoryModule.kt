package com.example.todocompose.di

import com.example.todocompose.data.repositories.ToDoRepository
import com.example.todocompose.data.repositories.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindToDoRepository(repositoryImpl: ToDoRepositoryImpl): ToDoRepository
}