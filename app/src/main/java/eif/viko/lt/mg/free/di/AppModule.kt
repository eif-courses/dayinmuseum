package eif.viko.lt.mg.free.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eif.viko.lt.mg.free.feature_exhibit.data.data_source.ExhibitDatabase
import eif.viko.lt.mg.free.feature_exhibit.data.repository.ExhibitRepositoryImpl
import eif.viko.lt.mg.free.feature_exhibit.domain.repository.ExhibitRepository
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.AddExhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.DeleteExhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.ExhibitUseCases
import eif.viko.lt.mg.free.feature_exhibit.domain.use_case.GetExhibits
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExhibitDatabase(app: Application): ExhibitDatabase {
        return Room.databaseBuilder(
            app,
            ExhibitDatabase::class.java,
            ExhibitDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExhibitRepository(db: ExhibitDatabase): ExhibitRepository{
        return ExhibitRepositoryImpl(db.exhibitDao)
    }

    @Provides
    @Singleton
    fun provideExhibitUseCases(repository: ExhibitRepository): ExhibitUseCases{
        return ExhibitUseCases(
            getExhibits = GetExhibits(repository),
            deleteExhibit = DeleteExhibit(repository),
            addExhibit = AddExhibit(repository)
        )
    }

}