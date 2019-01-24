package com.junglee.game.leaderboard.factory;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public class WizardFactory implements AchievementFactory {

  /**
   * Number of spells cast before this achievement is created
   */
  public static final int SPELLLIMIT = 50;

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList();

    // if we have something to work with
    if (stats.getSpellHits() >= SPELLLIMIT)
      result.add(new Wizard());

    return result;
  }


  public final class Wizard extends Achievement {

    public Wizard() {
      super("Wizard");
    }
  }

}
