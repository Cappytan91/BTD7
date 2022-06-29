package com.jason.btd7.helpers;

import com.jason.btd7.Editor;
import com.jason.btd7.Game;
import com.jason.btd7.MainMenu;
import com.jason.btd7.TileGrid;

import static com.jason.btd7.helpers.Leveler.LoadMap;

public class StateManager {

    public static enum GameState {
        MAINMENU, GAME, EDITOR
    }
    public static GameState gameState = GameState.MAINMENU;
    public static MainMenu mainMenu;
    public static Game game;
    public static Editor editor;

    static TileGrid map = LoadMap("mapTest");

    public static void update(){

        switch (gameState){
            case MAINMENU:
                if(mainMenu == null)
                    mainMenu = new MainMenu();

                mainMenu.update();
                break;

            case GAME:
                if(game == null)
                    game = new Game(map);

                game.update();
                break;

            case EDITOR:
                if(editor == null)
                    editor = new Editor();

                editor.update();
                break;
        }

    }

    public static void setState(GameState newState){
        gameState = newState;
    }

}
