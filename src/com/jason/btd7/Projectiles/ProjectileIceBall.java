package com.jason.btd7.Projectiles;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectile;
import org.newdawn.slick.opengl.Texture;

public class ProjectileIceBall extends Projectile {

    public ProjectileIceBall(Texture texture, Enemy target, float x, float y, int width, int height, float speed, int damage) {
        super(texture, target, x, y, width, height, speed, damage);
    }

    @Override
    public void doDamage(){
        super.getTarget().setSpeed(4f);
        super.doDamage();
    }

}
