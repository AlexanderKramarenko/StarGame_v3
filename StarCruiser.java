package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;

public class StarCruiser extends Sprite {

    private static final float PADDING = 0.1f;

    private static final float HEIGHT = 0.20f;

    private Vector2 actualShiftVector;
    private Vector2 rightShiftVector;
    private Vector2 leftShiftVector;

    TextureRegion[][] starCruiserParts;

    public StarCruiser(TextureAtlas atlas) {
        //    super(atlas.findRegion("main_ship"));

        super(new TextureRegion(
                new TextureAtlas.AtlasSprite(atlas.findRegion("main_ship")))
                .split(195, 287));

        actualShiftVector = new Vector2();
        rightShiftVector = new Vector2(0.1f, 0);
        leftShiftVector = new Vector2(-0.1f, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);

    }

    @Override
    public void update(float delta) {
        centerPosition.mulAdd(actualShiftVector, delta);
    }

    public void shiftRight() {
        actualShiftVector.set(rightShiftVector);
    }

    public void shiftLeft() {
        actualShiftVector.set(leftShiftVector);
    }

    public void freeze() {
        actualShiftVector.set(0, 0);
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            shiftLeft();
        }
        if (keycode == Input.Keys.RIGHT) {
            shiftRight();
        }
        if (keycode == Input.Keys.SPACE) {
            freeze();
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }
}
