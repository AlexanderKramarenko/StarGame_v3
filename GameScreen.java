package ru.alexander_kramarenko.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.alexander_kramarenko.base.BaseScreen;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.pool.BulletPool;
import ru.alexander_kramarenko.sprite.Background;
import ru.alexander_kramarenko.sprite.EnemyShip;
import ru.alexander_kramarenko.sprite.Star;
import ru.alexander_kramarenko.sprite.StarCruiser;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;
    private StarCruiser starCruiser;

    private Music backgroundMusic;

    private Music shootingSound;

    private EnemyShip enemyShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();

        shootingSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/laser.wav"));

        starCruiser = new StarCruiser(atlas, bulletPool, shootingSound);

        enemyShip = new EnemyShip(atlas, bulletPool, shootingSound);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
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
        starCruiser.resize(worldBounds);
        enemyShip.resize((worldBounds));
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        backgroundMusic.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        starCruiser.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShip.update(delta);
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyed();

    }

    private void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        starCruiser.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShip.draw(batch);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        starCruiser.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        starCruiser.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touchPoint, int pointer, int button) {
        starCruiser.touchDown(touchPoint, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touchPoint, int pointer, int button) {
        starCruiser.touchUp(touchPoint, pointer, button);
        return false;
    }
}
