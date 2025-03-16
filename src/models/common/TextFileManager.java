package models.common;
import interfaces.Column;
import interfaces.FileManage;
import interfaces.Table;
import models.core.ColumnImpl;
import models.core.ColumnType;
import models.core.TableImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileManager implements FileManage {

    @Override
    public Table readFile(Path basePath, String fileName) throws IOException {
        Path filePath = basePath.resolve(fileName);
        if (!filePath.toFile().exists()) {

            Files.createFile(filePath);
            return new TableImpl(fileName);
        }
        Table table = new TableImpl(fileName);

        List<String> all = Files.readAllLines(filePath);
        if (all.isEmpty()) {
            return table;
        }

        String[] columnPairs = all.get(0).split(", ");
        for (int i = 0; i < columnPairs.length; i += 2) {
            String name = columnPairs[i].split(": ")[1].trim();
            String type = columnPairs[i + 1].split(": ")[1].trim();
            Column column = new ColumnImpl(name,ColumnType.valueOf(type));

            table.addColumn(column);
        }
        
        List<String> rows = all.stream().skip(1).collect(Collectors.toList());

        for (String row :
                rows) {
            String[] records = Arrays.stream(row.split(", "))
                    .map(r -> r.split(": "))
                    .map(sr -> sr[1])
                            .toArray(String[]::new);

            table.addRow(records);
        }

        return table;
    }

    @Override
    public void writeFile(Path baseDirectory, Table tableImpl) throws IOException {
        Path filePath = baseDirectory.resolve(tableImpl.getName());

        Files.writeString(filePath, tableImpl.toString(),
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}