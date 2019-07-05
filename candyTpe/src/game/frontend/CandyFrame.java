package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.element.Element;

import game.backend.element.TimeBomb;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CandyFrame extends VBox {

	private static final int CELL_SIZE = 65;

	private BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private Level2ScorePanel movesPanel;
	private ScorePanel extraPanel;
	private ImageManager images;
	private Point2D lastPoint;
	private CandyGame game;

	public CandyFrame(CandyGame game) {

		this.game = game;
		getChildren().add(new AppMenu());
		images = new ImageManager();
		boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
		getChildren().add(boardPanel);
		scorePanel = new ScorePanel();
		getChildren().add(scorePanel);

		if(game.sameLevel(Level3.class)){
			extraPanel = new ScorePanel();
			getChildren().add(extraPanel);
		}

		if(game.sameLevel(Level2.class)) {
			movesPanel = new Level2ScorePanel();
			getChildren().add(movesPanel);
		}

		game.initGame();

		if(extraPanel!=null) {
			String initialExtraMessage = ((Long) game().getExtraInfo()).toString();
			extraPanel.updateScore(initialExtraMessage);
		}
		GameListener listener;
		game.addGameListener(listener = new GameListener() {

		@Override
		public void gridUpdated() {
			Timeline timeLine = new Timeline();
			Duration frameGap = Duration.millis(100);
			Duration frameTime = Duration.ZERO;
			for (int i = game().getSize() - 1; i >= 0; i--) {
				for (int j = game().getSize() - 1; j >= 0; j--) {
					int finalI = i;
					int finalJ = j;
					Cell cell = CandyFrame.this.game.get(i, j);
					Element element = cell.getContent();
					Image image = images.getImage(element);
					if (element instanceof TimeBomb) {
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null)));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image, ((TimeBomb) element).getCountdown())));
					}
					else {
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null, false )));
						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image, game().isGold(finalI, finalJ))));
					}
				}
				frameTime = frameTime.add(frameGap);
			}
			timeLine.play();
		}
		@Override
		public void cellExplosion(Element e) {
			//
		}
	});

		listener.gridUpdated();

		addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (lastPoint == null) {
				lastPoint = translateCoords(event.getX(), event.getY());
				System.out.println("Get first = " +  lastPoint);
			} else {
				Point2D newPoint = translateCoords(event.getX(), event.getY());
				if (newPoint != null) {
					System.out.println("Get second = " +  newPoint);
					game().tryMove((int)lastPoint.getX(), (int)lastPoint.getY(), (int)newPoint.getX(), (int)newPoint.getY());
					String message = ((Long)game().getScore()).toString();
					String message2 = ((Long)game().getExtraInfo()).toString();
					if (game().isFinished()) {
						if (game().playerWon()) {
							message = message + " Finished - Player Won!";
						} else {
							message = message + " Finished - Loser !";
						}
					}
					scorePanel.updateScore(message);
					if(extraPanel!=null)
						extraPanel.updateScore(message2);
					if(movesPanel != null) {
						movesPanel.updateScore(game.getMoves());
					}
					lastPoint = null;
				}
			}
		});

	}

	private CandyGame game() {
		return game;
	}

	private Point2D translateCoords(double x, double y) {
		double i = x / CELL_SIZE;
		double j = y / CELL_SIZE;
		return (i >= 0 && i < game.getSize() && j >= 0 && j < game.getSize()) ? new Point2D(j, i) : null;
	}

}
