package com.pixelmushroom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.VisUI;
import com.pixelmushroom.loader.B2dAssetManager;
import com.pixelmushroom.screen.EndScreen;
import com.pixelmushroom.screen.LoadingScreen;
import com.pixelmushroom.screen.MainScreen;
import com.pixelmushroom.screen.MenuScreen;
import com.pixelmushroom.screen.OptionsScreen;

/*
 * https://www.gamedevelopment.blog/full-libgdx-game-tutorial-infinite-level-generation-with-simplex-noise/
 * https://github.com/czyzby/gdx-skins/tree/master
 * https://github.com/kotcrab/vis-ui
 */
public class MyGame extends Game {
	
	private AppOptions options;
	
	private LoadingScreen loadingScreen;
	private OptionsScreen optionsScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	
	public B2dAssetManager manager = new B2dAssetManager();
	 
	public final static int MENU = 0;
	public final static int OPTIONS = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	
	private Music music;
	
	@Override
	public void create () {
		VisUI.load();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		options = new AppOptions();
		
		manager.queueAddMusic();
		manager.manager.finishLoading();
		music = manager.manager.get("music/music.mp3",Music.class);
		music.play();
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public void changeScreen(int screen) {
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case OPTIONS:
				if(optionsScreen == null) optionsScreen = new OptionsScreen(this);
				this.setScreen(optionsScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}
	
	public AppOptions getOptions() {
		return options;
	}
	
	@Override
	public void dispose() {
		music.dispose();
		manager.manager.dispose();
	}
}
