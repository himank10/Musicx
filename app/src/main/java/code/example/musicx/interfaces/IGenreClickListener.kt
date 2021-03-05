package code.example.musicx.interfaces

import android.view.View
import code.example.musicx.model.Genre


interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}