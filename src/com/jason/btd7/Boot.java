package com.jason.btd7;


import com.jason.btd7.helpers.Clock;
import com.jason.btd7.helpers.StateManager;
import org.lwjgl.opengl.Display;
import static com.jason.btd7.helpers.Artist.*;


public class Boot {

    public Boot(){
        // Call Static method in Artist class to initialize OpenGL calls

        BeginSession();

        // Main game loop
        while(!Display.isCloseRequested()){
            Clock.update();
            StateManager.update();
            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }

    public static void main(String[] args){
        new Boot();
    }
}
