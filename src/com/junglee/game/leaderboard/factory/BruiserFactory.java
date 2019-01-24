package com.junglee.game.leaderboard.factory;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public class BruiserFactory implements AchievementFactory {

  /**
   * Damage in one game necesarry to get this achievement
   */
  public static final int DAMAGELIMIT = 500;

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList<>();

    if (stats.getDamageDone() > DAMAGELIMIT)
      result.add(new Bruiser());

    return result;
  }


  public final class Bruiser extends Achievement {

    public Bruiser() {
      super("Bruiser");
    }
  }

}
