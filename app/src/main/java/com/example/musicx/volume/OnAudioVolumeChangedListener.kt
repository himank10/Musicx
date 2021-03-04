
package com.example.musicx.volume

interface OnAudioVolumeChangedListener {
    fun onAudioVolumeChanged(currentVolume: Int, maxVolume: Int)
}