package interfaces;
import java.util.Map;

public interface Database {

    Map<String,Table> getLoadedTables();
    String printLoadedTables();
    String innerJoinTables(String firstTable,int firstColIndex,String secondTable,int secondColIndex);
}
