package com.example.musicx.interfaces

import com.example.musicx.model.Album
import com.example.musicx.model.Artist
import com.example.musicx.model.Genre


interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}