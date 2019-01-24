package com.junglee.game.stats;

public interface HistoricalStats extends Stats {

  public void incWins();

  public int getWins();

  public void incGamesPlayed();

  public int getGamesPlayed();

  @Override
  public String getFormattedStats(String prefix);

  public void update(Stats stats);
}
