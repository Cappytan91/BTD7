package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower {

    public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
        super(type, startTile, enemies);

    }

}
