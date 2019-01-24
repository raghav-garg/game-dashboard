package com.junglee.game.stats.impl;

import java.util.ArrayList;
import java.util.List;
import com.junglee.game.entity.Player;
import com.junglee.game.stats.Stats;

public class StatsImpl implements Stats {


  protected int attemptedAttacks;

  @Override
  public void incAttemptedAttacks() {
    attemptedAttacks++;
  }

  @Override
  public int getAttemptedAttacks() {
    return attemptedAttacks;
  }

  protected int hits;

  @Override
  public void incHits() {
    hits++;
  }

  protected int damageDone;

  @Override
  public void incDamageDone(int damage) {
    damageDone += damage;
  }

  protected int kills = 0;

  @Override
  public void incKills() {
    kills++;
  }

  protected boolean dead = false;

  @Override
  public void setDead(boolean dead) {
    this.dead = true;
  }

  protected int firstHitKills;

  protected int spellsCast;

  @Override
  public void incSpellsCast() {
    spellsCast++;
  }

  protected int spellDamageDone;

  @Override
  public void incSpellDamageDone(int damage) {
    damageDone += damage;
    spellDamageDone += damage;
  }

  protected long timePlayed;

  @Override
  public void setTimePlayed(long time) {
    if (timePlayed == 0)
      timePlayed = time;
  }

  protected List<Player> attackedBy = new ArrayList();

  @Override
  public void addAttackedBy(Player player) {
    attackedBy.add(player);
  }

  @Override
  public List<Player> getAttackedBy() {
    return attackedBy;
  }

  @Override
  public void incFirstHitKills() {
    firstHitKills++;

  }

  protected int assists;

  @Override
  public void incAssists() {
    assists++;
  }

  @Override
  public int getHits() {
    return hits;
  }

  @Override
  public int getAssists() {
    return assists;
  }

  @Override
  public int getDamageDone() {
    return damageDone;
  }

  @Override
  public int getFirstHitKills() {
    return firstHitKills;
  }

  @Override
  public int getKills() {
    return kills;
  }


  @Override
  public int getSpellDamageDone() {
    return spellDamageDone;
  }

  @Override
  public int getSpellsCast() {
    return spellsCast;
  }

  @Override
  public long getTimePlayed() {
    return timePlayed;
  }

  @Override
  public boolean isDead() {
    return dead;
  }

  // hits
  protected int spellHits;

  @Override
  public int getSpellHits() {
    return spellHits;
  }

  @Override
  public void incSpellHits() {
    spellHits++;
  }

  @Override
  public String getFormattedStats(String prefix) {
    StringBuffer sb = new StringBuffer();
    sb.append(String.format("%sattempted Attacks: %d\n", prefix, attemptedAttacks));
    sb.append(String.format("%shits: %d\n", prefix, hits));
    sb.append(String.format("%sdamage Done: %d\n", prefix, damageDone));
    sb.append(String.format("%skills: %d\n", prefix, kills));
    sb.append(String.format("%sspells cast: %d\n", prefix, spellsCast));
    sb.append(String.format("%sspell damage done: %d\n", prefix, spellDamageDone));
    sb.append(String.format("%stime played: %s\n", prefix, timePlayed));
    sb.append(String.format("%sattacked by: %s\n", prefix, attackedBy));
    sb.append(String.format("%sfirst hit kills: %s\n", prefix, firstHitKills));
    sb.append(String.format("%sassists: %s\n", prefix, assists));

    sb.append(String.format("%sspell hits: %s\n", prefix, spellHits));

    return sb.toString();
  }

  @Override
  public String getFormattedStats() {
    return getFormattedStats("");
  }


}
