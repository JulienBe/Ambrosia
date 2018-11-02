package be.ambrosia.engine.g;

import com.badlogic.gdx.utils.NumberUtils;

public class GColor {
  private static final short MAX = 255, A = 24, R = 16, G = 8;
  public static float convertARGB(float a, float r, float g, float b) {
    return NumberUtils.intToFloatColor(((int)(MAX * a) << A) | ((int)(MAX * b) << R) | ((int)(MAX * g) << G) | ((int)(MAX * r)));
  }
}
