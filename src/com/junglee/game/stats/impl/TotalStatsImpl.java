package com.junglee.game.stats.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.junglee.game.entity.Player;
import com.junglee.game.stats.HistoricalStats;
import com.junglee.game.stats.Stats;
import com.junglee.game.stats.TotalStats;

public class TotalStatsImpl implements TotalStats {

  private Stats stats = new StatsImpl();
  private HistoricalStats hStats = new HistoricalStatsImpl();
  private Set<TotalStats> delegates = new HashSet<>();

  @Override
  public void resetStats() {
    stats = new StatsImpl();
  }


  public void addDelegate(TotalStats delegate) {
    this.delegates.add(delegate);
  }

  public void removeDelegate(TotalStats delegate) {
    this.delegates.remove(delegate);
  }

  @Override
  public String getFormattedStats() {
    return getFormattedStats("\t");
  }

  @Override
  public void incAttemptedAttacks() {
    stats.incAttemptedAttacks();
    for (Stats s : this.delegates)
      s.incAttemptedAttacks();
  }

  @Override
  public void incDamageDone(int damage) {
    if (damage > 0) {
      stats.incHits();
      for (Stats s : this.delegates)
        s.incHits();
    }

    stats.incDamageDone(damage);
    for (Stats s : this.delegates)
      s.incDamageDone(damage);
  }

  @Override
  public void incHits() {
    stats.incHits();
    for (Stats s : this.delegates)
      s.incHits();
  }

  @Override
  public void incKills() {
    stats.incKills();
    for (Stats s : this.delegates)
      s.incKills();
  }

  public void incKills(Player player) {
    if (player.isDead())
      incKills();
  }

  @Override
  public void incSpellDamageDone(int damage) {
    stats.incSpellDamageDone(damage);
    for (Stats s : this.delegates)
      s.incSpellDamageDone(damage);

    // new - we could also have taken a factory approach like achievements
    if (damage > 0)
      incSpellHits();
  }

  @Override
  public void incSpellsCast() {
    stats.incSpellsCast();
    for (Stats s : this.delegates)
      s.incSpellsCast();
  }

  @Override
  public void setTimePlayed(long time) {
    stats.setTimePlayed(time);
  }


  public void setTimePlayed(Player player) {
    long timePlayed = System.currentTimeMillis() - player.getGame().getStartTime();
    if (player.isDead())
      setTimePlayed(timePlayed);
    for (Stats s : this.delegates)
      s.setTimePlayed(timePlayed);
  }


  public TotalStats getGameStats() {
    return this;
  }

  @Override
  public void setDead(boolean dead) {
    stats.setDead(dead);
    if (dead) {
      if (stats.getAttackedBy().size() == 1) {
        stats.getAttackedBy().iterator().next().incFirstHitKills();

      } else {
        for (Player p : stats.getAttackedBy())
          p.incAssists();
      }
    }
  }

  @Override
  public void addAttackedBy(Player player) {
    stats.addAttackedBy(player);
    for (Stats s : this.delegates)
      s.addAttackedBy(player);
  }

  @Override
  public void incFirstHitKills() {
    stats.incFirstHitKills();
    for (Stats s : this.delegates)
      s.incFirstHitKills();
  }

  @Override
  public void incAssists() {
    stats.incAssists();
    for (Stats s : this.delegates)
      s.incAssists();
  }

  @Override
  public List<Player> getAttackedBy() {
    return stats.getAttackedBy();
  }

  @Override
  public void incGamesPlayed() {
    hStats.incGamesPlayed();
  }

  @Override
  public void incWins() {
    hStats.incWins();
  }

  @Override
  public void update(Stats stats) {
    hStats.update(stats);
  }

  @Override
  public int getHits() {
    return stats.getHits();
  }

  @Override
  public int getAttemptedAttacks() {
    return stats.getAttemptedAttacks();
  }

  /**
   * Return the historical Stats
   * 
   * @return historical Stats
   */
  public HistoricalStats getHistoricalStat() {
    return hStats;
  }

  @Override
  public void updateHistoricalStats() {
    hStats.update(stats);
  }

  @Override
  public int getAssists() {
    return stats.getAssists();
  }

  @Override
  public int getDamageDone() {
    return stats.getDamageDone();
  }

  @Override
  public int getFirstHitKills() {
    return stats.getFirstHitKills();
  }

  @Override
  public int getKills() {
    return stats.getKills();
  }

  @Override
  public int getSpellDamageDone() {
    return stats.getSpellDamageDone();
  }

  @Override
  public int getSpellsCast() {
    return stats.getSpellsCast();
  }

  @Override
  public long getTimePlayed() {
    return stats.getTimePlayed();
  }

  @Override
  public boolean isDead() {
    return stats.isDead();
  }

  @Override
  public int getWins() {
    return hStats.getWins();
  }

  @Override
  public int getGamesPlayed() {
    return hStats.getGamesPlayed();
  }

  @Override
  public int getSpellHits() {
    return stats.getSpellHits();
  }

  @Override
  public void incSpellHits() {
    stats.incSpellHits();
    for (Stats s : this.delegates)
      s.incSpellHits();
  }

  @Override
  public String getFormattedStats(String prefix) {
    StringBuffer sb = new StringBuffer();
    sb.append(String.format("%s--- Game Stats ---\n", prefix));
    sb.append(stats.getFormattedStats(prefix));
    sb.append(String.format("%s--- Historical Stats ---\n", prefix));
    sb.append(hStats.getFormattedStats(prefix));
    return sb.toString();
  }

}
