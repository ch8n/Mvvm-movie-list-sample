package com.example.ch8n


interface AudioPlayer{

    fun play()
    fun resume()
    fun rewind()
    fun forward()
    fun loop()
    fun loadCover()
    fun progressStatus()
    fun trackDuration()
    fun nextTrack()
    fun previousTrack()

}

interface Notification{

    fun audioTrackName()
    fun audioTrackSingerName()
    fun playSong()
    fun pauseSong()
    fun duration()
    fun loadCover()
    fun nextSong()
    fun previousSong()
    fun showNotification()
    fun hideNotification()
}