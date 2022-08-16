package eif.viko.lt.mg.free.feature_exhibit.domain.use_case

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.repository.ExhibitRepository

class DeleteExhibit(
    private val repository: ExhibitRepository
) {
    suspend operator fun invoke(exhibit: Exhibit){
        repository.deleteExhibit(exhibit)
    }
}