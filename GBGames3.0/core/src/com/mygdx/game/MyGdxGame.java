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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

	private int x;

	@Override
	public void create () {
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
			camera.position.x--;
			chip.setDir(true);
			chip.setWalk(true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			camera.position.x++;
			chip.setDir(false);
			chip.setWalk(true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
		camera.update();

		batch.begin();
		batch.draw(fon,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.end();

		mapRenderer.setView(camera);
		mapRenderer.render(backGround);

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

		mapRenderer.render(foreGround);
		//Color heroClr=new Color(Color.WHITE);
		//renderer.setColor(heroClr);
		//renderer.begin(ShapeRenderer.ShapeType.Line);
		//for (int i = 0; i < coinList.size(); i++) {
			//coinList.get(i).shapeDraw(renderer, camera);
		//	if (coinList.get(i).isOverLaps(chip.getRect(),camera))
		//		coinList.remove(i);
				//heroClr=Color.BLUE;
		}
		//renderer.setColor(heroClr);
		//renderer.rect(heroRect.x,heroRect.y,heroRect.width,heroRect.height);
		//renderer.end();
	//}

	@Override
	public void dispose () {
		batch.dispose();
		coinList.get(0).dispose();
	}
}
