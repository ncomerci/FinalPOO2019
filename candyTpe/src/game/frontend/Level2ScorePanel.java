package game.frontend;

import game.backend.level.Level2;

public class Level2ScorePanel extends ScorePanel {

    public Level2ScorePanel() {
        super();
        scoreLabel.setText(String.format("Cells to win: %d", Level2.getCantCells()));
    }

    public void updateScore(int goldenCells) {
        scoreLabel.setText(String.format("Cells to win: %d", Level2.getCantCells() - goldenCells));
    }
}
