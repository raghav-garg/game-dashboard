package com.junglee.game.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.junglee.game.entity.enums.GameStatus;

public class Game extends Thread {


  private List<Team> teams = new ArrayList();
  private GameStatus status;
  private Random generator = new Random();
  private long startTime;
  private long finishTime;

  private Game() {
    setStatus(GameStatus.BEFORESTART);
  }

  public Game(Team a, Team b) {
    // we have to call this explicitly
    this();
    this.teams.add(a);
    this.teams.add(b);
  }


  public long getStartTime() {
    return this.startTime;
  }

  public long getFinishTime() {
    return this.finishTime;
  }

  public Team getRandomTeam() {
    return getTeams().get(generator.nextInt(getTeams().size()));
  }

  public Team getTeam(Player player) {
    return getTeams().get(0).getPlayers().contains(player) ? getTeams().get(0) : getTeams().get(1);
  }


  public Team getOtherTeam(Team team) {
    return teams.indexOf(team) == 1 ? teams.get(0) : teams.get(1);
  }

  private void setStatus(GameStatus status) {
    this.status = status;
  }


  public GameStatus getStatus() {
    return this.status;
  }


  public void addTeam(Team team) throws IllegalArgumentException {
    if (this.teams.size() > 1)
      throw new IllegalArgumentException("Cannot have more than 2 teams in a Game");

    this.teams.add(team);
  }


  public void removeTeam(Team team) throws IllegalArgumentException {
    if (this.teams.size() < 0)
      throw new IllegalArgumentException("There are no teams in this Game");

    this.teams.remove(team);
  }


  private void begin() {
    setStatus(GameStatus.STARTED);
    this.startTime = System.currentTimeMillis();

    // need to make sure everybody is alive
    for (Team t : this.teams)
      for (Player p : t.getPlayers())
        p.resetHitPoints();

    for (Team t : this.teams) {
      t.startGame(this);
      for (Player p : t.getPlayers())
        p.startGame(this);
    }
  }


  private void finish() {
    setStatus(GameStatus.OVER);
    this.finishTime = System.currentTimeMillis();

    for (Team t : this.teams) {
      t.finishGame();
      for (Player p : t.getPlayers())
        p.finishGame();
    }

  }


  @Override
  public void run() {

    synchronized (this) {
      begin();
      while (true) {

        // this is where we would wait on user events, such as attack or
        // cast spell
        try {
          this.wait();

        } catch (InterruptedException e) {
          // tbd
        }

        if (teams.get(0).getNumberOfAlivePlayers() == 0
            || teams.get(1).getNumberOfAlivePlayers() == 0)
          break;
      }
      finish();
    }
  }

  /**
   * Start the game
   */
  public void startGame() {
    if (this.teams.size() != 2)
      throw new IllegalArgumentException("You need 2 teams to start a Game");
    if (teams.get(0).getNumberOfPlayers() != teams.get(1).getNumberOfPlayers())
      throw new IllegalArgumentException("Number of players in  both teams is not equal");
    if (teams.get(0).getNumberOfPlayers() < 3)
      // 3 should be a defined constant - bad magic number!
      throw new IllegalArgumentException("Number of players in teams cannot be less than 3");

    this.start();
  }


  public List<Team> getTeams() {
    return this.teams;
  }


  public Team getWinner() {
    if (this.status != GameStatus.OVER)
      throw new IllegalStateException("Game is not finished");

    for (Team t : getTeams()) {
      if (t.getNumberOfAlivePlayers() > 0)
        return t;
    }

    return null;
  }


  public Team getLoser() {
    return this.teams.get(0) == getWinner() ? this.teams.get(1) : this.teams.get(0);
  }
}
