package com.junglee.game.stats;

import java.util.List;
import com.junglee.game.entity.Player;

public interface Stats {

  public void incKills();

  public int getAttemptedAttacks();

  public void incSpellsCast();

  public String getFormattedStats();

  public void incHits();

  public int getKills();

  public void incAttemptedAttacks();

  public int getHits();

  public void incDamageDone(int damage);

  public int getDamageDone();

  public int getAssists();

  public void incSpellHits();

  public int getSpellHits();

  public String getFormattedStats(String prefix);

  public int getSpellsCast();

  public void incFirstHitKills();

  public int getFirstHitKills();

  public void incSpellDamageDone(int damage);

  public int getSpellDamageDone();

  public void setDead(boolean dead);

  public boolean isDead();

  public void setTimePlayed(long time);

  public long getTimePlayed();

  public void addAttackedBy(Player player);

  public List<Player> getAttackedBy();

  public void incAssists();

}
