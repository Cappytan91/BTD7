package com.jason.btd7.Projectiles;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectile;
import com.jason.btd7.Tower;
import org.newdawn.slick.opengl.Texture;

import java.util.Random;

import static com.jason.btd7.helpers.Artist.TILE_SIZE;

public class ProjectileFireBall extends Projectile {

    public ProjectileFireBall(Texture texture, Enemy target, float x, float y, int width, int height, float speed, int damage) {
        super(texture, target, x, y, width, height, speed, damage);
    }

    @Override
    public void calculateDirection(){
        float totalAllowedMovement = 1.0f;
        float xDistanceFromTarget = Math.abs(getTarget().getX() - getX() - 64 + new Random().nextInt(1, 128) - TILE_SIZE / 4 + TILE_SIZE / 2);
        float yDistanceFromTarget = Math.abs(getTarget().getY() - getY() - TILE_SIZE / 4 + TILE_SIZE / 2);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        float xVelocity = xPercentOfMovement;
        float yVelocity = totalAllowedMovement - xPercentOfMovement;

        if(getTarget().getX() < getX()){
            xVelocity *= -1;
        }
        if(getTarget().getY() < getY() && getTarget().getX() - 32 != getX()){
            yVelocity *= -1;
        }

        setxVelocity(xVelocity);
        setyVelocity(yVelocity);

    }


}
