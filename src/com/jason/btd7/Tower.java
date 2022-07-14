package com.jason.btd7;



import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.Tower.TargetType.*;
import static com.jason.btd7.helpers.Artist.*;

import static com.jason.btd7.helpers.Clock.Delta;

public abstract class Tower implements Entity{

    private float x, y, timeSinceLastShot, firingSpeed, angle;
    private int width, height, range, cost;
    public Enemy target;
    private Texture[] textures;
    private CopyOnWriteArrayList<Enemy> enemies;
    private boolean targeted;
    public ArrayList<Projectile> projectiles;
    public TowerType type;
    public int turn;
    enum TargetType{
        First,
        Last,
        Close,
        Strong;
    }
    public TargetType towerTargeting;

    public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
        this.type = type;
        this.textures = type.textures;
        this.range = type.range;
        this.cost = type.cost;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = startTile.getWidth();
        this.height = startTile.getHeight();
        this.enemies = enemies;
        this.targeted = false;
        this.towerTargeting = First;

        this.timeSinceLastShot = 0f;
        this.projectiles = new ArrayList<Projectile>();
        this.firingSpeed = type.firingSpeed;
        this.angle = 0f;
        this.turn = 0;
    }

    private Enemy acquireTarget(){

        if(towerTargeting == Close) {

            Enemy closest = null;

            // Arbitrary distance (larger than map) to help sort enemy distances
            float closestDistance = 10000;
            // Go through each enemy and return the nearest one
            for (Enemy e : enemies) {
                if (isInRange(e) && findDistance(e) < closestDistance && e.isAlive()) {
                    closestDistance = findDistance(e);
                    closest = e;
                }
            }
            // If an enemy exists and is returned, targeted == true
            if (closest != null)
                targeted = true;
            return closest;
        }else if(towerTargeting == First){

            Enemy first = null;

            for (Enemy e : enemies) {
                if(isInRange(e)) {
                    first = e;
                    break;
                }
            }

            if (first != null)
                targeted = true;
            return first;

        }else if(towerTargeting == Last){

            Enemy last = null;

            for (Enemy e : enemies) {
                if(isInRange(e)) {
                    last = e;
                }
            }

            if (last != null)
                targeted = true;
            return last;

        }else if(towerTargeting == Strong){

            Enemy strongest = null;
            float largestHP = 0;

            for (Enemy e : enemies) {
                if(isInRange(e) && e.getHealth() > largestHP && e.isAlive()) {
                    largestHP = e.getHealth();
                    strongest = e;
                }
            }

            if (strongest != null)
                targeted = true;
            return strongest;
        }
        return null;
    }

    protected boolean isInRange(Enemy e){
        float xDistance = Math.abs(e.getX() + 32 - (x + 32));
        float yDistance = Math.abs(e.getY() + 32 - (y + 32));

        if(xDistance < range && yDistance < range)
            return true;
        return false;
    }

    private float findDistance(Enemy e){
        float xDistance = Math.abs(e.getX() - x);
        float yDistance = Math.abs(e.getY() - y);
        return xDistance + yDistance;
    }

    private float calculateAngle(){
        double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(angleTemp);
    }

    // Abstract method for "shoot", must @Override in subclasses
    public abstract void shoot(Enemy target);


    public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList){
        enemies = newList;
    }

    public void update(){

        target = acquireTarget();
        if(target != null) {
            angle = calculateAngle() - turn;
            if (timeSinceLastShot > firingSpeed) {
                shoot(target);
                timeSinceLastShot = 0;
            }
        }

        if(target == null || target.isAlive() == false || !isInRange(target))
            targeted = false;

        timeSinceLastShot += Delta();



        draw();
    }

    public void draw() {
        DrawQuadTex(textures[0], x, y, width, height);
        // This makes the bullet under the shooting part & not the base
        for (Projectile p : projectiles) {
            p.update();
        }
        if(textures.length > 1)
            for (int i = 1; i < textures.length; i++)
                DrawQuadTexRot(textures[i], x, y, width, height, angle);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Enemy getTarget(){
        return target;
    }

    public void setTimeSinceLastShot(int time){
        this.timeSinceLastShot = time;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public int getCost(){
        return cost;
    }

    public CopyOnWriteArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
