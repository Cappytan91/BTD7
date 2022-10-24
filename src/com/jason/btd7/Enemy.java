package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.Clock.*;
import static com.jason.btd7.helpers.StateManager.game;


public class Enemy implements Entity{

    private int width, height, currentCheckpoint, trackLength;
    private float speed, x, y, health, startHealth, percentComplete, distanceTraveled, xMoved, yMoved;
    private Tile startTile;
    private Texture texture, healthBackground, healthForeground, healthBorder, freezeTexture;
    private boolean first, alive, frozen;
    private TileGrid grid;

    private ArrayList<Checkpoint> checkpoints;
    private int[] directions;
    public float originalSpeed, freezeClock;
    public int bloonLvl;
    public CopyOnWriteArrayList<Enemy> kids;

    public Enemy(Texture texture, Tile startTile,TileGrid grid, int width, int height, float speed, float health, int bloonLvl){
        this.texture = texture;
        this.healthBackground = QuickLoad("healthBackground");
        this.healthForeground = QuickLoad("healthForeground");
        this.healthBorder = QuickLoad("healthBorder");
        this.freezeTexture = QuickLoad("frozen");
        this.startTile = startTile;
        this.grid = grid;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.xMoved = 0f;
        this.yMoved = 0f;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.originalSpeed = speed;
        this.health = health;
        this.startHealth = health;
        this.first = true;
        this.alive = true;
        this.freezeClock = 0f;
        this.frozen = false;
        this.trackLength = 1;
        this.percentComplete = 0f;
        this.bloonLvl = bloonLvl;

        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        //x direction
        this.directions[0] = 0;
        //y direction
        this.directions[1] = 0;
        directions = findNextD(startTile);
        this.currentCheckpoint = 0;
        createKids();
        populateCheckpointList();
    }

    public Enemy(Texture texture, Tile startTile,TileGrid grid, int width, int height, float speed, float health, int bloonLvl, float x, float y, int currentCheckpoint){
        this.texture = texture;
        this.healthBackground = QuickLoad("healthBackground");
        this.healthForeground = QuickLoad("healthForeground");
        this.healthBorder = QuickLoad("healthBorder");
        this.freezeTexture = QuickLoad("frozen");
        this.startTile = startTile;
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.xMoved = 0f;
        this.yMoved = 0f;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.originalSpeed = speed;
        this.health = health;
        this.startHealth = health;
        this.first = true;
        this.alive = true;
        this.freezeClock = 0f;
        this.frozen = false;
        this.trackLength = 1;
        this.percentComplete = 0f;
        this.bloonLvl = bloonLvl;

        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        //x direction
        this.directions[0] = 0;
        //y direction
        this.directions[1] = 0;
        directions = findNextD(startTile);
        this.currentCheckpoint = currentCheckpoint;
        createKids();
        populateCheckpointList();
    }

    public void createKids(){
        this.kids = new CopyOnWriteArrayList<Enemy>();
        if(bloonLvl - 1 > 0) {
            kids.add(new Enemy(texture, startTile, grid, TILE_SIZE, TILE_SIZE, speed, health , bloonLvl - 1, x, y, currentCheckpoint));

        }
    }

    public void update(){
        // Check if it's the first time the class is updated, if so do nothing
        if(first){
            first = false;
        }else{
            if(checkpointReached()){
                // Check if there are more checkpoints before moving on
                if(currentCheckpoint + 1 == checkpoints.size()){
                    endOfMazeReached();
                }else{
                    currentCheckpoint++;
                }
            }else {
                // If not at checkpoint, keep going
                x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
                y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;

                xMoved += Math.abs(Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed);
                yMoved += Math.abs(Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed);

            }

            distanceTraveled =  (xMoved + yMoved) / TILE_SIZE;

            if(distanceTraveled != 0)
                percentComplete =  distanceTraveled / trackLength;

            freezeClock += Delta();
        }
    }

    // Run when last checkpoint is reached by enemy
    private void endOfMazeReached(){
        Player.modifyLives(-1);
        die();
    }

    private boolean checkpointReached(){
        boolean reached = false;
        Tile t = checkpoints.get(currentCheckpoint).getTile();
        // check if position reached tile within variance of 3 (arbitrary)
        if(x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3){
            reached = true;
            x = t.getX();
            y = t.getY();

        }

        return reached;
    }


    private void populateCheckpointList(){
        // Add first checkpoint manually based on startTile
        checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));

        int counter = 0;
        boolean cont = true;
        while (cont){
            int[] currentD = findNextD(checkpoints.get(counter).getTile());
            // check if next direction / checkpoint exists, end after 20 checkpoints
            if(currentD[0] == 2 || counter == 20){
                cont = false;
            }else{
                checkpoints.add(findNextC(checkpoints.get(counter).getTile(), directions = findNextD(checkpoints.get(counter).getTile())));

            }
            counter++;
        }

    }

    private Checkpoint findNextC(Tile s, int[] dir){
        Tile next = null;
        Checkpoint c = null;
        // Boolean to decide if next checkpoint is found
        boolean found = false;

        // int to increase after each loop
        int counter = 1;

        while(!found){

            if(s.getXPlace() + dir[0] * counter == grid.getTilesWide() || s.getYPlace() + dir[1] * counter == grid.getTilesHigh() || s.getType() != grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()){
                found = true;
                // move counter back 1 to find tile before new tiletype
                counter -= 1;
                trackLength -= 1;
                next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);

            }
            counter++;
            trackLength++;
        }
        c = new Checkpoint(next, dir[0], dir[1]);

        return c;
    }


    private int[] findNextD(Tile s){
        int[] dir = new int[2];
        Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
        Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace() );
        Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
        Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());

        // Check if current inhabited tiletype matches tiletype above, right down or up
        if(s.getType() == u.getType() && directions[1] != 1){
            dir[0] = 0;
            dir[1] = -1;
        }else if (s.getType() == r.getType() && directions[0] != -1){
            dir[0] = 1;
            dir[1] = 0;
        }else if (s.getType() == d.getType() && directions[1] != -1){
            dir[0] = 0;
            dir[1] = 1;
        }else if (s.getType() == l.getType() && directions[0] != 1){
            dir[0] = -1;
            dir[1] = 0;
        }else{
            dir[0] = 2;
            dir[1] = 2;

        }


        return dir;
    }

    // Take damage from external source
    public void damage(int amount){
        health -= amount;
        if(health <= 0) {
            die();
            Player.modifyCash(5);
        }
    }

    private void die(){
        DrawQuadTex(QuickLoad("pop"), x, y, width, height);
        for(Enemy k: kids){
            k.x = x;
            k.y = y;
            k.currentCheckpoint = currentCheckpoint;
        }
        alive = false;

    }


    public void draw(){
        float healthPercentage = health / startHealth;

        // Enemy texture
        DrawQuadTex(texture, x, y, width, height);
        // Health bar textures
        DrawQuadTex(healthBackground, x, y - 16, width, 8);
        DrawQuadTex(healthForeground, x, y - 16, TILE_SIZE * healthPercentage, 8);
        DrawQuadTex(healthBorder, x, y - 16, width, 8);


    }
    public void drawFrozen(){
        // Enemy Freeze texture
        DrawQuadTex(freezeTexture, x, y, width, height);
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
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

    public boolean isAlive(){
        return alive;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public float getPercentComplete() {
        return percentComplete;
    }

    public int getCurrentCheckpoint() {
        return currentCheckpoint;
    }
}
