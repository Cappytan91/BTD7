package com.jason.btd7;

import com.jason.btd7.Towers.TowerCannonBlue;
import com.jason.btd7.Towers.TowerCannonIce;
import com.jason.btd7.UI.Button;
import com.jason.btd7.UI.UI;
import org.lwjgl.input.Mouse;

import static com.jason.btd7.helpers.Artist.*;

public class Game {

    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;
    private UI gameUI;
    private boolean leftMouseButtonDown;
    private UI.Menu towerPickerMenu;

    public Game(TileGrid grid){

        this.grid = grid;
        waveManager = new WaveManager(new Enemy(QuickLoad("bad"), grid.getTile(2, 14), grid, TILE_SIZE, TILE_SIZE, 60, 25), 2, 2);
        player = new Player(grid, waveManager);
        player.setup();
        this.leftMouseButtonDown = false;
        setupUI();

    }

    private void setupUI(){
        gameUI = new UI();
        //towerPickerUI.addButton("CannonIce", "cannonIceGun", 0, 0);
        //towerPickerUI.addButton("CannonBlue", "cannonGunBlue", 64, 0);
        gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);
        towerPickerMenu = gameUI.getMenu("TowerPicker");
        towerPickerMenu.quickAdd("CannonIce", "cannonIceGun");
        towerPickerMenu.quickAdd("CannonBlue", "cannonGunBlue");
    }

    private void updateUI(){
        gameUI.draw();

        if(Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);

            if (mouseClicked && !leftMouseButtonDown) {
                if (towerPickerMenu.isButtonClicked("CannonIce"))
                    player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
                if (towerPickerMenu.isButtonClicked("CannonBlue"))
                    player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
            }
        }
        leftMouseButtonDown = Mouse.isButtonDown(0);
    }

    public void update(){
        grid.draw();
        waveManager.update();
        player.update();
        DrawQuadTex(QuickLoad("menu_BG2"), 1280, 0, 192, 960);
        updateUI();

    }

}
