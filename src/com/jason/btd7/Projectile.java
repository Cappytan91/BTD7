package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.Clock.*;

public abstract class Projectile implements Entity {

    private Texture texture;
    private float x, y, speed, xVelocity, yVelocity;
    private int damage, width, height;
    private Enemy target;
    private boolean alive;
    private Tower targetTower;


    public Projectile(Texture texture, Enemy target, float x, float y, int width, int height, float speed, int damage){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
        this.target = target;
        this.alive = true;
        this.xVelocity = 0f;
        this.yVelocity = 0f;
        this.targetTower = null;
        calculateDirection();
    }

    public void calculateDirection(){
        float totalAllowedMovement = 1.0f;
        float xDistanceFromTarget = Math.abs(target.getX() - x -TILE_SIZE / 4 + TILE_SIZE / 2);
        float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;

        if(target.getX() < x){
            xVelocity *= -1;
        }
        if(target.getY() < y){
            yVelocity *= -1;
        }

    }

    private Tower findTargetTower(){
        for (Tower t : Player.towerList) {
            System.out.println("t x: " +  t.getX() + "\np x: " +  (x) + "\n");
            if(inNumRange((int)x, (int) t.getX(), 48) && inNumRange((int) y, (int) t.getY(), 48)) {
                targetTower = t;
                return t;
            }
        }
        //System.exit(0);
        return null;
    }

    private boolean inNumRange(int compare, int number, int numRange){
        System.out.println("t x: " +  number + "\np x: " +  (compare) + "\n");
        if(compare > number - numRange && compare < number + numRange)
            return true;
        return false;
    }

    private boolean isInRange(int range) {

        if(targetTower != null) {
            System.out.println(Math.abs(x - targetTower.getX()));
            if (Math.abs(x - targetTower.getX()) < range && Math.abs(y - targetTower.getY()) < range)
                return true;
        }
        return false;
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

    public void doDamage(){
        target.damage(damage);
        alive = false;
    }

    public void update(){
        if(alive) {
            x += xVelocity * speed * Delta();
            y += yVelocity * speed * Delta();
            if (CheckCollision(x, y, width, height,
                    target.getX(), target.getY(), target.getWidth(), target.getHeight()) && target.isAlive()) {
                doDamage();
            }
            findTargetTower();
            System.out.println(targetTower);
            if(!isInRange(targetTower.getRange() + 64))
                alive = false;//System.out.println("WORK");

            draw();
        }
    }



    public void draw(){
        DrawQuadTex(texture, x, y, 32, 32);
    }

    public Enemy getTarget(){
        return target;
    }

    public void setAlive(boolean status){
        alive = status;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

}
