package com.jason.btd7;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static com.jason.btd7.helpers.Artist.HEIGHT;
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
        grid.Draw();
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
        grid.SetTile((int) Math.floor(Mouse.getX() / 64),
                (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64), types[index] );
    }

    private void viewTile(){
        Tile t = null;

        if(types[index] == TileType.Grass){
            t = new Tile((float) Math.floor(Mouse.getX() /64 * 64), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / 64 * 64), 64, 64, TileType.GrassE);

        }else if(types[index] == TileType.Dirt){
            t = new Tile((float) Math.floor(Mouse.getX() /64 * 64), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / 64 * 64), 64, 64, TileType.DirtE);

        }else if(types[index] == TileType.Water){
            t = new Tile((float) Math.floor(Mouse.getX() /64 * 64), (float) Math.floor((HEIGHT - Mouse.getY() - 1) / 64 * 64), 64, 64, TileType.WaterE);

        }

        t.Draw();



    }

    private void moveIndex(){
        index++;
        if(index > types.length - 1){
            index = 0;
        }
    }

}
