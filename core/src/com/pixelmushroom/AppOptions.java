package com.pixelmushroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppOptions {

	private static final String OPT_MUSIC_VOLUME = "volume";
	private static final String OPT_MUSIC_ENABLED = "music.enabled";
	private static final String OPT_SOUND_ENABLED = "sound.enabled";
	private static final String OPT_SOUND_VOL = "sound";
	private static final String OPT_NAME = "pixelmush";
	
	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(OPT_NAME);
	}
	
	public boolean isSoundEnabled() {
		return getPrefs().getBoolean(OPT_SOUND_ENABLED,true);
	}
	
	public void setSoundEnabled(boolean enable) {
		getPrefs().putBoolean(OPT_SOUND_ENABLED, enable);
		getPrefs().flush();
	}
	
	public float getSoundVolume() {
		return getPrefs().getFloat(OPT_SOUND_VOL, 0.5f);
	}
	
	public void setSoundVolume(float volume) {
		getPrefs().putFloat(OPT_SOUND_VOL, volume);
		getPrefs().flush();
	}
	
	public boolean isMusicEnabled() {
		return getPrefs().getBoolean(OPT_MUSIC_ENABLED,true);
	}
	
	public void setMusicEnabled(boolean enable) {
		getPrefs().putBoolean(OPT_MUSIC_ENABLED, enable);
		getPrefs().flush();
	}
	
	public float getMusicVolume() {
		return getPrefs().getFloat(OPT_MUSIC_VOLUME,0.5f);
	}
	
	public void setMusicVolume(float volume) {
		getPrefs().putFloat(OPT_MUSIC_VOLUME, volume);
		getPrefs().flush(); //write data to disk/save-it
	}
}
