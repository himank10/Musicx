package code.example.musicx.interfaces

import code.example.musicx.model.Album
import code.example.musicx.model.Artist
import code.example.musicx.model.Genre


interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}