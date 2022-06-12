package com.jason.btd7;

import com.jason.btd7.UI.*;
import com.jason.btd7.helpers.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.*;

public class MainMenu {

    private Texture background;
    private UI menuUI;

    public MainMenu(){
        background = QuickLoad("mainmenu");
        menuUI = new UI();
        menuUI.addButton("Play", "playbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.64f));
        menuUI.addButton("Edit", "editorbutton", WIDTH / 3 - 128, (int) (HEIGHT * 0.74f));
        menuUI.addButton("quit", "quitbutton", WIDTH * 2 / 3 - 128, (int) (HEIGHT * 0.74f));
    }

    public void updateButtons(){
        if(Mouse.isButtonDown(0)){
            if(menuUI.isButtonClicked("Play")){
                StateManager.setState(StateManager.GameState.GAME);
            }
        }
    }

    public void update(){
        DrawQuadTex(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
    }

}
