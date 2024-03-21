package com.pixelmushroom;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.pixelmushroom.component.CollisionComponent;
import com.pixelmushroom.model.B2dModel;

public class B2dContactListener implements ContactListener {

	private B2dModel parent;
	
	public B2dContactListener() {
		
	}
	
	public B2dContactListener(B2dModel parent) {
		this.parent = parent;
	}
	
	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa.getBody().getUserData() instanceof Entity) {
			Entity ent = (Entity)fa.getBody().getUserData();
			entityCollision(ent,fb);
			return;
		}
		else if (fb.getBody().getUserData() instanceof Entity) {
			Entity ent = (Entity)fb.getBody().getUserData();
			entityCollision(ent,fa);
		}
	}
	
	private void entityCollision(Entity entA, Fixture fb) {
		if (fb.getBody().getUserData() instanceof Entity) {
			Entity entB = (Entity)fb.getBody().getUserData();
			CollisionComponent colA = entA.getComponent(CollisionComponent.class);
			CollisionComponent colB = entB.getComponent(CollisionComponent.class);
			
			if (colA != null) {
				colA.collisionEntity = entB;
			}
			else if (colB != null) {
				colB.collisionEntity = entA;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		System.out.println("Contact End");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
