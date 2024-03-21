package com.pixelmushroom;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

	private static BodyFactory thisInstance;
	private World world;
	
	public static final int STEEL=0;
	public static final int WOOD=1;
	public static final int RUBBER=2;
	public static final int STONE=3;
	
	private final float DEGTORAD=0.0174533f;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory getInstance(World world) {
		if (thisInstance == null) {
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}
	
	public void makeAllFixturesSensors(Body body) {
		for(Fixture fix : body.getFixtureList()) {
			fix.setSensor(true);
		}
	}
	
	public Body makeCirclePolyBody(float posX, float posY, float radius, int material) {
		return makeCirclePolyBody(posX,posY,radius,material,BodyType.DynamicBody,false);
	}
	
	public Body makeCirclePolyBody(float posX, float posY, float radius, int material, BodyType bodyType, boolean fixedRotation){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posX;
		boxBodyDef.position.y = posY;
		boxBodyDef.fixedRotation = fixedRotation;
			
		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius /2);
		boxBody.createFixture(makeFixture(material,circleShape));
		circleShape.dispose();
		return boxBody;
	}
	
	public Body makeBoxPolyBody(float posX, float posY, float width, float height, int material) {
		return makeBoxPolyBody(posX,posY,width,height,material,BodyType.DynamicBody,false);
	}
	
	public Body makeBoxPolyBody(float posX, float posY, float width, float height, int material, BodyType bodyType, boolean fixedRotation) {
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posX;
		boxBodyDef.position.y = posY;
		boxBodyDef.fixedRotation = fixedRotation;
		
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2,  height/2);
		boxBody.createFixture(makeFixture(material,poly));
		poly.dispose();
		return boxBody;
	}
	
	public Body makePolygonShapeBody(Vector2[] vertices, float posX, float posY, int material) {
		return makePolygonShapeBody(vertices,posX,posY,material,BodyType.DynamicBody);
	}
	
	public Body makePolygonShapeBody(Vector2[] vertices, float posX, float posY, int material, BodyType bodyType){
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posX;
		boxBodyDef.position.y = posY;
		Body boxBody = world.createBody(boxBodyDef);
			
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices);
		boxBody.createFixture(makeFixture(material,polygon));
		polygon.dispose();
			
		return boxBody;
	}
	

	public void makeConeSensor(Body body, float size){
		FixtureDef fixtureDef = new FixtureDef();
		//fixtureDef.isSensor = true; // will add in future
			
		PolygonShape polygon = new PolygonShape();
			
		float radius = size;
		Vector2[] vertices = new Vector2[5];
		vertices[0] = new Vector2(0,0);
		for (int i = 2; i < 6; i++) {
		    float angle = (float) (i  / 6.0 * 145 * DEGTORAD); // convert degrees to radians
		    vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
		}
		polygon.set(vertices);
		fixtureDef.shape = polygon;
		body.createFixture(fixtureDef);
		polygon.dispose();
	}
	
	static public FixtureDef makeFixture(int material, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape=shape;
		
		switch(material) {
		case STEEL:
			fixtureDef.density=1f;
			fixtureDef.friction=0.3f;
			fixtureDef.restitution=0.1f;
			break;
		case WOOD:
			fixtureDef.density=0.5f;
			fixtureDef.friction=0.7f;
			fixtureDef.restitution=0.3f;
			break;
		case RUBBER:
			fixtureDef.density=1f;
			fixtureDef.friction=0f;
			fixtureDef.restitution=0.8f;
			break;
		case STONE:
			fixtureDef.density=1f;
			fixtureDef.friction=0.9f;
			fixtureDef.restitution=0.01f;
			break;
		default:
			fixtureDef.density=7f;
			fixtureDef.friction=0.5f;
			fixtureDef.restitution=0.3f;
		}
		
		return fixtureDef;
	}
}
