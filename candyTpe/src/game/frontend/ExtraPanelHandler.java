package game.frontend;

import game.backend.level.Level2;

public class ExtraPanelHandler {
    
    private CandyFrame frame;
    private Class<?> current_level;
    private ScorePanel extraPanel = new ScorePanel();

    public ExtraPanelHandler(CandyFrame frame, Class<?> current_level) {
        this.frame = frame;
        this.current_level = current_level;
        updatePanel();
        frame.getChildren().add(extraPanel);
    }
    
    public void updatePanel() {
        String message;
        if(current_level == Level2.class) {
            message = String.format("Cells to win: %d", Level2.getCantCells() - frame.game().getMoves());
        }
        else {
            message = String.format("Time left: %d", frame.game().getExtraInfo());
        }
        extraPanel.updateScore(message);
    }
    
}
