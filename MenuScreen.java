package ru.alexander_kramarenko.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.alexander_kramarenko.base.BaseScreen;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.sprite.Background;
import ru.alexander_kramarenko.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Texture starShipImg1;
    private Logo logo;
    private Vector2 mouseClickTargetTemp;

    @Override
    public void show() {
        super.show();
        starShipImg1 = new Texture("logos/plane.png");
        logo = new Logo(starShipImg1);
        bg = new Texture("textures/texture-2048x2048.png");
        background = new Background(bg);
        mouseClickTargetTemp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
        //    currentPosition = logo.centerPosition.add(0.002f, 0.002f);
        currentPosition = logo.centerPosition;
        mouseClickTargetTemp.set(mouseClickTarget);
        if (mouseClickTargetTemp.sub(currentPosition).len() <= velocityToTarget.len()) {
            System.out.println("if (render)");
            currentPosition.set(mouseClickTarget);
            velocityToTarget.setZero();
        } else {
            System.out.println("else (render)");
            logo.centerPosition.add(velocityToTarget);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        starShipImg1.dispose();
    }
}
