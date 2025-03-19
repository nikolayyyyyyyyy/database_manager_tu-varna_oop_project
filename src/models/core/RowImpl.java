package models.core;
import interfaces.Column;
import interfaces.Row;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Имплементация на интерфейса {@link Row}, която предоставя функционалности за
 * управление на атрибути на ред в таблица.
 * Всеки ред съдържа колони и стойности за тях.
 */
public class RowImpl implements Row {
    private final Map<Column,String> attributes;

    /**
     * Конструктор за създаване на нов ред с атрибути.
     * Всеки ред има уникални колони с асоциирани стойности.
     */
    public RowImpl(){
        this.attributes = new LinkedHashMap<>();
    }

    /**
     * Връща всички атрибути на реда, асоциирани с колоните.
     *
     * @return Карта с колони и стойности.
     */
    @Override
    public Map<Column, String> getAttributes() {
        return this.attributes;
    }

    /**
     * Връща текстово представяне на реда, като колоните и стойностите са отделени с ", ".
     *
     * @return Текстово представяне на реда в формат "колона: стойност".
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Column column :
                this.attributes.keySet()) {

            stringBuilder
                    .append(column.getName())
                    .append(": ")
                    .append(this.attributes.get(column))
                    .append(", ");
        }
        return stringBuilder.toString().trim().replaceAll(",$", "");
    }
}
