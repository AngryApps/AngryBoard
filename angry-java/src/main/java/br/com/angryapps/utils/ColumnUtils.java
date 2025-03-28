package br.com.angryapps.utils;

import br.com.angryapps.db.dto.ColumnDTO;

public class ColumnUtils {

    public static final double DEFAULT_POSITION = 100_000.0;

    public static double calculateNewPosition(ColumnDTO previousColumn, ColumnDTO nextColumn) {
        if (previousColumn == null && nextColumn == null) {
            return DEFAULT_POSITION;
        }

        if (previousColumn == null) {
            return nextColumn.getPosition() / 2.0;
        }

        if (nextColumn == null) {
            return previousColumn.getPosition() + DEFAULT_POSITION;
        }

        return (previousColumn.getPosition() + nextColumn.getPosition()) / 2.0;
    }
}
