package models.common;
import interfaces.ColumnManager;
import interfaces.FileManage;
import interfaces.RowManager;
import models.core.ColumnType;
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

        String[] columnPair = rows.get(0).split(",");
        if (rows.size() == 1) {

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                table.addColumn(ColumnType.valueOf(nameTypeOfColumn[0]),nameTypeOfColumn[1]);
            }
        } else {

            for (String pair :
                    columnPair) {
                String[] nameTypeOfColumn = pair.split("-");
                table.addColumn(ColumnType.valueOf(nameTypeOfColumn[0]), nameTypeOfColumn[1]);
            }

            String[] records = rows
                    .stream()
                    .skip(1)
                    .toArray(String[]::new);

            for (String record :
                    records) {
                String[] values = record.split(" ");

                table.addRow(values);
            }
        }
        return table;
    }

    @Override
    public void writeFile(Path baseDirectory,Table table) throws IOException {
        if (!BaseFileValidator.isFileExist(table.getName())) {

            Files.createFile(baseDirectory.resolve(table.getName()));
        }

        if(!table.getColumns().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ColumnManager column :
                    table.getColumns()) {

                sb.append(column.getColumnType()).append("-").append(column.getName()).append(",");
            }

            Files.writeString(baseDirectory.resolve(table.getName()), sb.toString() + "\n", StandardOpenOption.APPEND);


            for (RowManager row :
                    table.getRows()) {
                Files.writeString(baseDirectory.resolve(table.getName()), String.join(" ", row.print()) + "\n", StandardOpenOption.APPEND);
            }
        }
    }
}















