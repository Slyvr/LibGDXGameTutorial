package com.pixelmushroom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pixelmushroom.MyGame;
import com.pixelmushroom.model.actor.LoadingBarPart;

public class LoadingScreen implements Screen {

	private MyGame parent;
	private Stage stage;
	
	private Image titleImage;
	private Image imgtitleLogo;
	
	private Table table;
	private Table loadingTable;
	
	private TextureAtlas atlas;
	private AtlasRegion title;
	private AtlasRegion titleLogo;
	private AtlasRegion dash;
	private AtlasRegion background;
	private Animation flameAnimation;
	
	private SpriteBatch batch;
	
	private int currentLoadingStage=0;
	private float countDown=1f;
	
	public final int IMAGE = 0;
	public final int FONT = 1;
	public final int PARTY = 2;
	public final int SOUND = 3;
	public final int MUSIC = 4;
	
	private float stateTime=0f;
	
	public LoadingScreen() {
		
	}
	
	public LoadingScreen(MyGame game) {
		this.parent = game;
		this.stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		
		loadAssets();
	}
	
	@Override
	public void show() {
		titleImage = new Image(title);
		imgtitleLogo = new Image(titleLogo);
		
		table = new Table();
		table.setFillParent(true);
		table.setDebug(false);
		table.setBackground(new TiledDrawable(background));
		
		loadingTable = new Table();
		for(int i=0; i<10; i++) {
			loadingTable.add(new LoadingBarPart(dash, flameAnimation));
		}
		
		table.add(imgtitleLogo).align(Align.center).pad(10,0,0,0).colspan(10);
		table.row();
		table.add(titleImage).align(Align.center).pad(10,0,0,0).colspan(10);
		table.row();
		table.add(loadingTable).width(400);
		
		stage.addActor(table);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (parent.manager.manager.update()){
			currentLoadingStage += 1;
			
			if (currentLoadingStage <= 5) {
				loadingTable.getCells().get((currentLoadingStage-1)*2).getActor().setVisible(true);
				loadingTable.getCells().get((currentLoadingStage-1)*2+1).getActor().setVisible(true);
			}
			
			switch(currentLoadingStage) {
			case FONT:
				System.out.println("Loading Fonts...");
				parent.manager.queueAddFonts();
				break;
			case PARTY:
				System.out.println("Loading Particle Effects...");
				parent.manager.queueAddParticleEffects();
				break;
			case SOUND:
				System.out.println("Loading Sounds...");
				parent.manager.queueAddSounds();
				break;
			case MUSIC:
				System.out.println("Loading Music...");
				parent.manager.queueAddMusic();
				break;
			case 5:
				System.out.println("Finished");
				break;
			}
			
			if (currentLoadingStage > 5) {
				countDown -= delta;
				currentLoadingStage = 5;
				if (countDown < 0) {
					parent.changeScreen(MyGame.MENU);
				}
			}
		}
		
		stage.act();
		stage.draw();
		
	}
	
	private void loadAssets() {
		parent.manager.queueAddLoadingImages();
		parent.manager.manager.finishLoading();
		
		atlas = parent.manager.manager.get("images/loading_textures.atlas");
		titleLogo = atlas.findRegion("pixelmushroom");
		title = atlas.findRegion("pixelmushroom_text");
		dash = atlas.findRegion("loading");
		background = atlas.findRegion("loading2");
		flameAnimation = new Animation(0.07f,atlas.findRegions("flames"), PlayMode.LOOP);
		
		parent.manager.queueAddImages();
		System.out.println("Loading images...");
	}

	private void drawLoadingBar(int stage, TextureRegion currentFrame) {
		for(int i=0; i<stage; i++) {
			batch.draw(currentFrame, 50+(i*50),150,50,50);
			batch.draw(dash, 35+(i*50),140,80,80);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
