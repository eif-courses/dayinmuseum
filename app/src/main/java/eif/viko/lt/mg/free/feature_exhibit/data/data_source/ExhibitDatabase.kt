package eif.viko.lt.mg.free.feature_exhibit.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit

@Database(
    entities = [Exhibit::class],
    version = 1,
    exportSchema = false
)
abstract class ExhibitDatabase: RoomDatabase(){
    abstract val exhibitDao: ExhibitDao

    companion object{
        const val DATABASE_NAME = "exhibits_db"
    }
}