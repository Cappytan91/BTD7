package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectiles.ProjectileIceBall;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;

import java.util.ArrayList;

import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Artist.TILE_SIZE;

public class TowerIce extends Tower {

    public TowerIce(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    @Override
    public void shoot(){
        setTimeSinceLastShot(0);
        getProjectiles().add(new ProjectileIceBall(QuickLoad("iceBullet"), getTarget(), getX() + TILE_SIZE / 2 - TILE_SIZE / 4, getY() + TILE_SIZE / 2 - TILE_SIZE / 4, 32, 32, 1000, getDamage()));
    }
}
