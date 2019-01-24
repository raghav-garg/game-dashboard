package com.junglee.game.stats;

public interface TotalStats extends Stats, HistoricalStats {

  public void resetStats();

  public void updateHistoricalStats();
}
