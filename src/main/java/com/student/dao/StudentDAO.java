package com.student.dao;

import com.student.database.Database;
import com.student.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public int save(Student student) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "INSERT INTO students (name,email,gender,dob) values (?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,student.getName());
        stmt.setString(2,student.getEmail());
        stmt.setString(3,student.getGender());
        stmt.setDate(4,student.getDob());
        return stmt.executeUpdate();
    }
    public List<Student> getStudents() throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "SELECT * FROM students";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultSet=stmt.executeQuery();
        List<Student> studentList= new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            Date  dob = resultSet.getDate("dob");
            Student tempStd = new Student(id,name,email,gender,dob);
            studentList.add(tempStd);
        }
        return  studentList;
    }
    public  Student getStudent(int id) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        Student student =null;
        String sql= "SELECT * FROM students WHERE id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet resultSet = stmt.executeQuery();
        if(resultSet.next()){
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            Date  dob = resultSet.getDate("dob");
            student= new Student(id,name,email,gender,dob);
        }
        return  student;
    }
    public  int updateStudent(Student student) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "UPDATE students SET name=?,email=?,gender=?,dob=? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,student.getName());
        stmt.setString(2,student.getEmail());
        stmt.setString(3,student.getGender());
        stmt.setDate(4,student.getDob());
        stmt.setInt(5,student.getId());
        return stmt.executeUpdate();
    }
    public  int deleteStudent(int id) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        return  statement.executeUpdate();
    }
}
