package com.jason.btd7;

import static com.jason.btd7.helpers.Artist.QuickLoad;

public class Game {

    private TileGrid grid;
    private Player player;
    private Wave wave;


    // Temp variables
    TowerCannon tower;

    public Game(int[][] map){

        grid = new TileGrid(map);
        player = new Player(grid);
        wave = new Wave(20, new Enemy(QuickLoad("bad"), grid.GetTile(10, 6), grid, 64, 64, 15));

        tower = new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(5, 7), 10);

    }

    public void update(){
        grid.Draw();
        wave.Update();
        player.Update();

        tower.update();

    }

}
