package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.alexander_kramarenko.base.Sprite;
import ru.alexander_kramarenko.math.Rect;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;

    private Vector2 mouseClickTarget;  // touch
    private Vector2 velocityToTarget;  // v
    private Vector2 mouseClickTargetTemp;  // tmp

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        mouseClickTarget = new Vector2();   // touch
        velocityToTarget = new Vector2();  // v
        mouseClickTargetTemp = new Vector2();  // tmp

            }

    @Override
    public void update(float delta) {
        super.update(delta);
        mouseClickTargetTemp.set(mouseClickTarget);
        if(mouseClickTargetTemp.sub(centerPosition).len() <= V_LEN){
               centerPosition.set(mouseClickTarget);
        }
           else {

               centerPosition.add(velocityToTarget);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 mouseClickTarget, int pointer, int button) {
        this.mouseClickTarget.set(mouseClickTarget);
        velocityToTarget.set(mouseClickTarget.cpy().sub(centerPosition)).setLength(V_LEN);
        return false;
    }
}
