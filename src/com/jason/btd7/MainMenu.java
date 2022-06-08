package com.jason.btd7;

import org.newdawn.slick.opengl.Texture;

import static com.jason.btd7.helpers.Artist.*;

public class MainMenu {

    private Texture background;

    public MainMenu(){
        background = QuickLoad("mainmenu");
    }

    public void update(){
        DrawQuadTex(background, 0, 0, 2048, 1024);
    }

}
