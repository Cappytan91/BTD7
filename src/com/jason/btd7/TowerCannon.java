package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.DrawQuadTex;

public class TowerCannon {
    private float x, y;
    private int width, height, damage;
    private Texture texture;
    private Tile startTile;

    public TowerCannon(Texture texture, Tile startTile, int damage){
        this.texture = texture;
        this.startTile = startTile;
        this.x = startTile.getX();
        this.x = startTile.getY();
        this.damage = damage;
    }

    public void update(){

    }

    public void draw(){
        DrawQuadTex(texture, x, y, width, height);
    }

}
