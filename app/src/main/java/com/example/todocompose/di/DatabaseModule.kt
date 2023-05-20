package com.example.todocompose.di

import android.content.Context
import androidx.room.Room
import com.example.todocompose.data.database.ToDoDao
import com.example.todocompose.data.database.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideToDoDatabase(
        @ApplicationContext context: Context
    ):ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            ToDoDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideToDoDao(database: ToDoDatabase): ToDoDao {
        return database.toDoDao()
    }
}