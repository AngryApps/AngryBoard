package br.com.angryapps.utils;

import br.com.angryapps.db.dto.CardDTO;

public class CardUtils {

    public static final double DEFAULT_POSITION = 100_000.0;

    public static double calculateNewPosition(CardDTO previousCard, CardDTO nextCard) {
        if (previousCard == null && nextCard == null) {
            return DEFAULT_POSITION;
        }

        if (previousCard == null) {
            return nextCard.getPosition() / 2.0;
        }

        if (nextCard == null) {
            return previousCard.getPosition() + DEFAULT_POSITION;
        }

        return (previousCard.getPosition() + nextCard.getPosition()) / 2.0;
    }
}
