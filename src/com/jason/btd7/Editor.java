package com.jason.btd7;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static com.jason.btd7.helpers.Artist.HEIGHT;
import static com.jason.btd7.helpers.Artist.TILE_SIZE;
import static com.jason.btd7.helpers.Leveler.*;


public class Editor {

    private TileGrid grid;
    private int index;
    private TileType[] types;

    public Editor(){
        this.grid = loadMap("mapTest");
        this.index = 0;

        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
    }

    public void update(){
        grid.draw();
        viewTile();

        // Handle mouse input
        if(Mouse.isButtonDown(0)){
            setTile();
        }

        // Handle keyboard input
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
                moveIndex();
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()){
                saveMap("mapTest", grid);
            }
        }
    }


    private void setTile(){
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

    private void moveIndex(){
        index++;
        if(index > types.length - 1){
            index = 0;
        }
    }

}
