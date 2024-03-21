package com.pixelmushroom.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.pixelmushroom.B2dContactListener;
import com.pixelmushroom.BodyFactory;
import com.pixelmushroom.controller.KeyboardController;
import com.pixelmushroom.loader.B2dAssetManager;

public class B2dModel {

	public World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;
	private B2dAssetManager manager;
	private Body bodyD;
	private Body bodyS;
	private Body bodyK;
	public Body player;
	private Body water;
	public boolean isSwimming=false;
	private KeyboardController controller;
	
	private Sound ping;
	private Sound boing;
	
	public static final int BOING_SOUND = 0;
	public static final int PING_SOUND = 1;
	
	public B2dModel(KeyboardController controller, OrthographicCamera cam, B2dAssetManager manager) {
		this.controller=controller;
		this.cam = cam;
		this.manager=manager;
		world = new World(new Vector2(0,-10f),true);
		world.setContactListener(new B2dContactListener(this));
		
		manager.queueAddSounds();
		manager.manager.finishLoading();
		
		ping = manager.manager.get("sounds/ping.wav", Sound.class);
		boing = manager.manager.get("sounds/boing.wav", Sound.class);
		
		createFloor();
		//createObject();
		//createMovingObject();
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER);
		
		water = bodyFactory.makeBoxPolyBody(1, -8,40,10, BodyFactory.RUBBER,BodyType.StaticBody,false);
		water.setUserData("IAMTHESEA");
		
		bodyFactory.makeAllFixturesSensors(water);
		
		/*
		bodyFactory.makeCirclePolyBody(-4, 1, 2, BodyFactory.STONE);
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER);
		bodyFactory.makeCirclePolyBody(4, 1, 2, BodyFactory.STEEL);
		bodyFactory.makeBoxPolyBody(10, -1, 3, 3, BodyFactory.STONE);
		
		Vector2[] vertices = new Vector2[] {
				new Vector2(1,5),
				new Vector2(0,2),
				new Vector2(2.5f,0),
				new Vector2(5,2),
				new Vector2(4,5)
				};
		
		bodyFactory.makePolygonShapeBody(vertices, 1, 3, BodyFactory.STONE);
		
		//https://www.mathopenref.com/coordpolycalc.html
		vertices = new Vector2[] {
				new Vector2(1,-3),
				new Vector2(-1,-3),
				new Vector2(-3,-1),
				new Vector2(-3,1),
				new Vector2(-1,3),
				new Vector2(1,3),
				new Vector2(3,1),
				new Vector2(3,-1)
				};
		
		bodyFactory.makePolygonShapeBody(vertices, 8, 3, BodyFactory.STEEL);
		*/
	}
	
	public void logicStep(float delta) {
		
		if (controller.left) {
			player.applyForceToCenter(-10, 0, true);
		}
		else if (controller.right) {
			player.applyForceToCenter(10, 0, true);
		}
		else if (controller.up) {
			player.applyForceToCenter(0, 10, true);
		}
		else if (controller.down) {
			player.applyForceToCenter(0, -10, true);
		}
		
		if (isSwimming) {
			player.applyForceToCenter(0,60,true);
		}
		
		if (controller.isMouse1Down && pointIntersectsBody(player,controller.mouseLocation)) {
			System.out.println("Player was clicked");
		}
		
		world.step(delta, 3, 3);
	}
	
	public void playSound(int sound) {
		switch(sound) {
		case BOING_SOUND:
			boing.play();
			break;
		case PING_SOUND:
			ping.play();
			break;
		}
	}
	
	/*
	 * Checks if point is in first fixture
	 */
	public boolean pointIntersectsBody(Body body, Vector2 mouseLocation) {
		Vector3 mousePos = new Vector3(mouseLocation,0);//convert to 3d position
		cam.unproject(mousePos);
		if(body.getFixtureList().first().testPoint(mousePos.x,mousePos.y)) {
			return true;
		}
		return false;
	}
	
	private void createObject() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0,0);
		
		bodyD = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		
		bodyD.createFixture(shape, 0.0f);
		
		shape.dispose();
	}
	
	private void createFloor() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0,-10);
		
		bodyS = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50,1);
		
		bodyS.createFixture(shape, 0.0f);
		shape.dispose();
	}
	
	private void createMovingObject() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(0,-12);
		
		bodyK = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		
		bodyK.createFixture(shape, 0.0f);
		
		shape.dispose();
		
		bodyK.setLinearVelocity(0,0.75f);
	}
}
