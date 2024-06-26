package github.ntn.blueprint.di

import android.app.Application
import androidx.room.Room
import github.ntn.blueprint.data.local.ArticleDao
import github.ntn.blueprint.data.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: ArticleDatabase.Callback): ArticleDatabase {
        return Room.databaseBuilder(application, ArticleDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: ArticleDatabase): ArticleDao {
        return db.getArticleDao()
    }
}