package com.junglee.game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.junglee.game.stats.impl.TotalStatsImpl;

public class Team extends TotalStatsImpl {

  private List<Player> players = new ArrayList();
  private String name = null;
  private Random generator = new Random();
  private Game game = null;


  public Team(String name) {
    this.name = name;
  }


  public String getName() {
    return this.name;
  }


  public void addPlayer(Player player) throws IllegalArgumentException {

    if (this.players.size() > 5)
      throw new IllegalArgumentException("You can't have more than 5 players in a Team");

    this.players.add(player);
  }


  public void removePlayer(Player player) throws IllegalArgumentException {

    // see addPlayer
    if (this.players.size() < 1)
      throw new IllegalArgumentException("You can't have a Team with nobody in it");

    this.players.remove(player);
  }


  public List<Player> getPlayers() {
    return this.players;
  }


  public int getNumberOfPlayers() {
    return getPlayers().size();
  }


  public int getNumberOfAlivePlayers() {
    int alivePlayers = 0;
    for (Player p : getPlayers()) {
      if (!p.isDead())
        alivePlayers++;
    }
    return alivePlayers;
  }


  public Player getRandomPlayer() {
    return players.get(generator.nextInt(players.size()));
  }


  public Player getRandomAlivePlayer() {
    if (this.game == null)
      return null;

    Player alivePlayer = null;
    synchronized (this.game) {
      if (this.game == null || getNumberOfAlivePlayers() == 0)
        return null;
      alivePlayer = getAlivePlayers().get(generator.nextInt(getNumberOfAlivePlayers()));
    }
    return alivePlayer;
  }


  public void startGame(Game game) {
    // reset the stats because its a new game
    resetStats();

    // and add our statistics as a delegate to each of our players
    // statistics
    for (Player p : getPlayers())
      p.addDelegate(getGameStats());

    this.game = game;
  }


  public Game getGame() {
    return this.game;
  }


  public void finishGame() {
    incGamesPlayed();

    if (this.equals(getGame().getWinner()))
      incWins();
    updateHistoricalStats();
  }


  public List<Player> getAlivePlayers() {
    if (this.game == null)
      throw new IllegalStateException("Team is not in an actual game");

    List<Player> alivePlayers = new ArrayList<Player>();
    for (Player p : getPlayers()) {
      if (!p.isDead())
        alivePlayers.add(p);
    }

    return alivePlayers;
  }


  public String getPlayerNames() {
    String names = "";
    for (Player p : getPlayers()) {
      names = names.concat(p.getName()).concat(",");
    }
    return names.substring(0, (names.length() > 0 ? names.length() - 1 : 0));
  }

  public String toLongString() {
    StringBuffer sb = new StringBuffer();
    sb.append(String.format("\tTeam: %s\n", getName()));
    sb.append(String.format("\tAlive Players: %d\n", this.getNumberOfAlivePlayers()));
    sb.append(getGameStats().getFormattedStats());
    sb.append("\n");
    return sb.toString();
  }

}
