package com.pixelmushroom.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
	
	public final Vector3 position = new Vector3();
	public final Vector2 scale = new Vector2(1,1);
	public float rotation = 0;
	public boolean isHidden = false;

}
