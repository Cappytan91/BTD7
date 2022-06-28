package com.jason.btd7;

import com.jason.btd7.Towers.TowerCannonBlue;
import com.jason.btd7.Towers.TowerIce;
import com.jason.btd7.helpers.Clock;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

import static com.jason.btd7.helpers.Artist.*;

public class Player {

    private TileGrid grid;
    private TileType[] types;
    private WaveManager waveManager;
    private ArrayList<Tower> towerList;
    private boolean leftMouseButtonDown, rightMouseButtonDown;
    public static int Cash, Lives;


    public Player(TileGrid grid, WaveManager waveManager){
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<Tower>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        Cash = 0;
        Lives = 0;
    }

    // Initialize starting cash and lives for player
    public void setup(){
        Cash = 200;
        Lives = 10;
    }

    public static boolean modifyCash(int amount){
        if(Cash + amount >= 0){
            Cash += amount;
            System.out.println(Cash);
            return true;
        }
        System.out.println(Cash);
        return false;
    }

    public static void modifyLives(int amount){
        Lives += amount;
    }

    public void update(){
        // Update all towers in game
        for(Tower t : towerList){
            t.update();
            t.draw();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
        }

        // Handle mouse input
        if(Mouse.isButtonDown(0) && !leftMouseButtonDown){

            if(modifyCash(-20))
                towerList.add(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE), waveManager.getCurrentWave().getEnemyList()));
        }
        if(Mouse.isButtonDown(1) && !rightMouseButtonDown){
            if(modifyCash(-55))
                towerList.add(new TowerIce(TowerType.CannonIce, grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE), waveManager.getCurrentWave().getEnemyList()));
        }

        leftMouseButtonDown = Mouse.isButtonDown(0);
        rightMouseButtonDown = Mouse.isButtonDown(1);

        // Handle keyboard input
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
                Clock.ChangeMultiplier(0.2f);
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
                Clock.ChangeMultiplier(-0.2f);
            }

        }

    }




}
