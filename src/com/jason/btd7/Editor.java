package com.jason.btd7;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static com.jason.btd7.helpers.Artist.HEIGHT;


public class Editor {

    private TileGrid grid;
    private int index;
    private TileType[] types;

    public Editor(){
        this.grid = new TileGrid();
        this.index = 0;

        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
    }

    public void update(){
        grid.Draw();

        // Handle mouse input
        if(Mouse.isButtonDown(0)){
            setTile();
        }

        // Handle keyboard input
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
                moveIndex();
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){

            }
        }
    }


    private void setTile(){
        grid.SetTile((int) Math.floor(Mouse.getX() / 64),
                (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64), types[index] );
    }

    private void moveIndex(){
        index++;
        if(index > types.length - 1){
            index = 0;
        }
    }

}
