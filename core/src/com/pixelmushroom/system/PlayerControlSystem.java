package com.pixelmushroom.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.pixelmushroom.component.B2dBodyComponent;
import com.pixelmushroom.component.PlayerComponent;
import com.pixelmushroom.component.StateComponent;
import com.pixelmushroom.controller.KeyboardController;

public class PlayerControlSystem extends IteratingSystem {

	ComponentMapper<PlayerComponent> playerM;
	ComponentMapper<B2dBodyComponent> bodyM;
	ComponentMapper<StateComponent> stateM;
	
	KeyboardController controller;
	
	@SuppressWarnings("unchecked")
	public PlayerControlSystem(KeyboardController controller) {
		super(Family.all(PlayerComponent.class).get());
		this.controller = controller;
		playerM = ComponentMapper.getFor(PlayerComponent.class);
		bodyM = ComponentMapper.getFor(B2dBodyComponent.class);
		stateM = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodyM.get(entity);
		StateComponent state = stateM.get(entity);
		
		if(b2body.body.getLinearVelocity().y > 0){
			state.set(StateComponent.STATE_FALLING);
		}
		
		if(b2body.body.getLinearVelocity().y == 0){
			if(state.get() == StateComponent.STATE_FALLING){
				state.set(StateComponent.STATE_NORMAL);
			}
			if(b2body.body.getLinearVelocity().x != 0){
				state.set(StateComponent.STATE_MOVING);
			}
		}
		
		if(controller.left){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		if(controller.right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		
		if(!controller.left && ! controller.right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
		}
		
		if(controller.up && 
				(state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
			//b2body.body.applyForceToCenter(0, 3000,true);
			b2body.body.applyLinearImpulse(0, 10, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
			state.set(StateComponent.STATE_JUMPING);
		}
	}

}
