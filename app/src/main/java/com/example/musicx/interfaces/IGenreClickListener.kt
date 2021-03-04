package com.example.musicx.interfaces

import android.view.View
import com.example.musicx.model.Genre


interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}