package com.pixelmushroom.system;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pixelmushroom.component.TextureComponent;
import com.pixelmushroom.component.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem{

	//amount of pixels each meter of box2d objects contains
	static final float PPM=32; 
	
	//gets the height and width of our camera frustum based off width/height of screen and pixel per meter ratio
	static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
	static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
	
	//ratio of pixels to meter
	public static final float PIXELS_TO_METERS = 1.0f / PPM;
	
	private static Vector2 meterDimensions = new Vector2();
	private static Vector2 pixelDimensions = new Vector2();
	
	private SpriteBatch batch;
	
	//Array images so that they can be sorted
	private Array<Entity> renderQueue;
	
	//A comparator to sort images based on the z position of the TransformComponent
	private Comparator<Entity> comparator;
	
	//Reference to camera
	private OrthographicCamera cam;
	
	//component mappers to get components from entities
	private ComponentMapper<TextureComponent> textureM;
	private ComponentMapper<TransformComponent> transformM;
	
	//Static method to get screen width in meters
	public static Vector2 getScreenSizeInMeters() {
		meterDimensions.set(
				Gdx.graphics.getWidth()*PIXELS_TO_METERS,
				Gdx.graphics.getHeight()*PIXELS_TO_METERS);
		return meterDimensions;
	}
	
	//Static method to get screen size in pixels
	public static Vector2 getScreenSizeInPixels() {
		pixelDimensions.set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		return pixelDimensions;
	}
	
	public static float PixelsToMeters(float pixelValue) {
		return pixelValue*PIXELS_TO_METERS;
	}
	
	@SuppressWarnings("unchecked")
	public RenderingSystem(SpriteBatch batch) {
		super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());
		
		textureM = ComponentMapper.getFor(TextureComponent.class);
		transformM = ComponentMapper.getFor(TransformComponent.class);
		
		renderQueue = new Array<Entity>();
		
		this.batch = batch;
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH/2f, FRUSTUM_HEIGHT/2f, 0);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		renderQueue.sort(comparator);
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.enableBlending();
		batch.begin();
		
		for(Entity entity : renderQueue) {
			TextureComponent tex = textureM.get(entity);
			TransformComponent t = transformM.get(entity);
			
			if (tex.region == null || t.isHidden) {
				continue;
			}
			
			float width = tex.region.getRegionWidth();
			float height = tex.region.getRegionHeight();
			
			float originX = width/2f;
			float originY = height/2f;
			
			batch.draw(tex.region,
					t.position.x - originX,
					t.position.y - originY,
					originX,
					originY,
					width,
					height,
					PixelsToMeters(t.scale.x),
					PixelsToMeters(t.scale.y),
					t.rotation);
		}
		
		batch.end();
		renderQueue.clear();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}

}
