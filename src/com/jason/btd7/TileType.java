package com.jason.btd7;

public enum TileType {

    Grass("grass", true),
    Dirt("dirt", true),
    Water("water", false);

    String textureName;
    boolean buildable;

    TileType(String textureName, boolean buildable){
        this.textureName = textureName;
        this.buildable = buildable;
    }

}
