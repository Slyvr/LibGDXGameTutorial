package com.pixelmushroom.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.Input.Keys;

public class KeyboardController implements InputProcessor {

	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;
	
	public boolean isMouse1Down;
	public boolean isMouse2Down;
	public boolean isMouse3Down;
	public boolean isDragged;
	public Vector2 mouseLocation;
	
	public KeyboardController() {
		super();
		mouseLocation = new Vector2();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		switch(keycode) {
		case Keys.LEFT:
			left=true;
			keyProcessed=true;
			break;
		case Keys.RIGHT:
			right=true;
			keyProcessed=true;
			break;
		case Keys.UP:
			up=true;
			keyProcessed=true;
			break;
		case Keys.DOWN:
			down=true;
			keyProcessed=true;
			break;
		}
		return keyProcessed;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch(keycode) {
		case Keys.LEFT:
			left=false;
			keyProcessed=true;
			break;
		case Keys.RIGHT:
			right=false;
			keyProcessed=true;
			break;
		case Keys.UP:
			up=false;
			keyProcessed=true;
			break;
		case Keys.DOWN:
			down=false;
			keyProcessed=true;
			break;
		}
		return keyProcessed;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button==0) {
			isMouse1Down=true;
		}
		else if(button==1) {
			isMouse2Down=true;
		}
		else if(button==2) {
			isMouse3Down=true;
		}
		mouseLocation.x=screenX;
		mouseLocation.y=screenY;
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDragged=false;
		if(button==0) {
			isMouse1Down=false;
		}
		else if(button==1) {
			isMouse2Down=false;
		}
		else if(button==2) {
			isMouse3Down=false;
		}
		mouseLocation.x=screenX;
		mouseLocation.y=screenY;
		return false;
	}
	
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragged=true;
		mouseLocation.x=screenX;
		mouseLocation.y=screenY;
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseLocation.x=screenX;
		mouseLocation.y=screenY;
		return false;
	}
	
	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
}
