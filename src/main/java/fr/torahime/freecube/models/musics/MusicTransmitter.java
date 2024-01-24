package fr.torahime.freecube.models.musics;

import org.bukkit.Location;

public class MusicTransmitter {

    private Music music;
    private Location location;
    private float volume;
    private float pitch;

    public MusicTransmitter(Music music, Location location, float volume, float pitch) {
        this.music = music;
        this.location = location;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }

    public Location getLocation() {
        return location;
    }

    public void setVolume(float volume) {
        if(volume < 1 || volume > 5){
            return;
        }
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }


}
