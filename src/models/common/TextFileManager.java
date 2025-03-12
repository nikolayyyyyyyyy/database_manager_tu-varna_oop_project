package models.common;
import interfaces.FileManage;
import interfaces.RowManager;
import models.core.Column;
import models.core.ColumnType;
import models.core.Row;
import models.core.Table;
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

            return new Table(fileName);
        }

        List<String> rows = Files.readAllLines(basePath.resolve(fileName));

        if (rows.isEmpty()) {

            return new Table(fileName);
        }
        Table table = new Table(fileName);

        if (rows.size() == 1) {

            String[] columnPair = rows.get(0).split(",");

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                table.addColumn(nameTypeOfColumn[0], ColumnType.valueOf(nameTypeOfColumn[1]));
            }
        } else {
            String[] columnPair = rows.get(0).split(",");

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                table.addColumn(nameTypeOfColumn[1], ColumnType.valueOf(nameTypeOfColumn[0]));
            }

            String[] records = rows
                    .stream()
                    .skip(1)
                    .toArray(String[]::new);

            for (String record :
                    records) {
                String[] values = record.split(" ");

                Row row = new Row();

                for (String value :
                        values) {
                    row.addValue(value);
                }

                table.getRows().add(row);
            }
        }

        return table;
    }

    @Override
    public void writeFile(Path baseDirectory,Table table) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Column columns :
                table.getColumns()) {
            sb.append(columns.getType()).append("-").append(columns.getName()).append(",");
        }

        if(sb.length() != 0) {
            Files.writeString(baseDirectory.resolve(table.getName()), sb.toString() + "\n", StandardOpenOption.APPEND);
        }

        for (RowManager row :
                table.getRows()) {
            Files.writeString(baseDirectory.resolve(table.getName()), String.join(" ", row.print()) + "\n", StandardOpenOption.APPEND);
        }
    }
}















