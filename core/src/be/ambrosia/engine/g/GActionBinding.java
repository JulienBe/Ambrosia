package be.ambrosia.engine.g;

public class GActionBinding {
  final int key;
  final GAction action;

  public GActionBinding(int key, GAction action) {
    this.key = key;
    this.action = action;
  }
}
