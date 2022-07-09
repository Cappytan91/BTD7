package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectiles.ProjectileIceBall;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Clock.Delta;

public class TowerCannonIce extends Tower {

    private float freezeTime, time;

    public TowerCannonIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);

        this.freezeTime = 0.5f;
        this.time = 0;
    }

    @Override
    public void shoot(Enemy target) {
        for (Enemy e : getEnemies()) {
            if(super.isInRange(e))
                e.setSpeed(15);
        }
    }

    @Override
    public void update(){

        for (Enemy e : getEnemies()) {
            if(time > freezeTime) {
                e.setSpeed(e.originalSpeed);
                time = 0;
            }
        }

        time += Delta();
        super.update();
    }



}
