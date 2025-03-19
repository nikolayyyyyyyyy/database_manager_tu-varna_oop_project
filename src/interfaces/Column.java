package interfaces;
import models.enums.ColumnType;

/**
 * Интерфейс, който дефинира колоната в таблица.
 * Съдържа методи за достъп до името на колоната и нейния тип.
 */
public interface Column {

    /**
     * @return името на колоната
     */
    String getName();

    /**
     * @return типа на колоната
     */
    ColumnType getColumnType();
}
