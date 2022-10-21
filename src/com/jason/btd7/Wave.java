package com.jason.btd7;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Artist.TILE_SIZE;
import static com.jason.btd7.helpers.Clock.*;
import static com.jason.btd7.helpers.StateManager.game;

public class Wave {

    private float timeSinceLastSpawn, spawnTime;
    private Enemy enemyType;
    private CopyOnWriteArrayList<Enemy> enemyList;
    private int enemiesPerWave, enemiesSpawned;
    private boolean waveCompleted;

    public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave ){
        this.enemyType = enemyType;
        this.spawnTime = spawnTime;
        this.enemiesPerWave = enemiesPerWave;
        this.enemiesSpawned = 0;
        this.timeSinceLastSpawn = 0;
        this.enemyList = new CopyOnWriteArrayList<Enemy>();
        this.waveCompleted = false;

        spawn();
    }

    public void update(){
        // Assume all enemies are dead, until for loop proves otherwise
        boolean allEnemiesDead = true;
        if(enemiesSpawned < enemiesPerWave) {
            allEnemiesDead = false;
            timeSinceLastSpawn += Delta();
            if (timeSinceLastSpawn > spawnTime) {
                spawn();
                timeSinceLastSpawn = 0;
            }
        }
        for (Enemy e: enemyList){
            if(e.isAlive()){
                allEnemiesDead = false;
                e.update();
                e.draw();
                if(e.isFrozen())
                    e.drawFrozen();
            }else{
                if(e.bloonLvl - 1 > 0)
                    enemyList.add(new Enemy(enemyType.getTexture(), game.getGrid().getTile((int) e.getX() / TILE_SIZE, (int) e.getY() / TILE_SIZE), enemyType.getTileGrid(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(), enemyType.getHealth(), e.bloonLvl - 1));
                enemyList.remove(e);

            }

        }
        if(allEnemiesDead){
            waveCompleted = true;
        }
    }

    private void spawn(){
        enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(), enemyType.getHealth(), 2));
        enemiesSpawned++;
    }


    public boolean isCompleted(){
        return waveCompleted;
    }

    public CopyOnWriteArrayList<Enemy> getEnemyList() {
        return enemyList;
    }
}
