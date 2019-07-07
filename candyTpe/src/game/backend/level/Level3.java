package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.*;

import java.awt.*;
import java.security.SecureRandom;
import java.security.interfaces.ECKey;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level3 extends Grid {
    private static final int MAX_BOMBS = 3;
    private static final int LOWER_MOVES_BOUND = 4;
    private static final int HIGHER_MOVES_BOUND = 15;

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
    public void cellExplosion(Element e) {
        TimeBomb aux;
        boolean i=true;
        Iterator<TimeBomb> it=bombs.iterator();
        if(e instanceof TimeBomb) {
            while (it.hasNext() && i) {
                aux=it.next();
                if (aux.equals(e)) {
                    it.remove();
                    i=false;
                }
            }
        }
        super.cellExplosion(e);
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        Candy aux= specialCase(i1,j1,i2,j2);
        if(aux!=null){
            CandyColor color=aux.getColor();
            Iterator<TimeBomb> it= bombs.iterator();
            while (it.hasNext()){
                if(it.next().getColor()==color)
                    it.remove();
            }
        }

        if (ret = super.tryMove(i1, j1, i2, j2)) {
            state().addMove();
            System.out.println("Bombs amount is: " + bombs.size());
            decrementBombs();
        }
        return ret;
    }

    private Candy specialCase(int i1, int j1, int i2, int j2){
        Element e1=get(i1,j1);
        Element e2=get(i2,j2);
        if (compare(e1,e2))
                return (Candy)e2;
        else if(compare(e2,e1))
            return (Candy) e1;
        else return null;
    }

    private boolean compare(Element e1,Element e2){
        return (e1 instanceof Bomb && (e2 instanceof HorizontalStripedCandy||e2 instanceof VerticalStripedCandy));
    }
    @Override
    public void initialize() {
        super.initialize();
        generateBombs();
        generateCells(bombs);
    }

    private void decrementBombs() {
        for (TimeBomb bomb : bombs) {
            bomb.decrementCoundown();
        }
    }

    private void generateBombs() {
        SecureRandom random = new SecureRandom();
        int bombsAmount = 1+random.nextInt(MAX_BOMBS);
        for (int i = 0; i < bombsAmount; i++) {
            bombs.add(new TimeBomb(LOWER_MOVES_BOUND, HIGHER_MOVES_BOUND));
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
        public long getCountdown() {
            return getMinCountdown();
        }

        @Override
        public boolean looser() {
            return winner() || getMinCountdown() <= 0;
        }

        @Override
        public boolean winner() {
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

