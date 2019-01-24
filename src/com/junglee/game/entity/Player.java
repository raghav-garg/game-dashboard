package com.junglee.game.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.stats.TotalStats;
import com.junglee.game.stats.impl.TotalStatsImpl;

public class Player extends TotalStatsImpl implements TotalStats {

  private static final int HITPOINTS = 1000;


  int hitPoints = HITPOINTS;
  String name = null;
  private Random generator = new Random();
  private Game game = null;
  private Map<Achievement, Integer> achievements = new HashMap<>();

  public Map<Achievement, Integer> getAchievements() {
    return this.achievements;
  }

  public void addAchievement(Achievement achievement) {
    if (this.achievements.containsKey(achievement)) {
      if (!achievement.isOnceOnly())
        this.achievements.put(achievement, this.achievements.get(achievement) + 1);
    } else {
      this.achievements.put(achievement, 1);
    }

  }

  public String toLongString() {
    String prefix = "\t\t";
    StringBuffer sb = new StringBuffer("\n");
    sb.append(String.format("%sPlayer: %s\n", prefix, getName()));
    sb.append(String.format("%sHitpoints: %d\n", prefix, getHitPoints()));
    sb.append(String.format("%sAchievements: %s\n", prefix, getAchievements()));
    sb.append(getFormattedStats(prefix));
    sb.append("\n");
    // should be using BufferedString or something ?
    return sb.toString();
  }

  public Player(String name) {
    this.name = name;
  }


  public int getHitPoints() {
    return this.hitPoints;
  }


  public void resetHitPoints() {
    this.hitPoints = HITPOINTS;
  }


  @Override
  public boolean isDead() {
    return this.hitPoints <= 0;
  }


  @Override
  public String toString() {
    return getName();
  }

  public String getName() {
    return this.name;
  }


  public void startGame(Game game) {
    // we reset the game stats at the start of each game
    resetStats();
    this.game = game;
  }


  public void finishGame() {
    // this.game = null;
    setTimePlayed(this.game.getFinishTime() - this.game.getStartTime());

    updateHistoricalStats();
    incGamesPlayed();
    if (getGame().getTeam(this).equals(getGame().getWinner()))
      incWins();

  }

  public Game getGame() {
    return this.game;
  }

  private int damagedBy(Player player) {
    if (this.game == null)
      throw new IllegalStateException("Player is not in an actual game");

    if (isDead())
      throw new IllegalStateException("Player is already dead");

    int damage = 0;

    // we're synchronized on the game object itself
    synchronized (this.game) {

      // we'll call it 50% change of hitting
      boolean success = generator.nextInt(2) == 0 ? false : true;
      if (success)
        damage = generator.nextInt(1200);
      if (damage > getHitPoints())
        damage = getHitPoints();
      this.hitPoints -= damage;

      this.game.notifyAll(); // more than 1 ? Probably not
    }

    return damage;
  }

  // I attacked you!
  public synchronized int attack(Player player) {

    if (isDead())
      throw new IllegalStateException("Player is dead and cannot attack");

    // and attack
    int damage = player.attackedBy(this);

    // stats
    incAttemptedAttacks();
    incDamageDone(damage);
    incKills(player);

    return damage;
  }

  // You attacked me!
  protected int attackedBy(Player player) {
    int damage = damagedBy(player);
    addAttackedBy(player);
    System.out.printf("Player %s attacked player %s for %d damage\n", player, this, damage);
    if (isDead()) {
      setDead(true);
      setTimePlayed(this);
      System.out.printf("Player %s has killed Player %s!\n", player, this);
    }
    return damage;
  }

  // I cast a spell on you!
  public synchronized int castSpell(Player player) {

    if (isDead())
      throw new IllegalStateException("Player is dead and cannot cast spell");

    incSpellsCast();

    int damage = player.spellCastBy(this);

    incSpellDamageDone(damage);
    incKills(player);

    return damage;
  }

  protected synchronized int spellCastBy(Player player) {
    int damage = damagedBy(player);

    System.out.printf("Player %s cast spell on player %s for %d damage\n", player, this, damage);
    addAttackedBy(player);

    if (isDead()) {

      setDead(true);
      setTimePlayed(this);

      System.out.printf("Player %s has killed Player %s!\n", player, this);
    }

    return damage;
  }
}
