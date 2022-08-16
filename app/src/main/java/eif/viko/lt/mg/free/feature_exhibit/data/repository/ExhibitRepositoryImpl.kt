package eif.viko.lt.mg.free.feature_exhibit.data.repository

import eif.viko.lt.mg.free.feature_exhibit.data.data_source.ExhibitDao
import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow

class ExhibitRepositoryImpl(
    private val dao: ExhibitDao
): ExhibitRepository {
    override fun getExhibits(): Flow<List<Exhibit>> {
        return dao.getExhibits()
    }

    override suspend fun getExhibit(id: Int): Exhibit? {
        return dao.getExhibitById(id)
    }

    override suspend fun insertExhibit(exhibit: Exhibit) {
        dao.insertExhibit(exhibit)
    }

    override suspend fun deleteExhibit(exhibit: Exhibit) {
        dao.deleteExhibit(exhibit)
    }
}