package models.common;
import interfaces.Column;
import interfaces.FileManage;
import interfaces.Row;
import interfaces.Table;
import models.core.ColumnType;
import models.core.TableImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TextFileManager implements FileManage {

    @Override
    public Table readFile(Path basePath, String fileName) throws IOException {
        if (!BaseFileValidator.isFileExist(fileName)) {

            Files.createFile(basePath.resolve(fileName));

            return new TableImpl(fileName);
        }

        List<String> rows = Files.readAllLines(basePath.resolve(fileName));

        if (rows.isEmpty()) {

            return new TableImpl(fileName);
        }
        Table tableImpl = new TableImpl(fileName);

        String[] columnPair = rows.get(0).split(",");
        if (rows.size() == 1) {

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                tableImpl.addColumn(ColumnType.valueOf(nameTypeOfColumn[0]),nameTypeOfColumn[1]);
            }
        } else {

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                tableImpl.addColumn(ColumnType.valueOf(nameTypeOfColumn[0]), nameTypeOfColumn[1]);
            }

            String[] records = rows
                    .stream()
                    .skip(1)
                    .toArray(String[]::new);

            for (String record :
                    records) {
                String[] values = record.split(" ");

                tableImpl.addRow(values);
            }
        }
        return tableImpl;
    }

    @Override
    public void writeFile(Path baseDirectory, Table tableImpl) throws IOException {
        if(!tableImpl.getColumns().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Column column :
                    tableImpl.getColumns()) {

                sb.append(column.getColumnType()).append("-").append(column.getName()).append(",");
            }

            Files.writeString(baseDirectory.resolve(tableImpl.getName()), sb.toString() + "\n", StandardOpenOption.APPEND);


            for (Row row :
                    tableImpl.getRows()) {
                Files.writeString(baseDirectory.resolve(tableImpl.getName()), String.join(" ", row.print()) + "\n", StandardOpenOption.APPEND);
            }
        }
    }
}















