package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;

public class Bullet extends Sprite {

    private Rect worldbounds;
    private Vector2 bulletSpeed;
    private int damage;
    private Sprite owner;

    public Bullet(){

        regions = new TextureRegion[1];
        bulletSpeed = new Vector2();
    }

    public void setBullet(
            Sprite owner,
            TextureRegion region,
            Vector2 initialPosition,
            Vector2 initialSpeed,
            Rect worldbounds,
            int damage,
            float height

    ){
        this.owner = owner;
        this.regions[0] = region;
        this.centerPosition.set(initialPosition);
        this.bulletSpeed.set(initialSpeed);
        this.worldbounds = worldbounds;
        this.damage = damage;
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        centerPosition.mulAdd(bulletSpeed, delta);
        if (isOutside(worldbounds)){
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
