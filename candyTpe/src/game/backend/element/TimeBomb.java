package game.backend.element;

import java.security.SecureRandom;

public class TimeBomb extends Candy {
    private int countdown;

    public TimeBomb(int lowerMovesBound, int higherMovesBound) {
        SecureRandom random = new SecureRandom();
        this.countdown = lowerMovesBound + random.nextInt(higherMovesBound - lowerMovesBound);
    }


    public int getCountdown() {
        return countdown;
    }

    public void decrementCoundown() {
        if (countdown > 0) {
            countdown--;
        }
    }
}

