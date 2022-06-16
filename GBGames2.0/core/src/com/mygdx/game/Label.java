package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Label {
    private BitmapFont bitmapFont;

    public Label(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Standard.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size=size;
        fontParameter.characters="ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю!";
        bitmapFont =fontGenerator.generateFont(fontParameter);
        bitmapFont.setColor(Color.RED);


    }
    public void draw(SpriteBatch batch, String text, int x, int y){
        bitmapFont.draw(batch,text, x,y+bitmapFont.getLineHeight());

            }
}
