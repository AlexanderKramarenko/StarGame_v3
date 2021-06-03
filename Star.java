package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.math.Rnd;

public class Star extends Sprite {

    private final Vector2 velocity;

    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        velocity = new Vector2();
        float velocityX = Rnd.nextFloat(-0.0005f, 0.0005f);
        float velocityY = Rnd.nextFloat(-0.1f, -0.05f);
        velocity.set(velocityX, velocityY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        centerPosition.mulAdd(velocity, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }

        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }

        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }

        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }

        float starHeight = getHeight();
        starHeight += 0.0001f;
        if (starHeight >= 0.012f) {
            starHeight = 0.008f;
        }
        setHeightProportion(starHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(Rnd.nextFloat(0.005f, 0.013f));
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        centerPosition.set(x, y);
    }
}
