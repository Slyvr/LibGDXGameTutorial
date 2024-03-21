package com.pixelmushroom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.pixelmushroom.MyGame;

public class MenuScreen implements Screen{

	private MyGame parent;
	private Stage stage;
	
	private TextureAtlas atlas;
	private AtlasRegion background;
	
	private Skin skin;
	
	public MenuScreen() {
		
	}
	
	public MenuScreen(MyGame game) {
		this.parent = game;
		this.stage = new Stage(new ScreenViewport());
		
		loadAssets();
	}
	
	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		table.setBackground(new TiledDrawable(background));
		//table.setDebug(true);
		stage.addActor(table);
		
		//Skin skin = new Skin(Gdx.files.internal("skin/vis/uiskin.json"));
		
		TextButton newGame = new TextButton("New Game",skin);
		TextButton options = new TextButton("Options", skin);
		TextButton quit = new TextButton("Quit",skin);
		
		table.add(newGame).fillX().uniformX();
		table.row().pad(10,0,10,0);
		table.add(options).fillX().uniformX();
		table.row();
		table.add(quit).fillX().uniformX();
		
		quit.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MyGame.APPLICATION);
			}
		});
		
		options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MyGame.OPTIONS);
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}
	
	private void loadAssets() {
		parent.manager.queueAddLoadingImages();
		parent.manager.manager.finishLoading();
		atlas = parent.manager.manager.get("images/loading_textures.atlas");
		background = atlas.findRegion("loading2");
		
		parent.manager.queueAddSkin();
		parent.manager.manager.finishLoading();
		skin = parent.manager.manager.get("skin/vis/uiskin.json");
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
