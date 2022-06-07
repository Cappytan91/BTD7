package com.jason.btd7;

import java.util.ArrayList;

import static com.jason.btd7.helpers.Clock.*;

public class Wave {

    private float timeSinceLastSpawn, spawnTime;
    private Enemy enemyType;
    private ArrayList<Enemy> enemyList;
    private int enemiesPerWave;

    public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave ){
        this.enemyType = enemyType;
        this.spawnTime = spawnTime;
        this.enemiesPerWave = enemiesPerWave;
        timeSinceLastSpawn = 0;
        enemyList = new ArrayList<Enemy>();

        Spawn();
    }

    public void Update(){
        timeSinceLastSpawn += Delta();
        if(timeSinceLastSpawn > spawnTime){
            Spawn();
            timeSinceLastSpawn = 0;
        }

        for (Enemy e: enemyList){
            if(e.isAlive()){
                e.Update();
                e.Draw();
            }

        }
    }

    private void Spawn(){
        enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), 64, 64, enemyType.getSpeed()));
    }

}
