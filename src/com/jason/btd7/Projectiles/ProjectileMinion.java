package com.jason.btd7.Projectiles;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectile;
import com.jason.btd7.ProjectileType;
import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.DrawQuadTexRot;

public class ProjectileMinion extends Projectile {


    public ProjectileMinion(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height, 3);
    }

    @Override
    public void draw(){
        DrawQuadTexRot(super.texture, super.getX(), super.getY(), 32, 32, calculateAngle() - 180);
    }

}
