package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;

public class Background extends Sprite {

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.centerPosition.set(worldBounds.centerPosition);
    }
}
