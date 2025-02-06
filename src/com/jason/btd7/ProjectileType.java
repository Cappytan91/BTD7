package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.QuickLoad;

public enum ProjectileType {


    CannonBall(QuickLoad("bullet"), 10, 1000),
    IceBall(QuickLoad("iceBullet"), 2, 1000),
    Minion(QuickLoad("minion"), 10, 1000);

    Texture texture;
    public int damage;
    float speed;

    ProjectileType(Texture texture, int damage, float speed){
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;

    }

}
