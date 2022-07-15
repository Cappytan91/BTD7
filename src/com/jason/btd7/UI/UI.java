package com.jason.btd7.UI;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import java.awt.*;
import java.util.ArrayList;
import static com.jason.btd7.helpers.Artist.*;

public class UI {

    private ArrayList<Button> buttonList;
    private ArrayList<Menu>  menuList;
    private TrueTypeFont font;
    private Font awtFont;

    public UI(){
        buttonList = new ArrayList<Button>();
        menuList = new ArrayList<Menu>();
        awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
    }

    public void drawString(int x, int y, String text){
        font.drawString(x, y, text);
    }

    public void addButton(String name, String textureName, int x, int y){
        buttonList.add(new Button(name, QuickLoad(textureName), x, y));
    }

    public boolean isButtonClicked(String buttonName){
        Button b = getButton(buttonName);
        float mouseY = HEIGHT - Mouse.getY() - 1;
        if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
        mouseY > b.getY() && mouseY < b.getY() + b.getHeight()){
            return true;
        }

        return false;
    }

    private Button getButton(String buttonName){
        for(Button b: buttonList){
            if (b.getName().equals(buttonName)){
                return b;
            }
        }
        return null;
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight){
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
    }

    public void createMenuWTex(Texture texture, String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight){
        menuList.add(new Menu(texture, name, x, y, width, height, optionsWidth, optionsHeight));
    }

    public Menu getMenu(String name){
        for(Menu m: menuList)
            if(name.equals(m.getName()))
                return m;
        return null;
    }

    public void draw(){
        for(Button b: buttonList)
            DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());

        for(Menu m: menuList)
            m.draw();
    }

    public class Menu{

        String name;
        private ArrayList<Button> menuButtons;
        private int x, y, width, height,  buttonAmount, optionsWidth, optionsHeight, padding;
        private Texture texture;

        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight){
            this.texture = null;
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeight = optionsHeight;
            this.padding = (width - (optionsWidth * TILE_SIZE))/ (optionsWidth + 1);
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
        }

        public Menu(Texture texture, String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight){
            this.texture = texture;
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeight = optionsHeight;
            this.padding = (width - (optionsWidth * TILE_SIZE))/ (optionsWidth + 1);
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
        }

        public void addButton(Button b){
            menuButtons.add(b);
        }

        public void quickAdd(String name, String buttonTextureName){
            Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0);
            setButton(b);
        }

        public void quickAddGrid(String name, String buttonTextureName){
            Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0);
            placeButtonGrid(b);
        }

        public void hide(){
            y -= 960;
            for(Button b: menuButtons){
                b.setY(b.getY() - 960);
            }

        }

        public void show(){
            y += 960;
            for(Button b: menuButtons){
                b.setY(b.getY() + 960);
            }

        }

        private void placeButtonGrid(Button b){
            if(optionsWidth != 0)
                b.setY((buttonAmount / optionsWidth) * TILE_SIZE);
            b.setX((buttonAmount % 2) * (padding + TILE_SIZE) + padding);
            buttonAmount++;
            menuButtons.add(b);
        }

        private void setButton(Button b){
            if(optionsWidth != 0)
                b.setY(y + (buttonAmount / optionsWidth) * TILE_SIZE);
            b.setX(x + (buttonAmount % 2) * (padding + TILE_SIZE) + padding);
            buttonAmount++;
            menuButtons.add(b);
        }

        public boolean isButtonClicked(String buttonName){
            Button b = getButton(buttonName);
            float mouseY = HEIGHT - Mouse.getY() - 1;
            if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
                    mouseY > b.getY() && mouseY < b.getY() + b.getHeight()){
                return true;
            }

            return false;
        }

        public Button getButton(String buttonName){
            for(Button b: menuButtons){
                if (b.getName().equals(buttonName)){
                    return b;
                }
            }
            return null;
        }

        public void draw(){
            if(texture != null)
                DrawQuadTex(texture, x, y, width + 64, 960 + 64);

            for(Button b: menuButtons)
                DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        public String getName(){
            return name;
        }

    }

}
