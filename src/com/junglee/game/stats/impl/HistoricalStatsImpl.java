package com.junglee.game.stats.impl;

import com.junglee.game.stats.HistoricalStats;
import com.junglee.game.stats.Stats;

public class HistoricalStatsImpl extends StatsImpl implements HistoricalStats {

  @Override
  public String getFormattedStats(String prefix) {
    StringBuffer sb = new StringBuffer();
    sb.append(super.getFormattedStats(prefix));
    sb.append(String.format("%sgames played: %d\n", prefix, gamesPlayed));
    sb.append(String.format("%swins: %d\n", prefix, wins));
    return sb.toString();
  }

  private int gamesPlayed;

  @Override
  public void incGamesPlayed() {
    this.gamesPlayed++;
  }

  private int wins;

  @Override
  public void incWins() {
    this.wins++;
  }

  @Override
  public int getWins() {
    return this.wins;
  }

  @Override
  public int getGamesPlayed() {
    return this.gamesPlayed;
  }

  @Override
  public void update(Stats stats) {
    this.attemptedAttacks += stats.getAttemptedAttacks();
    this.damageDone += stats.getDamageDone();
    this.hits += stats.getHits();
    this.kills += stats.getKills();
    this.spellsCast += stats.getSpellsCast();
    this.spellDamageDone += stats.getSpellDamageDone();
    this.timePlayed += stats.getTimePlayed();

    this.attackedBy = stats.getAttackedBy();
    this.firstHitKills += stats.getFirstHitKills();
    this.assists += stats.getAssists();

    this.spellHits += stats.getSpellHits();
  }
}
