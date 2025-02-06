package com.jason.btd7.Projectiles;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectile;
import com.jason.btd7.ProjectileType;
import org.newdawn.slick.opengl.Texture;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Artist.DrawQuadTexRot;

public class ProjectileMinion extends Projectile {

    private CopyOnWriteArrayList<Enemy> enemies;
    private float angle;

    public ProjectileMinion(ProjectileType type, Enemy target, float x, float y, int width, int height, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, target, x, y, width, height, 3);
        this.enemies = enemies;
        this.angle = 0;

    }

    @Override
    public void draw(){
        if(aquireTarget() != null)
            angle = calculateAngle();
        DrawQuadTexRot(super.texture, super.getX(), super.getY(), 32, 32, angle - 180);
    }

    public Enemy aquireTarget(){
        Enemy closest = null;

        // Arbitrary distance (larger than map) to help sort enemy distances
        float closestDistance = 10000;
        boolean isUnpop = false;
        // Go through each enemy and return the nearest one
        for (Enemy e : enemies) {
            for(Enemy u: unpopable){
                if(e == u){
                    isUnpop = true;
                }
            }
            if (findDistance(e) < closestDistance && e.isAlive() && !isUnpop) {
                closestDistance = findDistance(e);
                closest = e;
            }
        }

        return closest;
    }

    private float findDistance(Enemy e){
        float xDistance = Math.abs(e.getX() - super.getX());
        float yDistance = Math.abs(e.getY() - super.getY());
        return xDistance + yDistance;
    }

    public void doDamage(Enemy enemy){
        super.doDamage(enemy);
        setTarget(aquireTarget());
        if(aquireTarget() != null)
            calculateDirection();
    }


    public void update(){
        super.update();
    }

}
