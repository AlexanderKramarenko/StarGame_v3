package ru.alexander_kramarenko.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.math.MatrixUtils;
import ru.alexander_kramarenko.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;
    protected Vector2 mouseClickTarget;
    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    protected Vector2 velocityToTarget;
    protected Vector2 currentPosition;
    private static final float V_LEN = 2f;


    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        mouseClickTarget = new Vector2();
        Gdx.input.setInputProcessor(this);
        velocityToTarget = new Vector2();
        currentPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("resize worldBounds width = " + worldBounds.getWidth() + " worldBounds height = " + worldBounds.getHeight());
    }


    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseClickTarget.set(screenX, screenBounds.getHalfWidth() - screenY).mul(screenToWorld);
        touchDown(mouseClickTarget, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 mouseClickTarget, int pointer, int button) {
        velocityToTarget.set(mouseClickTarget.cpy().sub(currentPosition)).setLength(V_LEN);
        System.out.println("velocityToTarget = " + velocityToTarget);
        //       System.out.println("touchDown mouseClickTargetX = " + mouseClickTarget.x + " mouseClickTargetY = " + mouseClickTarget.y);
        System.out.println("mouseClickTarget = " + mouseClickTarget);
        System.out.println("currentPosition = " + currentPosition);

        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseClickTarget.set(screenX, screenBounds.getHalfWidth() - screenY).mul(screenToWorld);
        touchUp(mouseClickTarget, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 mouseClickTarget, int pointer, int button) {
        System.out.println("touchUp mouseClickTargetX = " + mouseClickTarget.x + " mouseClickTargetY = " + mouseClickTarget.y);
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseClickTarget.set(screenX, screenBounds.getHalfWidth() - screenY).mul(screenToWorld);
        touchDragged(mouseClickTarget, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 mouseClickTarget, int pointer) {
        System.out.println("touchDragged mouseClickTargetX = " + mouseClickTarget.x + " mouseClickTargetY = " + mouseClickTarget.y);
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println("scrolled amountX = " + amountX + " amountY = " + amountY);
        return false;
    }

    public Vector2 getMouseClickTarget() {
        return mouseClickTarget;
    }
}