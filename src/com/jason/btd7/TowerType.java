package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.QuickLoad;

public enum TowerType {

    CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, 10),
    Cannon(new Texture[] {QuickLoad("cannonBase"), QuickLoad("cannonGun")}, 30);

    Texture[] textures;
    int damage;

    TowerType(Texture[] textures, int damage){
        this.textures = textures;
        this.damage = damage;

    }

}
