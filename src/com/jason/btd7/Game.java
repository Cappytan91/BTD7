package com.jason.btd7;

import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Clock.Delta;

public class Game {

    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;


    private float test;
    // Temp variables
    TowerCannon tower;

    public Game(int[][] map){

        grid = new TileGrid(map);
        player = new Player(grid);
        waveManager = new WaveManager(new Enemy(QuickLoad("bad"), grid.GetTile(10, 6), grid, 64, 64, 60), 2, 2);

        tower = new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(5, 7), 10);

    }

    public void update(){

        grid.Draw();
        waveManager.update();
        player.Update();

        tower.update();

    }

}
