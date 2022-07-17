package com.jason.btd7.UI;


import com.jason.btd7.Tile;
import com.jason.btd7.Tower;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import java.awt.*;
import java.util.ArrayList;
import static com.jason.btd7.helpers.Artist.*;
import static com.jason.btd7.helpers.StateManager.game;

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
        public boolean visible;

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
            this.visible = true;
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
            this.visible = true;
        }

        public void addButton(Button b){
            menuButtons.add(b);
        }

        public void quickAdd(String name, String buttonTextureName){
            Button b = new Button(name, QuickLoad(buttonTextureName), 0, 0);
            setButton(b);
        }

        public void hide(){
            if(visible) {
                y -= 960;
                for (Button b : menuButtons) {
                    b.setY(b.getY() - 960);
                }
                visible = false;
            }

        }

        public void show(){
            if(!visible) {
                y += 960;
                for (Button b : menuButtons) {
                    b.setY(b.getY() + 960);
                }
                visible = true;
            }

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

    public static class TowerMenu{

        private int x, y, width, height;
        private UI towerUI;
        public UI.Menu towerMenu;
        private String name;
        private boolean leftMouseButtonDown;
        enum Side{
            Left,
            Right;
        }
        private Side side;

        public TowerMenu(String name, int x){

            this.side = Side.Left;
            if(x > 9)
                side = Side.Right;

            this.x = 1088;
            if(side == Side.Right)
                this.x = 0;

            System.out.println(x);

            this.y = 0;
            this.width = 192;
            this.height = 960;
            this.name = name;
            this.leftMouseButtonDown = false;
            setupUI();

        }

        private void setupUI(){
            towerUI = new UI();

            towerUI.createMenuWTex(QuickLoad("towerMenuBG"), name, x, y, width, height, 1, 0);
            towerMenu = towerUI.getMenu(name);

            towerMenu.addButton(new Button("XButton", QuickLoad("xButton"), x + 1, y + 1));
            towerMenu.addButton(new Button("ProfileImage", QuickLoad("water"), x + 32, y + 80, 128, 192));

        }

        public void update(int x, int y){

            if(x > 9)
                side = Side.Right;

            if(side == Side.Right)
                this.x = 0;

            towerUI.draw();
            if(!game.gameLayoutMenu.isButtonClicked(x  + "," + y) && !Mouse.isButtonDown(0) && leftMouseButtonDown)
                if(side == Side.Left && !(Math.floor(Mouse.getX() / 64) >= 17))
                    towerMenu.hide();
                else if(side == Side.Right && !(Math.floor(Mouse.getX() / 64) <= 2))
                    towerMenu.hide();

            if(game.gameLayoutMenu.isButtonClicked(x  + "," + y) && !Mouse.isButtonDown(0) && leftMouseButtonDown) {
                boolean OnAnotherTower = false;
                ArrayList<Tower> towerList =  game.getPlayer().getTowerList();

                for (Tower t : towerList) {
                    if (t.towerMenu.towerMenu.visible)
                        OnAnotherTower = true;
                }
                if(!OnAnotherTower)
                    towerMenu.show();
            }

            leftMouseButtonDown = Mouse.isButtonDown(0);

        }

    }

}
