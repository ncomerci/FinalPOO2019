package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;

public class Level2 extends Grid {

    private static final int CANT_CELLS = SIZE*SIZE;
    private boolean[][] gold_flags = new boolean[SIZE][SIZE];


    private class Level2State extends GameState {

        @Override
        public long getExtraInfo() {
            return 0;
        }

        @Override
        public boolean gameOver() {
            return playerWon();
        }

        @Override
        public boolean playerWon() {
            return CANT_CELLS == getMoves();
        }
    }

    @Override
    protected boolean isGold(int i, int j) {
        return gold_flags[i][j];
    }

    @Override
    protected GameState newState() {
        return new Level2State();
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret = super.tryMove(i1, j1, i2, j2);
        if(ret){
            int i;
                for(i=0;i<SIZE;i++){
                    if(i1==i2 && !gold_flags[i1][i]) {
                        gold_flags[i1][i] = true;
                        state().addMove();
                    }else if(j1==j2 && !gold_flags[i][j1]){
                        gold_flags[i][j1]=true;
                        state().addMove();
                    }

                }
        }
        return ret;
    }

    public static int getCantCells() {
        return CANT_CELLS;
    }

}
