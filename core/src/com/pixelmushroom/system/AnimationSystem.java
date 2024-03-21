package com.pixelmushroom.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pixelmushroom.component.AnimationComponent;
import com.pixelmushroom.component.StateComponent;
import com.pixelmushroom.component.TextureComponent;

public class AnimationSystem extends IteratingSystem {

	ComponentMapper<TextureComponent> textureM;
	ComponentMapper<AnimationComponent> animationM;
	ComponentMapper<StateComponent> stateM;
	
	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(Family.all(TextureComponent.class,
				AnimationComponent.class,
				StateComponent.class).get());
		
		textureM = ComponentMapper.getFor(TextureComponent.class);
		animationM = ComponentMapper.getFor(AnimationComponent.class);
		stateM = ComponentMapper.getFor(StateComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AnimationComponent ani = animationM.get(entity);
		StateComponent state = stateM.get(entity);
		
		if (ani.animations.containsKey(state.get())) {
			TextureComponent tex = textureM.get(entity);
			tex.region = (TextureRegion) ani.animations.get(state.get()).getKeyFrame(state.time,state.isLooping);
		}
		
		state.time += deltaTime;
		
	}

}
