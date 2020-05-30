package me.PauMAVA.SecurityWorld3.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SC3SQLAPI {

    private static final String USER = "apiuser";
    private static final String PASSWORD = "Ad5Sv7Dsrmps7P=3^$2dz3gBsR8ZL8D9";

    private static final String HOST = "127.0.0.1";
    private static final String PORT = "3306";

    private static final String DATABASE = "sc3";

    private final Connection connection;

    public SC3SQLAPI() {
        Connection connection1;
        try {
            connection1 = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true", USER, PASSWORD);
        } catch (SQLException throwables) {
            connection1 = null;
            throwables.printStackTrace();
        }
        this.connection = connection1;
    }

    public String executeStatement(String statement) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Statement: " + statement);
        for (String subquery: statement.split(";")) {
            try {
                if (subquery != null && !subquery.isBlank() && !subquery.isEmpty()) {
                    ResultSet resultSet = execute(subquery);
                    resultSetToString(sb, resultSet);
                }
            } catch (SQLException e) {
                return e.getMessage();
            }
        }
        return sb.toString();
    }

    private void resultSetToString(StringBuilder sb, ResultSet resultSet) throws SQLException {
        assert resultSet != null;
        int columnCount = resultSet.getMetaData().getColumnCount();
        List<String[]> allRows = new ArrayList<>();
        while(resultSet.next()){
            String[] currentRow = new String[columnCount];
            for(int i = 1; i <= columnCount; i++){
                currentRow[i-1] = resultSet.getString(i);
            }
            allRows.add(currentRow);
            System.out.println(Arrays.toString(currentRow));
        }
        for (String[] row: allRows) {
            sb.append(Arrays.toString(row)).append("\n");
        }
    }

    private ResultSet execute(String query) throws SQLException {
        Statement statement = connection.createStatement();
        if (query.contains("UPDATE") || query.contains("INSERT") || query.contains("DROP") || query.contains("DELETE")) {
            return null;
        }
        return statement.executeQuery(query);
    }


}
