package com.student.database;


import java.sql.*;

public class Database {
    private  String url = "jdbc:mysql://localhost:3306/studentapp";
    private  String name = "root";
    private String password ="";
    private Connection c;
    private  static Database db;

    private  Database() throws SQLException {
        connect();
    }
    public static Database getInstance() throws SQLException {
        if(db==null){
            db= new Database();
        }
        return  db;
    }
    private   boolean  connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=  DriverManager.getConnection(url,name,password);
            return  true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection(){
        return  c;
    }

}
