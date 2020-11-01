package com.gradybward.colors;

public class Color {

  private final java.awt.Color color;

  private Color(java.awt.Color color) {
    this.color = color;
  }

  public static Color of(java.awt.Color color) {
    return new Color(color);
  }

  public java.awt.Color get() {
    return this.color;
  }
  
  public Color setOpacity(double opacity) {
    return new Color(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), (int) Math.floor(color.getAlpha() * opacity)));
  }
}
