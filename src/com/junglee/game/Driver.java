package com.junglee.game;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.junglee.game.entity.Game;
import com.junglee.game.entity.Player;
import com.junglee.game.entity.Team;
import com.junglee.game.entity.enums.GameStatus;
import com.junglee.game.leaderboard.Achievement;
import com.junglee.game.leaderboard.factory.AchievementFactory;
import com.junglee.game.leaderboard.factory.impl.AchievementFactoryImpl;

public class Driver {

  Team team1, team2 = null;
  Set<Team> teams = new HashSet<Team>();


  public Driver(int totalPlayers) {


    if (totalPlayers % 2 != 0) {
      System.out.println(
          "Number of players should be even so that can be distributed in two teams equally");
      return;
    }
    team1 = new Team("team1");
    team2 = new Team("team2");
    int numPlayers = totalPlayers / 2;

    for (int i = 0; i < numPlayers; i++) {
      team1.addPlayer(new Player(String.valueOf(i)));
    }

    for (int i = numPlayers; i < totalPlayers; i++) {
      team2.addPlayer(new Player(String.valueOf(i)));
    }

    teams.add(team1);
    teams.add(team2);

  }


  private void play() throws InterruptedException {

    Game game = new Game(team1, team2);
    game.startGame();

    while (!game.getStatus().equals(GameStatus.STARTED)) {
      Thread.sleep(1000);
    }

    while (!game.getStatus().equals(GameStatus.OVER)) {

      Team team = game.getRandomTeam();
      Player attacker = team.getRandomAlivePlayer();


      Team other = game.getOtherTeam(team);
      Player opponent = other.getRandomAlivePlayer();

      if (attacker != null && opponent != null) {

        Random generator = new Random();
        if (generator.nextInt(2) == 0) {
          attacker.castSpell(opponent);
        } else {
          attacker.attack(opponent);
        }
      }
    }


    System.out.println("And the winner is :" + game.getWinner().getName());
    System.out.println("Number of players alive:" + game.getWinner().getNumberOfAlivePlayers());


    for (Team t : game.getTeams()) {
      System.out.printf("Team Name :" + t.getName());
      for (Player p : t.getPlayers())
        System.out.println(p.toLongString());
    }
  }

  private void getAchievements() {
    // time to hand out awards
    AchievementFactory factory = AchievementFactoryImpl.getFactory();

    for (Team t : teams)
      for (Player p : t.getPlayers()) {
        List<Achievement> awards = factory.getAchievements(p);
        for (Achievement a : awards) {
          p.addAchievement(a);
          System.out.println("Player " + p.getName() + " got achievement :" + a.getName());
        }
      }
  }


  public static void main(String[] args) throws InterruptedException {

    Driver driver = new Driver(8);
    for (int i = 0; i < 100; i++) {
      driver.play();
      driver.getAchievements();
    }
  }

}
