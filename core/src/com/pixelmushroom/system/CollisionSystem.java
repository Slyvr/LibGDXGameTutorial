package com.pixelmushroom.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.pixelmushroom.component.CollisionComponent;
import com.pixelmushroom.component.PlayerComponent;
import com.pixelmushroom.component.TypeComponent;

public class CollisionSystem extends IteratingSystem {

	ComponentMapper<CollisionComponent> collisionM;
	ComponentMapper<PlayerComponent> playerM;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Family.all(CollisionComponent.class,PlayerComponent.class).get());
		
		collisionM = ComponentMapper.getFor(CollisionComponent.class);
		playerM = ComponentMapper.getFor(PlayerComponent.class);
		
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CollisionComponent cc = collisionM.get(entity);
		
		Entity collidedEntity = cc.collisionEntity;
		if (collidedEntity != null) {
			TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
			if (type != null) {
				switch(type.type) {
					case TypeComponent.ENEMY:
						System.out.println("Player hit enemy");
						break;
					case TypeComponent.SCENERY:
						System.out.println("Player hit scenery");
						break;
					case TypeComponent.OTHER:
						System.out.println("Player hit other");
						break;
				}
				//collision handled, reset component
				cc.collisionEntity = null;
			}
		}
	}

}
