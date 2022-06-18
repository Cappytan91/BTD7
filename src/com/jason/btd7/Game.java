package com.jason.btd7;

import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Artist.TILE_SIZE;

public class Game {

    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;


    public Game(int[][] map){

        grid = new TileGrid(map);
        waveManager = new WaveManager(new Enemy(QuickLoad("bad"), grid.getTile(10, 6), grid, TILE_SIZE, TILE_SIZE, 60, 25), 2, 2);
        player = new Player(grid, waveManager);
        player.setup();


    }

    public void update(){

        grid.draw();
        waveManager.update();
        player.update();



    }

}
