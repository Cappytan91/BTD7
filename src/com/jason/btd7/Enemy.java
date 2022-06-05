package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.Clock.*;


public class Enemy {

    private int width, height, health;
    private float speed, x, y;
    private Tile startTile;
    private Texture texture;
    private boolean first = true;
    private TileGrid grid;

    private ArrayList<Checkpoint> checkpoints;
    private int[] directions;

    public Enemy(Texture texture, Tile startTile,TileGrid grid, int width, int height, float speed){
        this.startTile = startTile;
        this.grid = grid;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.speed = speed;

        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        //x direction
        this.directions[0] = 0;
        //y direction
        this.directions[1] = 0;
        directions = FindNextD(startTile);

    }

    public void Update(){
        if(first){
            first = false;
        }else{
            x += Delta() * directions[0];
            y += Delta() * directions[1];

        }
    }

    private int[] FindNextD(Tile s){
        int[] dir = new int[2];
        Tile u = grid.GetTile(s.getXPlace(), s.getYPlace() - 1);
        Tile r = grid.GetTile(s.getXPlace() + 1, s.getYPlace() );
        Tile d = grid.GetTile(s.getXPlace(), s.getYPlace() + 1);
        Tile l = grid.GetTile(s.getXPlace() - 1, s.getYPlace());

        if(s.getType() == u.getType()){
            dir[0] = 0;
            dir[1] = -1;
        }else if (s.getType() == r.getType()){
            dir[0] = 1;
            dir[1] = 0;
        }else if (s.getType() == d.getType()){
            dir[0] = 0;
            dir[1] = 1;
        }else if (s.getType() == l.getType()){
            dir[0] = -1;
            dir[1] = 0;
        }


        return dir;
    }

/*
    private boolean pathContinues(){
        boolean answer = true;

        Tile myTile = grid.GetTile((int) (x / 64), (int) (y / 64));
        Tile nextTile = grid.GetTile((int) (x / 64) + 1, (int) (y / 64));

        if(myTile.getType() != nextTile.getType()){
            answer = false;
        }

        return answer;
    }
    */


    public void Draw(){
        DrawQuadTex(texture, x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public TileGrid getTileGrid(){
        return grid;
    }
}
