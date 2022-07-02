package com.jason.btd7;

public enum TileType {

    Grass("grass", true),
    Dirt("dirt", false),
    Water("water", false),
    NULL("water", false),


    // editor blocks
    GrassE("grassE", false),
    DirtE("dirtE", false),
    WaterE("waterE", false);

    String textureName;
    boolean buildable;

    TileType(String textureName, boolean buildable){
        this.textureName = textureName;
        this.buildable = buildable;
    }


}
