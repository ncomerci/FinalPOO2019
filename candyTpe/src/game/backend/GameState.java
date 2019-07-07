package game.backend;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.nio.file.Paths;

public abstract class GameState {

	private final String winnerSoundPath = "candyTpe/resources/audio/winner sound.mp3";
	private final String loserSoundPath = "candyTpe/resources/audio/failure sound.mp3";
	private final Media winner_sound = new Media(Paths.get(winnerSoundPath).toUri().toString());
	private final Media loser_sound = new Media(Paths.get(loserSoundPath).toUri().toString());
	protected final AudioClip winnerPlayer = new AudioClip(winner_sound.getSource());
	protected final AudioClip loserPlayer = new AudioClip(loser_sound.getSource());
	
	private long score = 0;
	private int moves = 0;
	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}

	public abstract long getExtraInfo();

	public abstract boolean winner();

	public abstract boolean looser();

	public boolean gameOver() {
		if(looser() && !winner()) { loserPlayer.play(); }
		return looser();
	}

	public boolean playerWon() {
		if(winner()) { winnerPlayer.setVolume(0.3); winnerPlayer.play(); }
		return winner();
	}

}
