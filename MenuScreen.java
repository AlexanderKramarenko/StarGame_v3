package ru.alexander_kramarenko.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.alexander_kramarenko.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 2f;

    private Texture img;
    private Texture wallpaper;
    private Vector2 currentPosition;
    private Vector2 mouseClickTarget;
    private Vector2 mouseClickTargetTemp;
    private Vector2 velocityToTarget;

    @Override
    public void show() {
        super.show();
        img = new Texture("plane-128x256.png");
        wallpaper = new Texture("texture-2048x2048.png");
        currentPosition = new Vector2();
        mouseClickTarget = new Vector2();
        mouseClickTargetTemp = new Vector2();
        velocityToTarget = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        batch.draw(wallpaper, 0, 0, ScreenUtils.getFrameBufferTexture().getRegionWidth(), ScreenUtils.getFrameBufferTexture().getRegionHeight());
        batch.draw(img, currentPosition.x, currentPosition.y);
        batch.end();
        mouseClickTargetTemp.set(mouseClickTarget);
        if (mouseClickTargetTemp.sub(currentPosition).len() <= velocityToTarget.len()) {
            currentPosition.set(mouseClickTarget);
            velocityToTarget.setZero();
        } else {
            currentPosition.add(velocityToTarget);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseClickTarget.set(screenX, Gdx.graphics.getHeight() - screenY);
        velocityToTarget.set(mouseClickTarget.cpy().sub(currentPosition)).setLength(V_LEN);
        return false;
    }
}
