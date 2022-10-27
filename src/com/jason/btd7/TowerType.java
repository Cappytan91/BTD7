package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.QuickLoad;

public enum TowerType {

    CannonBlue(new Texture[] {QuickLoad("cannonBaseBlue"), QuickLoad("cannonGunBlue")}, ProjectileType.CannonBall,10, 1000, 3f, 15),
    Cannon(new Texture[] {QuickLoad("cannonBase"), QuickLoad("cannonGun")}, ProjectileType.CannonBall, 30, 1000, 3, 0),
    CannonIce(new Texture[] {QuickLoad("cannonIceBase"), QuickLoad("cannonIceGun")}, ProjectileType.IceBall,2, 128, 1f, 20),
    GruTank(new Texture[] {QuickLoad("gruBody"), QuickLoad("gruHead")}, ProjectileType.Minion,2, 64*4, 2f, 20);

    Texture[] textures;
    public ProjectileType projectileType;
    int damage, range, cost;
    float firingSpeed;

    TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost){
        this.textures = textures;
        this.projectileType = projectileType;
        this.damage = damage;
        this.range = range;
        this.firingSpeed = firingSpeed;
        this.cost = cost;

    }

}
