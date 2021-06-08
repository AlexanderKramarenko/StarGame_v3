package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.pool.BulletPool;

public class EnemyShip extends Sprite {

    private static final float PADDING = 0.1f;

    private static final float HEIGHT = 0.15f;


    private Rect worldBounds;

    private Music shootingSound;

    private Vector2 topToBottomShiftVector;

   // TextureRegion[][] enemyShipParts;

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool, Music shootingSound) {

        super(atlas.findRegion("enemy0"), 1, 2, 2);

        topToBottomShiftVector = new Vector2(0, -0.2f);

        this.shootingSound = shootingSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setTop(worldBounds.getTop() - PADDING);
    }

    @Override
    public void update(float delta) {
        centerPosition.mulAdd(topToBottomShiftVector, delta);
        if (getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }
}
