package game.backend.level;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.GameState;
import game.backend.Grid;
import game.backend.element.*;

import java.awt.*;

public class Level2Aux extends Grid {

    private static final int CANT_CELLS = SIZE*SIZE;
    private boolean[][] gold_flags = new boolean[SIZE][SIZE];


    private class Level2auxState extends GameState {

        @Override
        public boolean gameOver() {
            return playerWon();
        }

        @Override
        public boolean playerWon() {
            return CANT_CELLS == getMoves();
        }
    }

    private void flagsUpdater(Figure figure, int i, int j) {
        int aux;
        Point p1;
        Point p2;

        if(figure != null) {
            for (aux = 0; aux < SIZE; aux++) {
                p1 = figure.getPoints()[0];
                p2 = figure.getPoints()[1];
                if (((p1.x == p2.x) && !gold_flags[i][aux]) || (figure.getReplacementClass() == WrappedCandy.class)) {
                    gold_flags[i][aux] = true;
                    state().addMove();
                }
                if ((p1.y == p2.y && !gold_flags[aux][j]) || (figure.getReplacementClass() == WrappedCandy.class)) {
                    gold_flags[aux][j] = true;
                    state().addMove();
                }
            }
        }
    }

    @Override
    protected boolean isGold(int i, int j) {
        return gold_flags[i][j];
    }

    @Override
    protected GameState newState() {
        return new Level2auxState();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        FigureDetector detector=new FigureDetector(this);

        Element e1=get(i1,j1);
        Element e2=get(i2,j2);
        if(specialCandy(e1) && specialCandy(e2)){
            int ind;
            for(ind=0;ind<SIZE;ind++){
                gold_flags[i2][ind]=true;
                gold_flags[ind][j2]=true;
            }
            return super.tryMove(i1,j1,i2,j2);
        }

        swapContent(i1,j1,i2,j2);
        Figure fig1=detector.checkFigure(i1, j1);
        Figure fig2=detector.checkFigure(i2, j2);
        swapContent(i1,j1,i2,j2);

        boolean ret = super.tryMove(i1, j1, i2, j2);
        if(ret) {
            flagsUpdater(fig1, i1, j1);
            flagsUpdater(fig2, i2, j2);
        }
        return ret;
    }

    public static int getCantCells() {
        return CANT_CELLS;
    }
    boolean specialCandy(Element e){
        return (e instanceof Bomb ||e instanceof WrappedCandy||e instanceof VerticalStripedCandy ||e instanceof HorizontalStripedCandy);
    }
}
