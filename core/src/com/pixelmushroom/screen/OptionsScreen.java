package com.pixelmushroom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.pixelmushroom.MyGame;

public class OptionsScreen implements Screen {

	private MyGame parent;
	private Stage stage;
	
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicEnableLabel;
	private Label soundEnableLabel;
	
	public OptionsScreen() {
		
	}
	
	public OptionsScreen(MyGame game) {
		this.parent = game;
		this.stage = new Stage(new ScreenViewport());
	}
	
	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.internal("skin/vis/uiskin.json"));
		
		final Slider volumeMusicSlider = new Slider(0f,1f,0.1f,false,skin);
		volumeMusicSlider.setValue(parent.getOptions().getMusicVolume());
		volumeMusicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				parent.getOptions().setMusicVolume(volumeMusicSlider.getValue());
				return false;
			}
		});
		
		final Slider volumeSoundSlider = new Slider(0f,1f,0.1f,false,skin);
		volumeSoundSlider.setValue(parent.getOptions().getSoundVolume());
		volumeSoundSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				parent.getOptions().setSoundVolume(volumeSoundSlider.getValue());
				return false;
			}
		});
		
		final CheckBox musicCheckbox = new CheckBox(null,skin);
		musicCheckbox.setChecked(parent.getOptions().isMusicEnabled());
		musicCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = musicCheckbox.isChecked();
				parent.getOptions().setMusicEnabled(enabled);
				return false;
			}
		});
		
		final CheckBox soundCheckbox = new CheckBox(null,skin);
		soundCheckbox.setChecked(parent.getOptions().isSoundEnabled());
		soundCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = soundCheckbox.isChecked();
				parent.getOptions().setSoundEnabled(enabled);
				return false;
			}
		});
		
		final TextButton back = new TextButton("Back",skin);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MyGame.MENU);
			}
		});
		
		titleLabel = new Label("Options", skin);
		volumeMusicLabel = new Label("Music Volume", skin);
		volumeSoundLabel = new Label("Sound Volume", skin);
		musicEnableLabel = new Label("Music", skin);
		soundEnableLabel = new Label("Sound", skin);
		
		table.add(titleLabel).colspan(2).center();
		table.row();
		table.add(volumeMusicLabel).right();
		table.add(volumeMusicSlider).left();
		table.row();
		table.add(musicEnableLabel).right();
		table.add(musicCheckbox).left();
		table.row();
		table.add(volumeSoundLabel).right();
		table.add(volumeSoundSlider).left();
		table.row();
		table.add(soundEnableLabel).right();
		table.add(soundCheckbox).left();
		table.row();
		table.add(back).colspan(2).center();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
