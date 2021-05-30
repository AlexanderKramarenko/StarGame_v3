package ru.alexander_kramarenko;

import com.badlogic.gdx.Game;

import ru.alexander_kramarenko.screen.MenuScreen;

public class StarGame_v3 extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
