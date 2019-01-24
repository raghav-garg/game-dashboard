package com.junglee.game.leaderboard.factory;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public class BigWinnerFactory implements AchievementFactory {

  /**
   * Number of wins in order to get this achievement
   */
  public static final int WINLIMIT = 200;

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList();

    if (stats.getWins() >= WINLIMIT)
      result.add(new BigWinner());

    return result;
  }

  public final class BigWinner extends Achievement {
    public BigWinner() {
      super("BigWinner", true);
    }
  }

}
