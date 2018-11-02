package be.ambrosia.engine.g;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

public class GActionList {
  private final Array<GActionBinding> justPressed = new Array<GActionBinding>();
  private final Array<GActionBinding> pressed = new Array<GActionBinding>();
  private final Array<GActionBinding> justReleased = new Array<GActionBinding>();
  private final Set<Integer> pressedBefore = new HashSet<Integer>();
  private boolean[] buttons = {false, false, false, false, false};

  public GActionList addPressed(int key, GAction action) {
    pressed.add(new GActionBinding(key, action));
    return this;
  }

  public GActionList addJustPressed(int key, GAction action) {
    justPressed.add(new GActionBinding(key, action));
    return this;
  }

  public GActionList addJustReleased(int key, GAction action) {
    justReleased.add(new GActionBinding(key, action));
    return this;
  }

  public void act() {
    for (GActionBinding b : justPressed) {
      if (isButton(b)) {
        if (Gdx.input.isButtonPressed(b.key)) {
          if (!buttons[b.key])
            b.action.act();
          buttons[b.key] = true;
        }
      }
      if (Gdx.input.isKeyJustPressed(b.key)) {
        b.action.act();
      }
    }
    for (GActionBinding b : pressed) {
      if (!isButton(b) && Gdx.input.isKeyPressed(b.key))
        b.action.act();
      if (isButton(b) && Gdx.input.isButtonPressed(b.key))
        b.action.act();
    }
    for (GActionBinding b : justReleased) {
      if (Gdx.input.isKeyJustPressed(b.key))
        pressedBefore.add(b.key);
      if (!Gdx.input.isKeyPressed(b.key) && pressedBefore.contains(b.key)) {
        pressedBefore.remove(b.key);
        b.action.act();
      }
    }
    for (int i = 0; i < buttons.length; i++) {
      if (!Gdx.input.isButtonPressed(i)) {
        buttons[i] = false;
      }
    }
  }

  private boolean isButton(GActionBinding b) {
    return b.key <= Input.Buttons.FORWARD && b.key >= 0;
  }
}
