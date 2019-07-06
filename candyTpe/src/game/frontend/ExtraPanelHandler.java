package game.frontend;

import game.backend.level.Level2;
import game.backend.level.Level3;

public class ExtraPanelHandler {
    
    private CandyFrame frame;
    private Class<?> current_level;
    private ScorePanel extraPanel = new ScorePanel();

    public ExtraPanelHandler(CandyFrame frame) {
        this.frame = frame;
        this.current_level = frame.game().getLevelClass();
        updatePanel();
        frame.getChildren().add(extraPanel);
    }
    
    public void updatePanel() {
        String message;
        if(current_level == Level3.class) {
            message = String.format("Time left: %d", frame.game().getExtraInfo());
        }
        else {
            message = String.format("Cells to win: %d", Level2.getCantCells() - frame.game().getMoves());
        }
        extraPanel.updateScore(message);
    }
    
}
