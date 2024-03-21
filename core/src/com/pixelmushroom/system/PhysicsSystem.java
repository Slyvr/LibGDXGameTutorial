package com.pixelmushroom.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pixelmushroom.component.B2dBodyComponent;
import com.pixelmushroom.component.TransformComponent;

public class PhysicsSystem extends IteratingSystem {

	private static final float MAX_STEP_TIME = 1/45f;
	private static float accumulator = 0f;
	private World world;
	private Array<Entity> bodiesQueue;
	private ComponentMapper<B2dBodyComponent> bodyM = ComponentMapper.getFor(B2dBodyComponent.class);
	private ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);
	
	@SuppressWarnings("unchecked")
	public PhysicsSystem(World world) {
		super(Family.all(B2dBodyComponent.class, TransformComponent.class).get());
		this.world=world;
		this.bodiesQueue = new Array<Entity>();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		if (accumulator >= MAX_STEP_TIME) {
			world.step(MAX_STEP_TIME, 6, 2);
			accumulator -= MAX_STEP_TIME;
			
			for(Entity entity : bodiesQueue) {
				TransformComponent transform = transformM.get(entity);
				B2dBodyComponent body = bodyM.get(entity);
				Vector2 position = body.body.getPosition();
				transform.position.x = position.x;
				transform.position.y = position.y;
				transform.rotation = body.body.getAngle()*MathUtils.radiansToDegrees;
			}
		}
		bodiesQueue.clear();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		bodiesQueue.add(entity);
	}

}
