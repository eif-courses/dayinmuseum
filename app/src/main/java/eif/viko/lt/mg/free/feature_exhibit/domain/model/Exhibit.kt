package eif.viko.lt.mg.free.feature_exhibit.domain.model

import android.provider.MediaStore
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exhibit(
    val title: String,
    val description: String,
    val imageURL: String,
    val audioURL: String,
    @PrimaryKey val id: Int? = null
)

class InvalidExhibitException(message: String): Exception(message)
