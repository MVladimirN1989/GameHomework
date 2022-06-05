package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	AnimPlayer batmanAnim;
	Label label;
	Texture img;

	private int x;

	@Override
	public void create () {
		batch = new SpriteBatch();
		batmanAnim=new AnimPlayer("Sol.png",6,1,16.0f, Animation.PlayMode.LOOP);
		label=new Label(80);
		img=new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;



		batmanAnim.step(Gdx.graphics.getDeltaTime());
		batch.begin();
		batch.draw(img,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(batmanAnim.getFrame(),x,Gdx.graphics.getHeight()/2);
		label.draw(batch, "Привет Евгений!",0,0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		batmanAnim.dispose();
		img.dispose();
	}
}
