package com.jason.btd7.Projectiles;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectile;
import org.newdawn.slick.opengl.Texture;

public class ProjectileBullet extends Projectile {

    public ProjectileBullet(Texture texture, Enemy target, float x, float y, int width, int height, float speed, int damage) {
        super(texture, target, x, y, width, height, speed, damage);
    }

}
