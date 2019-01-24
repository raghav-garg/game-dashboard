package com.junglee.game.leaderboard.factory.impl;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.leaderboard.factory.AchievementFactory;
import com.junglee.game.leaderboard.factory.BigWinnerFactory;
import com.junglee.game.leaderboard.factory.BruiserFactory;
import com.junglee.game.leaderboard.factory.SharpShooterFactory;
import com.junglee.game.leaderboard.factory.VeteranFactory;
import com.junglee.game.leaderboard.factory.WizardFactory;
import com.junglee.game.stats.TotalStats;

public class AchievementFactoryImpl implements AchievementFactory {

  private List<AchievementFactory> factories = new ArrayList<>();
  private static AchievementFactory singletonFactory = null;

  /**
   * Singleton - returns only available instance
   * 
   * @return singleton factory
   */
  public static AchievementFactory getFactory() {
    if (singletonFactory == null) {
      singletonFactory = new AchievementFactoryImpl();
    }
    return singletonFactory;
  }

  private AchievementFactoryImpl() {
    addFactory(new SharpShooterFactory());
    addFactory(new BruiserFactory());
    addFactory(new VeteranFactory());
    addFactory(new BigWinnerFactory());
    addFactory(new WizardFactory());
  }

  @Override
  public List<Achievement> getAchievements(TotalStats stats) {
    List<Achievement> result = new ArrayList<Achievement>();
    for (AchievementFactory f : this.factories)
      result.addAll(f.getAchievements(stats));
    return result;
  }


  public void addFactory(AchievementFactory factory) {
    this.factories.add(factory);
  }

  public void removeFactory(Achievement factory) {
    this.factories.remove(factory);
  }

}
