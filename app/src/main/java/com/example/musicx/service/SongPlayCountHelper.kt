

package com.example.musicx.service

import com.example.musicx.helper.StopWatch
import com.example.musicx.model.Song

class SongPlayCountHelper {

    private val stopWatch = StopWatch()
    var song = Song.emptySong
        private set

    fun shouldBumpPlayCount(): Boolean {
        return song.duration * 0.5 < stopWatch.elapsedTime
    }

    fun notifySongChanged(song: Song) {
        synchronized(this) {
            stopWatch.reset()
            this.song = song
        }
    }

    fun notifyPlayStateChanged(isPlaying: Boolean) {
        synchronized(this) {
            if (isPlaying) {
                stopWatch.start()
            } else {
                stopWatch.pause()
            }
        }
    }

    companion object {
        val TAG: String = SongPlayCountHelper::class.java.simpleName
    }
}