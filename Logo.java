package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;

public class Logo extends Sprite {

    public Logo(Texture texture) {
        super(new TextureRegion(texture));

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.4f);
        System.out.println("resize logo");
        this.centerPosition.set(worldBounds.centerPosition);
    }
}


