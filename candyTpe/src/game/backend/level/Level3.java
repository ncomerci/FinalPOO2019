package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.Candy;
import game.backend.element.TimeBomb;
import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Level3 extends Grid {
    private final int MAX_BOMBS = 3;
    private final int LOWER_MOVES_BOUND = 4;
    private final int HIGHER_MOVES_BOUND = 15;

    protected GameState newState() {
        return new Level3State();
    }

    private List<TimeBomb> bombs = new ArrayList<>();

    @Override
    public void clearContent(int i, int j) {
        TimeBomb aux = null;
        if (g()[i][j].getContent() instanceof TimeBomb) {
            for (TimeBomb bomb : bombs) {
                if (bomb.equals(g()[i][j].getContent())) {
                    aux = bomb;
                }
            }
        }
        super.clearContent(i, j);
        if (aux != null) {
            bombs.remove(aux);
        }
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            state().addMove();
            System.out.println("Bombs amount is: " + bombs.size());
            decrementBombs();
        }
        return ret;
    }

    @Override
    public void initialize() {
        super.initialize(); // llena de contenido las celdas y ahora agrego las bombas
        generateBombs(MAX_BOMBS, LOWER_MOVES_BOUND, HIGHER_MOVES_BOUND);
        generateCells(bombs);

    }

    private void decrementBombs() {
        for (TimeBomb bomb : bombs) {
            bomb.decrementCoundown();
        }
    }

    private void generateBombs(int maxBombs, int lowerMovesBound, int higherMovesBound) {
        SecureRandom random = new SecureRandom();
        int bombsAmount = 1+random.nextInt(maxBombs);
        for (int i = 0; i < bombsAmount; i++) {
            bombs.add(new TimeBomb(lowerMovesBound, higherMovesBound));
        }
    }

    private void generateCells(List<TimeBomb> bombs) {
        SecureRandom random = new SecureRandom();
        for (TimeBomb bomb : bombs) {
            int auxX = random.nextInt(SIZE);
            int auxY = random.nextInt(SIZE);
            Point randomPoint = new Point(auxX, auxY);

            Candy aux = (Candy) g()[auxX][auxY].getContent();   // el casteo es seguro porque al llenar solamente hay candys
            bomb.setColor(aux.getColor());
            g()[auxX][auxY].setContent(bomb);

            System.out.println(String.format("Bomb color: %s is at %s, remaining moves until explosion: %s", bomb.getColor(), randomPoint, bomb.getCountdown())); // TESTING
        }
    }

    private class Level3State extends GameState {

        public Level3State() {
        }

        @Override
        public long getExtraInfo() {
            return getMinCountdown();
        }

        public boolean gameOver() {
            return playerWon() || getMinCountdown() <= 0;
        }

        public boolean playerWon() {
            return bombs.size() == 0;
        }

        private int getMinCountdown() {
            if (bombs.isEmpty()) {
                return 0;
            }
            int minCountdown = HIGHER_MOVES_BOUND + 1; // Como todas las timeBombs tienen como máximo HIGHER_MOVES_BOUND y bombs no está vacío lo va a pisar
            for (TimeBomb bomb : bombs) {
                if (bomb.getCountdown() < minCountdown) {
                    minCountdown = bomb.getCountdown();
                }
            }
            return minCountdown;
        }
    }

}

