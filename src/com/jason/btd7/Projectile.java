package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.Clock.*;
import static com.jason.btd7.helpers.StateManager.game;

public abstract class Projectile implements Entity {

    protected Texture texture;
    private float x, y, speed, xVelocity, yVelocity;
    private int damage, strikeThrough, width, height;
    private Enemy target, hit;
    public CopyOnWriteArrayList<Enemy> unpopable;
    private boolean alive;


    public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height, int strikeThrough){
        this.texture = type.texture;
        this.x = x + TILE_SIZE / 4;
        this.y = y + TILE_SIZE / 4;
        this.width = width;
        this.height = height;
        this.speed = type.speed;
        this.damage = type.damage;
        this.strikeThrough = strikeThrough;
        this.unpopable = new CopyOnWriteArrayList<Enemy>();
        this.target = target;
        this.alive = true;
        this.xVelocity = 0f;
        this.yVelocity = 0f;
        calculateDirection();
    }

    public boolean isOnScreen(){

        if(x < 0 || x > WIDTH && y < 0 || y > HEIGHT){
            return false;
        }
        return true;
    }
    public float calculateAngle(){
        double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(angleTemp);
    }

    protected void calculateDirection(){
        float totalAllowedMovement = 1.0f;
        float xDistanceFromTarget = Math.abs(target.getX() - x -TILE_SIZE / 4 + TILE_SIZE / 2);
        float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;

        // Set direction based on position of target relative to tower
        if(target.getX() < x){
            xVelocity *= -1;
        }
        if(target.getY() < y){
            yVelocity *= -1;
        }

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
        return width;
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
        this.height =  height;
    }

    // Deal damage to Enemy
    public void doDamage(Enemy enemy){
        enemy.damage(damage);
        strikeThrough -= 1;
        if(strikeThrough <= 0)
            alive = false;
    }

    public void update(){

        if(alive) {
            CopyOnWriteArrayList<Enemy> enemyList = game.getWaveManager().getCurrentWave().getEnemyList();
            x += xVelocity * speed * Delta();
            y += yVelocity * speed * Delta();
            for (Enemy e: enemyList) {
                if (CheckCollision(x, y, width, height,
                        e.getX(), e.getY(), e.getWidth(), e.getHeight()) && e.isAlive()) {
                    boolean onUnpop = false;
                    hit = e;
                    for(Enemy k: e.kids){
                        unpopable.add(k);
                    }
                    for(Enemy u: unpopable){
                        if(u == hit){
                            onUnpop = true;
                            break;
                        }

                    }
                    if(!onUnpop) {
                        doDamage(e);
                    }
                }
            }

            draw();
        }
    }


    public void draw(){
        DrawQuadTex(texture, x, y, 32, 32);
    }

    public Enemy getTarget(){
        return target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public void setAlive(boolean status){
        alive = status;
    }

    public Enemy getHit() {
        return hit;
    }

}
