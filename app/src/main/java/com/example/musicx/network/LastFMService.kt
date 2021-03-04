

package com.example.musicx.network

import com.example.musicx.network.model.LastFmAlbum
import com.example.musicx.network.model.LastFmArtist
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface LastFMService {
    companion object {
        private const val API_KEY = "ad20959cf6b54cfc03a84e16d86ea9fc"
        const val BASE_QUERY_PARAMETERS = "?format=json&autocorrect=1&api_key=$API_KEY"
    }

    @GET("$BASE_QUERY_PARAMETERS&method=artist.getinfo")
    suspend fun artistInfo(
        @Query("artist") artistName: String,
        @Query("lang") language: String?,
        @Header("Cache-Control") cacheControl: String?
    ): LastFmArtist

    @GET("$BASE_QUERY_PARAMETERS&method=album.getinfo")
    suspend fun albumInfo(
        @Query("artist") artistName: String,
        @Query("album") albumName: String
    ): LastFmAlbum
}