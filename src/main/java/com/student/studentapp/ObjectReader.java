package com.student.studentapp;

import java.io.*;

public class ObjectReader {
    public static Student_old[] read(File source) throws IOException {
        if(source == null) return null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(source))){
            Student_old[] studentArray = (Student_old[]) ois.readObject();
            return  studentArray;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
