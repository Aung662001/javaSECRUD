package com.student.database;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


class DatabaseTest {

    @Test
public  void InsertTest(){
        Student student = new Student("Maung Maung","maung@gmail.com","male",new Date(2001,05,06));
        StudentDAO dao = new StudentDAO();
        try {
            Assertions.assertEquals(1,dao.save(student));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public  void getStudentsTest(){
        StudentDAO dao = new StudentDAO();
        try {
           List<Student> students= dao.getStudents();
            for (Student std :
                    students) {
                System.out.println(std.getName()+"|"+std.getEmail()+"|"+std.getGender()+"|"+std.getDob());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public  void updateStudentTest(){
        StudentDAO dao = new StudentDAO();
        try {
//            both getStudent and Update pass
            Student std = dao.getStudent(1);
            System.out.println(std.getName()+"|"+std.getEmail()+"|"+std.getGender()+"|"+std.getDob());
            std.setEmail("aungaung@gmail.com");
            Assertions.assertEquals(1,dao.updateStudent(std));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public  void deleteTest(){
        StudentDAO dao = new StudentDAO();
        try{
            Assertions.assertEquals(1,dao.deleteStudent(2));
        }catch(SQLException e){throw new RuntimeException(e);}
    }
}