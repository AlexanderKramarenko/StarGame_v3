package ru.alexander_kramarenko.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.alexander_kramarenko.base.ScaledButton;
import ru.alexander_kramarenko.math.Rect;
import ru.alexander_kramarenko.screen.GameScreen;

public class NewGame extends ScaledButton {

    private GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {

        super(atlas.findRegion("button_new_game"));

        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(0.05f);
        setBottom(-0.2f);
    }

    @Override
    protected void action() {
        gameScreen.startNewGame();
    }
}
