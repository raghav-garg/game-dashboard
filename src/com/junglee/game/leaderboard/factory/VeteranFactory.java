package com.junglee.game.leaderboard.factory;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public class VeteranFactory implements AchievementFactory {

  /**
   * Number of games played to get this award
   */
  public static final double GAMELIMIT = 1000;

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList();

    if (stats.getGamesPlayed() >= GAMELIMIT)
      result.add(new Veteran());

    return result;
  }


  public final class Veteran extends Achievement {

    public Veteran() {
      super("Veteran", true);
    }
  }
}
