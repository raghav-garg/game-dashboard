package com.junglee.game.leaderboard.factory;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public class SharpShooterFactory implements AchievementFactory {

  /**
   * Accuracy level to get this achievement
   */
  public static final double SUCCESSRATE = 0.75;

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList();

    // if we have something to work with
    if (stats.getHits() > 0 && stats.getAttemptedAttacks() > 0) {
      float successRate = (float) stats.getHits() / (float) stats.getAttemptedAttacks();
      if (successRate >= SUCCESSRATE)
        result.add(new Sharpshooter());
    }

    return result;
  }


  public final class Sharpshooter extends Achievement {


    public Sharpshooter() {
      super("Sharpshooter");
    }
  }
}
