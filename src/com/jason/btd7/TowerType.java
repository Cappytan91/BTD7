package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.QuickLoad;

public enum TowerType {

    CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, 10, 1000, 3),
    Cannon(new Texture[] {QuickLoad("cannonBase"), QuickLoad("cannonGun")}, 30, 1000, 3);

    Texture[] textures;
    int damage, range;
    float firingSpeed;

    TowerType(Texture[] textures, int damage, int range, float firingSpeed){
        this.textures = textures;
        this.damage = damage;
        this.range = range;
        this.firingSpeed = firingSpeed;

    }

}
