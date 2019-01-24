package com.junglee.game.leaderboard.factory;

import java.util.List;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;

public interface AchievementFactory {
  public List<Achievement> getAchievements(TotalStats stats);
}
