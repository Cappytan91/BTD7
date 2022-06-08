package com.jason.btd7.helpers;

import com.jason.btd7.Editor;
import com.jason.btd7.Game;
import com.jason.btd7.MainMenu;

public class StateManager {

    public static enum GameState {
        MAINMENU, GAME, EDITOR
    }
    public static GameState gameState = GameState.MAINMENU;
    public static MainMenu mainMenu;
    public static Game game;
    public static Editor editor;

    public static void update(){

        switch (gameState){
            case MAINMENU:
                if(mainMenu == null){
                    mainMenu = new MainMenu();
                }
                mainMenu.update();
                break;

            case GAME:

                break;

            case EDITOR:

                break;
        }

    }

}
