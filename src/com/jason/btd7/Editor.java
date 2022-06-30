package com.jason.btd7;

import com.jason.btd7.Towers.TowerCannonBlue;
import com.jason.btd7.Towers.TowerCannonIce;
import com.jason.btd7.UI.UI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.Artist.QuickLoad;
import static com.jason.btd7.helpers.Leveler.*;


public class Editor {

    private TileGrid grid;
    private int index;
    private TileType[] types;
    private UI editorUI;
    private UI.Menu tilePickerMenu;
    private Texture menuBackground;

    public Editor(){
        this.grid = LoadMap("mapTest");
        this.index = 0;

        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
        this.menuBackground = QuickLoad("menu_BG");

        setupUI();
    }

    private void setupUI(){
        editorUI = new UI();
        editorUI.createMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
        tilePickerMenu = editorUI.getMenu("TilePicker");
        tilePickerMenu.quickAdd("Grass", "grass");
        tilePickerMenu.quickAdd("Dirt", "dirt");
        tilePickerMenu.quickAdd("Water", "water");
    }

    public void update(){
        draw();


        // Handle mouse input
        if(Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);

            if (mouseClicked) {
                if (tilePickerMenu.isButtonClicked("Grass")) {
                    index = 0;
                } else if(tilePickerMenu.isButtonClicked("Dirt")) {
                    index = 1;
                } else if(tilePickerMenu.isButtonClicked("Water")){
                    index = 2;
                } else
                    setTile();

            }
        }

        // Handle keyboard input
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
                moveIndex();
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()){
                SaveMap("mapTest", grid);
            }
        }
    }

    private void draw(){
        grid.draw();
        viewTile();
        DrawQuadTex(menuBackground, 1280, 0, 192, 960);
        editorUI.draw();
    }


    private void setTile(){
        if( !((int) Math.floor(Mouse.getX() / TILE_SIZE) >= 20))
            grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
                (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index] );
    }

    private void viewTile(){
        Tile t = null;

        if(types[index] == TileType.Grass){
            t = new Tile((float) Math.floor(Mouse.getX() /TILE_SIZE * TILE_SIZE), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE * TILE_SIZE), TILE_SIZE, TILE_SIZE, TileType.GrassE);

        }else if(types[index] == TileType.Dirt){
            t = new Tile((float) Math.floor(Mouse.getX() /TILE_SIZE * TILE_SIZE), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE * TILE_SIZE), TILE_SIZE, TILE_SIZE, TileType.DirtE);

        }else if(types[index] == TileType.Water){
            t = new Tile((float) Math.floor(Mouse.getX() /TILE_SIZE * TILE_SIZE), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE * TILE_SIZE), TILE_SIZE, TILE_SIZE, TileType.WaterE);

        }

        t.draw();



    }

    // Allows Editor to change which TileType is selected
    private void moveIndex(){
        index++;
        if(index > types.length - 1){
            index = 0;
        }
    }

}
