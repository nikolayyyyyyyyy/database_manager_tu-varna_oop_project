package models.common;
import interfaces.Column;
import interfaces.FileManage;
import interfaces.Row;
import interfaces.Table;
import models.core.ColumnImpl;
import models.core.ColumnType;
import models.core.TableImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileManager implements FileManage {

    @Override
    public Table readFile(Path basePath, String fileName) throws IOException {
        Path filePath = basePath.resolve(fileName);

        if (!BaseFileValidator.isFileExist(fileName)) {
            Files.createFile(filePath);
            return new TableImpl(fileName);
        }

        List<String> rows = Files.readAllLines(filePath);
        Table table = new TableImpl(fileName);

        if (rows.isEmpty()) {
            return table;
        }

        String[] columnPairs = rows.get(0).split(", ");

        Arrays.stream(columnPairs)
                .map(pair -> pair.split(": "))
                .map(parts -> new ColumnImpl(parts[0], ColumnType.valueOf(parts[1])))
                .forEach(table::addColumn);

        rows.stream()
                .skip(1)
                .map(record -> record.split(" "))
                .forEach(table::addRow);

        return table;
    }

    @Override
    public void writeFile(Path baseDirectory, Table tableImpl) throws IOException {
        Path filePath = baseDirectory.resolve(tableImpl.getName());

        String header = tableImpl.getColumns().stream()
                .map(column -> column.getName() + ": " + column.getColumnType())
                .collect(Collectors.joining(", "));

        List<String> lines = new ArrayList<>();

        lines.add(header);
        tableImpl.getRows().stream()
                .map(Row::print)
                .forEach(lines::add);
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}















