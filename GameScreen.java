package ru.alexander_kramarenko.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

import ru.alexander_kramarenko.base.BaseScreen;
import ru.alexander_kramarenko.base.Font;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.pool.BulletPool;
import ru.alexander_kramarenko.pool.EnemyPool;
import ru.alexander_kramarenko.pool.ExplosionPool;
import ru.alexander_kramarenko.sprite.Background;
import ru.alexander_kramarenko.sprite.Bullet;
import ru.alexander_kramarenko.sprite.EnemyShip;
import ru.alexander_kramarenko.sprite.GameOver;
import ru.alexander_kramarenko.sprite.MainShip;
import ru.alexander_kramarenko.sprite.NewGame;
import ru.alexander_kramarenko.sprite.Star;
import ru.alexander_kramarenko.sprite.TrackingStar;
import ru.alexander_kramarenko.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private enum State {PLAYING, GAME_OVER}

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
//    private TrackingStar[] stars;

    private GameOver gameOver;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    private Sound explosionSound;
    private Sound laserSound;
    private Sound bulletSound;
    private Music music;

    private EnemyEmitter enemyEmitter;
    private State state;

    private int frags;

    private NewGame newGame;

    // Параметры пульсации
    private static final float SCALE_STEP = 0.0001f;
    private static final float MAX_SCALE = 0.06f;
    private static final float MIN_SCALE = 0.059f;
    private boolean decreaseSprite = false;
    private boolean increaseSprite = true;
    private float effectiveScale = MAX_SCALE;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;


    private static final float FONT_SIZE = 0.02f;
    private static final float PADDING = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
//        stars = new TrackingStar[][STAR_COUNT];
//        for (int i = 0; i < stars.length; i++) {
//            stars[i] = new TrackingStar(atlas);
//        }
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(worldBounds, explosionPool, bulletPool, bulletSound);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, explosionPool, bulletPool, laserSound);
        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        frags = 0;
        state = State.PLAYING;
    }

    public void startNewGame() {

        frags = 0;
        state = State.PLAYING;
        mainShip.resetMainShip();
        bulletPool.freeAllGameObjects();
        enemyPool.freeAllGameObjects();
        explosionPool.freeAllGameObjects();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        bulletSound.dispose();
        laserSound.dispose();
        music.dispose();
        font.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);

        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage() * 2);
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                    if (enemyShip.isDestroyed()){
                        frags++;
                    }
                }
            } else {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void pulsateStartNewGameSprite() {
        if (decreaseSprite) {
            effectiveScale -= SCALE_STEP;
            System.out.println("effectivescale = " + effectiveScale);

        } else if (increaseSprite) {
            effectiveScale += SCALE_STEP;
            System.out.println("effectivescale = " + effectiveScale);
        }
        if (effectiveScale > MAX_SCALE) {
            increaseSprite = false;
            decreaseSprite = true;
        } else if (effectiveScale < MIN_SCALE) {
            increaseSprite = true;
            decreaseSprite = false;
        }
    }

    private void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            // Условия пульсации
            pulsateStartNewGameSprite();

            newGame.setHeightProportion(effectiveScale);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }
    public void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);



    }
}
