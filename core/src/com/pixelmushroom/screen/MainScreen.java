package com.pixelmushroom.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.pixelmushroom.B2dContactListener;
import com.pixelmushroom.BodyFactory;
import com.pixelmushroom.MyGame;
import com.pixelmushroom.component.B2dBodyComponent;
import com.pixelmushroom.component.CollisionComponent;
import com.pixelmushroom.component.PlayerComponent;
import com.pixelmushroom.component.StateComponent;
import com.pixelmushroom.component.TextureComponent;
import com.pixelmushroom.component.TransformComponent;
import com.pixelmushroom.component.TypeComponent;
import com.pixelmushroom.controller.KeyboardController;
import com.pixelmushroom.model.B2dModel;
import com.pixelmushroom.system.*;

public class MainScreen implements Screen{

	private MyGame parent;
	private KeyboardController controller;
	private World world;
	private BodyFactory bodyFactory;
	
	private TextureAtlas atlas;
	private Sound ping;
	private Sound boing;
	
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private PooledEngine engine;
	
	public MainScreen() {
		
	}
	
	public MainScreen(MyGame game) {
		this.parent = game;
		controller = new KeyboardController();
		world = new World(new Vector2(0,-10f),true);
		world.setContactListener(new B2dContactListener());
		bodyFactory = BodyFactory.getInstance(world);
		
		parent.manager.queueAddSounds();
		parent.manager.manager.finishLoading();
		atlas = parent.manager.manager.get("images/game_textures.atlas", TextureAtlas.class);
		ping = parent.manager.manager.get("sounds/ping.wav",Sound.class);
		boing = parent.manager.manager.get("sounds/boing.wav",Sound.class);
		
		batch = new SpriteBatch();
		RenderingSystem renderingSystem = new RenderingSystem(batch);
		cam = renderingSystem.getCamera();
		batch.setProjectionMatrix(cam.combined);
		
		engine = new PooledEngine();
		
		engine.addSystem(new AnimationSystem());
		engine.addSystem(renderingSystem);
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller));
		
		createPlayer();
		createPlatform(2,2);
		createPlatform(2,7);
		createPlatform(7,2);
		createPlatform(7,7);
		
		createFloor();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		engine.update(delta);
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
	
	private void createPlayer() {
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		PlayerComponent player = engine.createComponent(PlayerComponent.class);
		CollisionComponent collision = engine.createComponent(CollisionComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		StateComponent state = engine.createComponent(StateComponent.class);
		
		b2dBody.body = bodyFactory.makeCirclePolyBody(10,10,1, BodyFactory.STONE,BodyType.DynamicBody, true);
		position.position.set(10,10,0);
		texture.region=atlas.findRegion("player");
		type.type = TypeComponent.PLAYER;
		state.set(StateComponent.STATE_NORMAL);
		b2dBody.body.setUserData(entity);
		
		entity.add(b2dBody);
		entity.add(position);
		entity.add(texture);
		entity.add(player);
		entity.add(collision);
		entity.add(type);
		entity.add(state);
		
		engine.addEntity(entity);
	}
	
	private void createPlatform(float x, float y){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 3, 0.2f, BodyFactory.STONE, BodyType.StaticBody, true);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = atlas.findRegion("player");
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.SCENERY;
		b2dbody.body.setUserData(entity);
		
		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		
		engine.addEntity(entity);
		
	}

	private void createFloor(){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyType.StaticBody, true);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = atlas.findRegion("player");
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.SCENERY;
		
		b2dbody.body.setUserData(entity);

		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		
		engine.addEntity(entity);
	}

}
