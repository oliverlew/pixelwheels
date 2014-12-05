package com.greenyetilab.race;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Stores all assets
 */
public class Assets {
    public final Skin skin;
    public final TextureRegion car;
    public final TextureRegion wheel;
    public final TextureAtlas atlas;
    public Array<MapInfo> mapInfoList = new Array<MapInfo>();

    Assets() {
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.atlas = new TextureAtlas(Gdx.files.internal("race.atlas"));
        this.car = this.atlas.findRegion("car/car");
        this.wheel = this.atlas.findRegion("car/wheel");

        for (String name: new String[]{
                //"chloe.tmx",
                "experiment.tmx",
                "santa.tmx",
                "big.tmx",
                "roads.tmx",
                "indy.tmx",
                //"antonin.tmx",
        }) {
            mapInfoList.add(new MapInfo(name));
        }
    }
}
