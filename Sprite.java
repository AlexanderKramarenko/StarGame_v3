package ru.alexander_kramarenko.base;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.math.Rect;

public class Sprite extends Rect{

    protected float angle;
    protected float scale = 1;
    protected TextureRegion[] regions;
    protected int frame;
    protected Vector2 mouseClickTarget1;

    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
        mouseClickTarget1 = new Vector2();
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );


    }

    public void resize(Rect worldBounds) {
    }

    public boolean touchDown(Vector2 mouseClickTarget, int pointer, int button) {
        return false;
    }


    public boolean touchUp(Vector2 mouseClickTarget, int pointer, int button) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}