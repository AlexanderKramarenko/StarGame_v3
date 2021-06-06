package ru.alexander_kramarenko.pool;

import ru.alexander_kramarenko.base.SpritesPool;
import ru.alexander_kramarenko.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
