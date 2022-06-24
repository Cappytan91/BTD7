package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectiles.ProjectileCannonball;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower {

    public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
        super(type, startTile, enemies);

    }

    @Override
    public void shoot(Enemy target){
        super.projectiles.add(new ProjectileCannonball(super.type.projectileType, super.target, super.getX(), super.getY(), 32, 32));
    }

}
