package eif.viko.lt.mg.free.feature_exhibit.domain.use_case

import eif.viko.lt.mg.free.feature_exhibit.domain.model.Exhibit
import eif.viko.lt.mg.free.feature_exhibit.domain.model.InvalidExhibitException
import eif.viko.lt.mg.free.feature_exhibit.domain.repository.ExhibitRepository
import kotlin.system.exitProcess

class AddExhibit(
    private val repository: ExhibitRepository
) {
    @Throws(InvalidExhibitException::class)
    suspend operator fun invoke(exhibit: Exhibit){
        if(exhibit.title.isBlank()){
            throw InvalidExhibitException("The title of the exhibit can't be empty.")
        }
        repository.insertExhibit(exhibit)
    }
}