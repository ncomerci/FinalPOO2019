package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;

public class Level1 extends Grid {
	
	private static final int REQUIRED_SCORE = 5000;
	private static final int MAX_MOVES = 20;

	@Override
	protected GameState newState() {
		return new Level1State(REQUIRED_SCORE, MAX_MOVES);
	}
	
	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
		}
		return ret;
	}
	
	private class Level1State extends GameState {
		private long requiredScore;
		private long maxMoves;
		
		public Level1State(long requiredScore, int maxMoves) {
			this.requiredScore = requiredScore;
			this.maxMoves = maxMoves;
		}

		@Override
		public boolean looser() {
			return winner() || getMoves() >= maxMoves;
		}

		@Override
		public boolean winner() {
			return getScore() > requiredScore;
		}
	}

}
