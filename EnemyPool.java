package ru.alexander_kramarenko.pool;

import com.badlogic.gdx.audio.Sound;

import ru.alexander_kramarenko.base.SpritesPool;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final Rect worldBounds;
    private final ExplosionPool explosionPool;
    private final BulletPool bulletPool;
    private final Sound bulletSound;

    public EnemyPool(Rect worldBounds, ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds, explosionPool, bulletPool, bulletSound);
    }
}
