package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimPlayer {
    Texture texture;
    Animation<TextureRegion> animation;
    private float time;
    private boolean loop;

    public AnimPlayer(String name, int widht, int height, float fps,Animation.PlayMode mode) {
        this.loop=loop;
        texture=new Texture(name);
        TextureRegion region=new TextureRegion(texture);
        TextureRegion[][]regions=region.split(region.getRegionWidth()/widht, region.getRegionHeight()/height);
        TextureRegion[]regions1=new TextureRegion[widht*height];

        int cnt=0;
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].length; j++) {
                regions1[cnt++]=regions[i][j];
            }
        }

        animation=new Animation<>(1.0f/fps,regions1);
        animation.setPlayMode(mode);
    }
    public void step(float time){
        this.time += time;
    }
    public void reSetTime(){time=0;}
    public TextureRegion getFrame(){
        return animation.getKeyFrame(time);
    }
    public boolean isFinished(){return animation.isAnimationFinished(time);}
    public void setPlayMode(Animation.PlayMode mode){animation.setPlayMode(mode);}

    public void dispose(){
        texture.dispose();
    }
}

