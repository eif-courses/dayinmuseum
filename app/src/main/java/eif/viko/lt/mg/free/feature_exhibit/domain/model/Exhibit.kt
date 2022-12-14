package eif.viko.lt.mg.free.feature_exhibit.domain.model

import android.provider.MediaStore
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exhibit(
    val title: String,
    val content: String,
    val color: Int,
    val imageURL: String,
    val audioURL: String,
    @PrimaryKey val id: Int? = null
){
    companion object {
        val exhibitColors = listOf(Color.Yellow, Color.Green, Color.Magenta, Color.Cyan, Color.Red)
    }
}

class InvalidExhibitException(message: String): Exception(message)
