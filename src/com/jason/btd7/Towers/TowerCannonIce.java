package com.jason.btd7.Towers;

import com.jason.btd7.Enemy;
import com.jason.btd7.Projectiles.ProjectileIceBall;
import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import com.jason.btd7.TowerType;
import com.jason.btd7.UI.UI;
import org.lwjgl.input.Mouse;


import java.util.concurrent.CopyOnWriteArrayList;

import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Clock.Delta;
import static com.jason.btd7.helpers.StateManager.game;

public class TowerCannonIce extends Tower {

    private float freezeTime, time;
    private CopyOnWriteArrayList<Enemy> enemiesInRange;
    private UI towerUI;
    private UI.Menu towerMenu;
    private boolean leftMouseButtonDown;

    public TowerCannonIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
        this.enemiesInRange = new CopyOnWriteArrayList<Enemy>();
        this.freezeTime = 0.7f;
        this.time = 0;
        this.leftMouseButtonDown = false;
        setupUI();
    }

    private void setupUI(){
        towerUI = new UI();

        towerUI.createMenuWTex(QuickLoad("towerMenuBG"), "IceCannonMenu", 1088, 0, 192, 960, 1, 0);
        towerMenu = towerUI.getMenu("IceCannonMenu");
        towerMenu.quickAdd("CannonIce", "cannonIceGun");
        towerMenu.quickAdd("CannonBlue", "cannonGunBlue");
        towerMenu.quickAdd("GruTank", "gruHead");

    }

    @Override
    public void shoot(Enemy target) {
        for (Enemy e : enemiesInRange) {
            if(super.isInRange(e)) {
                e.setSpeed(4);
                e.setFrozen(true);
            }
        }
    }

    @Override
    public void update(){

        for (Enemy e : getEnemies()) {
            if(super.isInRange(e))
                enemiesInRange.add(e);
            if(e.freezeClock > freezeTime) {
                e.setFrozen(false);
                e.setSpeed(e.originalSpeed);
                e.freezeClock = 0;
            }
        }

        super.update();
        towerUI.draw();

        if(towerMenu.isButtonClicked("CannonIce") && Mouse.isButtonDown(0))
            towerMenu.hide();
        if(game.gameLayoutMenu.isButtonClicked(getXPlace()  + "," + getYPlace()) && !Mouse.isButtonDown(0) && leftMouseButtonDown)
            towerMenu.show();
        leftMouseButtonDown = Mouse.isButtonDown(0);
    }



}
