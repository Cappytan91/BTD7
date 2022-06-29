package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectiles.ProjectileIceBall;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonIce extends Tower {

    public TowerCannonIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    @Override
    public void shoot(Enemy target) {
        super.projectiles.add(new ProjectileIceBall(super.type.projectileType, super.target, super.getX(), super.getY(), 32, 32));
    }


}
