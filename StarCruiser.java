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

public class StarCruiser extends Sprite {

    private static final float PADDING = 0.1f;

    private static final float HEIGHT = 0.15f;

    private static final int INVALID_POINTER = -1;

    private Vector2 actualShiftVector;
    private Vector2 rightShiftVector;
    private Vector2 leftShiftVector;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;

    private BulletPool bulletPool;

    private TextureRegion bulletRegion;

    private Vector2 bulletV;

    private Vector2 bulletPosition;

    private Music shootingSound;

    TextureRegion[][] starCruiserParts;

    private float timeSeconds = 0f;
    private float shootingPeriod = 0.5f;
    private boolean autoShooting;

    private boolean backgroundMusicOff;

    private boolean shootingSoundOff;

    public StarCruiser(TextureAtlas atlas, BulletPool bulletPool, Music shootingSound) {

        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletPosition = new Vector2();

        actualShiftVector = new Vector2();
        rightShiftVector = new Vector2(0.2f, 0);
        leftShiftVector = new Vector2(-0.2f, 0);

        this.shootingSound = shootingSound;

        autoShooting = false;

        backgroundMusicOff = false;

        shootingSoundOff = false;

    }

    @Override
    public void resize(Rect worldBounds) {
 //       super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);

    }

    @Override
    public void update(float delta) {

        centerPosition.mulAdd(actualShiftVector, delta);
        if (autoShooting) {
            timeSeconds += Gdx.graphics.getDeltaTime();
            if (timeSeconds > shootingPeriod) {
                timeSeconds -= shootingPeriod;
                shoot();
            }
        }

// Корабль ограничен стенками - начало -

        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            freeze();
        }

        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            freeze();
        }

// Корабль ограничен стенками - конец -

// Корабль бегает по кругу - начало -

//        if (getLeft() > worldBounds.getRight()){
//            setRight(worldBounds.getLeft());
//        }
//
//        if (getRight() < worldBounds.getLeft()){
//            setLeft(worldBounds.getRight());
//        }
// Корабль бегает по кругу - конец -

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

        switch(keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                shiftLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                shiftRight();
                break;
            case Input.Keys.UP:
               shoot();
                break;
            case Input.Keys.F:
                 if (!autoShooting)
                     autoShooting = true;
                  else
                      autoShooting = false;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch(keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    shiftRight();
                }
                else {
                    freeze();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft){
                    shiftLeft();
                }
                else {
                    freeze();
                }
                break;


        }

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touchPoint, int pointer, int button) {


        if( touchPoint.x < worldBounds.centerPosition.x){
            if (leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            shiftLeft();
        } else {
            if (rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            shiftRight();
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touchPoint, int pointer, int button) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER){
                shiftRight();
            } else {
                freeze();
            }
        } else if (pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER){
                shiftLeft();
            } else {
                freeze();
            }
        }
        return false;
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bulletPosition.set(centerPosition.x, centerPosition.y + getHalfHeight());
        bullet.setBullet(this, bulletRegion, bulletPosition, bulletV, worldBounds, 1, 0.01f );
        shootingSound.play();

    }
}
