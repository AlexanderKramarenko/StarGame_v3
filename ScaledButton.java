package ru.alexander_kramarenko.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledButton extends Sprite {

    private static final float SCALE = 0.9f;

    private boolean pressed;
    private int pointer;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touchPoint, int pointer, int button) {
        if (pressed || !isMe(touchPoint)) {
            return false;
        }
        this.pointer = pointer;
        this.scale = SCALE;
        pressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touchPoint, int pointer, int button) {

        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touchPoint)) {
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    protected abstract void action();
}
