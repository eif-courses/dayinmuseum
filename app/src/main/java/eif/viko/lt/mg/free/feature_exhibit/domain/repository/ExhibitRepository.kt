package eif.viko.lt.mg.free.feature_exhibit.domain.repository

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import kotlinx.coroutines.flow.Flow

interface ExhibitRepository {

    fun getExhibits():Flow<List<Exhibit>>

    suspend fun getExhibit(id: Int):Exhibit?

    suspend fun insertExhibit(exhibit: Exhibit)

    suspend fun deleteExhibit(exhibit: Exhibit)
}