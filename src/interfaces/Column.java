package interfaces;

import models.core.ColumnType;

public interface Column {

    public String print();
    public String getName();
    public ColumnType getColumnType();
}
