package com.jason.btd7;

import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Clock.Delta;

public class Game {

    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;
    public static final int TILE_SIZE = 64;

    // Temp variables


    public Game(int[][] map){

        grid = new TileGrid(map);
        waveManager = new WaveManager(new Enemy(QuickLoad("bad"), grid.GetTile(10, 6), grid, 64, 64, 60), 2, 2);
        player = new Player(grid, waveManager);


    }

    public void update(){

        grid.Draw();
        waveManager.update();
        player.update();



    }

}
