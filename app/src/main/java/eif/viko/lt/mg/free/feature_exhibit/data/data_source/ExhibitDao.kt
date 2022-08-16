package eif.viko.lt.mg.free.feature_exhibit.data.data_source

import androidx.room.*
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import kotlinx.coroutines.flow.Flow
@Dao
interface ExhibitDao {

    @Query("SELECT * FROM exhibit")
    fun getExhibits(): Flow<List<Exhibit>>

    @Query("SELECT * FROM exhibit WHERE id = :id")
    suspend fun getExhibitById(id: Int): Exhibit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExhibit(exhibit: Exhibit)

    @Delete
    suspend fun deleteExhibit(exhibit: Exhibit)

}