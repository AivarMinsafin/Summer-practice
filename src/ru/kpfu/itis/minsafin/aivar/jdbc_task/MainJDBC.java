package ru.kpfu.itis.minsafin.aivar.jdbc_task;

import java.sql.*;

//Main for task №3 (JDBC)
public class MainJDBC {
    private static final String URL = "jdbc:postgresql://localhost:5432/javapractice";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) throws SQLException {
        SimpleDataSource source = new SimpleDataSource();
        Connection connection = source.openConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student");
        while (resultSet.next()){
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("First Name: " + resultSet.getString("first_name"));
            System.out.println("Last Name: " + resultSet.getString("last_name"));
            System.out.println("Age: " + resultSet.getInt("age"));
            System.out.println("Group: " + resultSet.getInt("group_number"));
        }
        System.out.println("-------------------------------------------------");
        resultSet.close();
        resultSet = statement.executeQuery("select s.id s_id from student s full outer join mentor m on s.id = m.student_id;");
        while (resultSet.next()){
            System.out.println("ID: " + resultSet.getInt("s_id"));
        }
        connection.close();
    }
}
