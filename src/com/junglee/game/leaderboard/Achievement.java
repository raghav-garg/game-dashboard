package com.junglee.game.leaderboard;

public abstract class Achievement {

  private String name;
  private boolean onceOnly;


  public Achievement(String name, boolean onceOnly) {
    this(name);
    this.onceOnly = onceOnly;
  }


  public Achievement(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    return o.getClass().equals(this.getClass())
        && this.getName().equals(((Achievement) o).getName());
  }

  @Override
  public int hashCode() {
    return this.getName().hashCode();
  }

  public boolean isOnceOnly() {
    return this.onceOnly;
  }

  public String getName() {
    return name;
  }

}
