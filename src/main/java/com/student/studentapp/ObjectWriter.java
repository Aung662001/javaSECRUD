package com.student.studentapp;

import java.io.*;

public class ObjectWriter {
    public static void write(File target, Student_old[] students) throws IOException {
        if(target == null) return;
        try(ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(target))){
            oos.writeObject(students);
        }
    }
}
