package com.gradybward.colors.coolors;

import java.util.List;

import com.gradybward.colors.Color;
import com.gradybward.colors.ColorPallete;

public class CoolorsColorPallete implements ColorPallete {
  final int numberOfSaves;
  private final List<Color> colors;
  public CoolorsColorPallete(List<Color> colors, int numberOfSaves) {
    this.numberOfSaves = numberOfSaves;
    this.colors = colors;
  }

  @Override
  public Color[] get() {
    return colors.toArray(new Color[] {});
  }
}
