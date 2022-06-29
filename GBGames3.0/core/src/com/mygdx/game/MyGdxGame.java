package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	//private ShapeRenderer renderer;
	//private AnimPlayer batmanAnim;
	private Label label;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private List<Coin>coinList;
	private Texture fon;
	//private Rectangle heroRect;
	private MyCharacter chip;
	private int[]foreGround,backGround;
	private int score;

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean start;
	private Body heroBody;

	@Override
	public void create () {
		world=new World(new Vector2(0,-9.81f), true);
		debugRenderer=new Box2DDebugRenderer();

		BodyDef def=new BodyDef();
		FixtureDef fdef=new FixtureDef();
		PolygonShape polygonShape=new PolygonShape();

		def.position.set(new Vector2(100f,170f));
		def.type=BodyDef.BodyType.StaticBody;
		fdef.density=1;
		fdef.friction=0f;
		fdef.restitution=0.0f;

		polygonShape.setAsBox(100,10);
		fdef.shape=polygonShape;

		world.createBody(def).createFixture(fdef);

		def.position.set(new Vector2(100f,210f));

		polygonShape.setAsBox(10,10,new Vector2(0,0),45*MathUtils.radiansToDegrees);
		fdef.shape=polygonShape;

		world.createBody(def).createFixture(fdef);

		def.type=BodyDef.BodyType.DynamicBody;
	/*	for (int i = 0; i < 20; i++) {
			def.position.set(new Vector2(MathUtils.random(0,150),400f));
			def.gravityScale=MathUtils.random(0.5f,5f);
			float size=MathUtils.random(3f,15f);
			polygonShape.setAsBox(size,size);
			fdef.shape=polygonShape;
			world.createBody(def).createFixture(fdef);
		}*/

		def.position.set(new Vector2(100,300f));
		def.gravityScale=1.0f;
		float size=5;
		polygonShape.setAsBox(size,size);
		fdef.density=0;
		fdef.shape=polygonShape;
		heroBody=world.createBody(def);
		heroBody.createFixture(fdef);

		polygonShape.dispose();

		chip=new MyCharacter();
		fon=new Texture("fons.png");
		map=new TmxMapLoader().load("maps/map2.tmx");
		mapRenderer=new OrthogonalTiledMapRenderer(map);

		foreGround = new int[1];
		foreGround[0] = map.getLayers().getIndex("Слой тайлов 2");
		backGround = new int[1];
		backGround[0] = map.getLayers().getIndex("Слой тайлов 1");

		batch=new SpriteBatch();
		//renderer=new ShapeRenderer();

		label=new Label(40);

		camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		RectangleMapObject o =(RectangleMapObject) map.getLayers().get("Слой объектов 1").getObjects().get("camera");  //получить доступ к объектам
		camera.position.x=o.getRectangle().x;
		camera.position.y=o.getRectangle().y;
		camera.zoom=0.7f;
		camera.update();


		coinList=new ArrayList<>();
		MapLayer ml=map.getLayers().get("монетки");
		if (ml!=null){
			MapObjects mo = ml.getObjects();
			if (mo.getCount()>0){
				for (int i=0;i<mo.getCount();i++){
				RectangleMapObject tmpMo=(RectangleMapObject) ml.getObjects().get(i);
					Rectangle rect =tmpMo.getRectangle();
					coinList.add(new Coin(new Vector2(rect.x,rect.y)));
				}
			}

		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		chip.setWalk(false);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			heroBody.applyForceToCenter(new Vector2(-300.0f,0.0f),true);
			//camera.position.x--;
			chip.setDir(true);
			chip.setWalk(true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			heroBody.applyForceToCenter(new Vector2(300.0f,0.0f),true);
			//camera.position.x++;
			chip.setDir(false);
			chip.setWalk(true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			heroBody.applyForceToCenter(new Vector2(0.0f,3000.0f),true);
			//camera.position.y++;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			heroBody.applyForceToCenter(new Vector2(0.0f,-3000.0f),true);
			//camera.position.y--;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) start=true;

		camera.position.x=heroBody.getPosition().x;
		camera.position.y=heroBody.getPosition().y;

		camera.update();

		batch.begin();
		batch.draw(fon,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.end();

		mapRenderer.setView(camera);
		mapRenderer.render(backGround);
		mapRenderer.render(foreGround);

		//batmanAnim.step(Gdx.graphics.getDeltaTime());

		batch.begin();
		batch.draw(chip.getFrame(), Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		label.draw(batch, "Монеток собрано "+String.valueOf(score),0,0);

		for (int i = 0; i < coinList.size(); i++) {
			coinList.get(i).draw(batch, camera);
			if (coinList.get(i).isOverLaps(chip.getRect(), camera)){
				coinList.remove(i);
				score++;
			}
		}
		batch.end();

		//Color heroClr=new Color(Color.WHITE);
		//renderer.setColor(heroClr);
		//renderer.begin(ShapeRenderer.ShapeType.Line);
		//for (int i = 0; i < coinList.size(); i++) {
			//coinList.get(i).shapeDraw(renderer, camera);
		//	if (coinList.get(i).isOverLaps(chip.getRect(),camera))
		//		coinList.remove(i);
				//heroClr=Color.BLUE;
		//renderer.setColor(heroClr);
		//renderer.rect(heroRect.x,heroRect.y,heroRect.width,heroRect.height);
		//renderer.end();
		//}

		if (start) world.step(1/60.0f,3,3);
		debugRenderer.render(world,camera.combined);
		}


	@Override
	public void dispose () {
		batch.dispose();
		coinList.get(0).dispose();
		world.dispose();
	}
}
