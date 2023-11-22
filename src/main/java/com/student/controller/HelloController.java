package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import com.student.studentapp.ObjectReader;
import com.student.studentapp.ObjectWriter;
import com.student.studentapp.Student_old;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button saveBtn;
    @FXML
    private TextField nameField;
    @FXML
    private  TextField emailField;
    @FXML
    private DatePicker dobField;
    @FXML
    private ToggleGroup genderSelect;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private  TableView studentTable;
    private  StudentDAO dao;
    private  Integer id = 1;


    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("vcad only","*.vcad");
    FileChooser.ExtensionFilter filter1= new FileChooser.ExtensionFilter("all files","*.*");
//    public   void saveBtnClick(ActionEvent event){
//        String name = nameField.getText();
//        String email = emailField.getText();
//        LocalDate dob= dobField.getValue();
//        String gender = (String)genderSelect.getSelectedToggle().getUserData();
//
//        if(name.isEmpty() || email.isEmpty() || dob == null || gender.isEmpty()){
//            System.out.println("Please fill out all fields");
//            return;
//        }
//        Student student = new Student(id.toString(),name,email,dob,gender);
//        studentTable.getItems().add(student);
//        nameField.clear();
//        emailField.clear();
//        dobField.setValue(null);
//        id++;
//    }
    @FXML
    public  void saveBtnClick(){
        String name = nameField.getText();
        String email = emailField.getText();
        LocalDate localDate=dobField.getValue();
        String gender = (String)genderSelect.getSelectedToggle().getUserData();
        if(name.isEmpty() || email.isEmpty() || localDate == null || gender.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all fields.");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        Date dob = Date.valueOf(localDate);
        if(saveBtn.getText().equals("Update")){
            try {
                Student student = new Student(id,name,email,gender,dob);
                int ret= dao.updateStudent(student);
                System.out.println(ret);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Student Successfully Updated.");
                setTableData();
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.show();
                clearForm();
                saveBtn.setText("Save");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                Student student = new Student(id,name,email,gender,dob);
                dao.save(student);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Student Successfully saved.");
                setTableData();
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.show();
                clearForm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        maleRadio.setSelected(true);
        dobField.setValue(null);
    }

//    @FXML
//    public void openFileClick(ActionEvent event) {
//        studentTable.getItems().clear();
//        Stage stage = (Stage)saveBtn.getScene().getWindow();
//        fileChooser.getExtensionFilters().addAll(filter,filter1);
//        File source = fileChooser.showOpenDialog(stage);
//        try {
//            Student_old[] studentsArr= ObjectReader.read(source);
//            if(studentsArr==null) return;
//            studentTable.getItems().setAll(studentsArr);
//            id=studentsArr.length+1;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//@FXML
//    public void saveFileClick(ActionEvent event) {
//        Stage stage = (Stage)saveBtn.getScene().getWindow();
//        File target =fileChooser.showSaveDialog(stage);
//        fileChooser.getExtensionFilters().addAll(filter,filter1);
//        ObservableList<Student_old> stdList = studentTable.getItems();
//        Student_old[] students = stdList.toArray(new Student_old[stdList.size()]);
//        try {
//            ObjectWriter.write(target,students);
//            studentTable.getItems().clear();
//            stage.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing app...");
        dao = new StudentDAO();
        setTableData();
    }

    public void setTableData() {
        try {
            studentTable.getItems().clear();
            List<Student> students = dao.getStudents();
            studentTable.getItems().addAll(students);

            mouseClickOnCellHandler();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void mouseClickOnCellHandler(){
        studentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                    TableView.TableViewSelectionModel<?> selectionModel = studentTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                if (mouseEvent.getClickCount() == 2) {
                    doubleClickHandler(selectedItem);
                }
                if (mouseEvent.getClickCount() == 3){
                    tripleClickHandler(selectedItem);
                }
            }

            private void  doubleClickHandler(Object selectedItem){
                // Retrieve the values from the selected item
                if (selectedItem != null) {
                    // Assuming your table contains Student objects
                    Student selectedStudent = (Student) selectedItem;

                    int studentID = selectedStudent.getId();

                    try {
                        Student student = dao.getStudent(studentID);
                        saveBtn.setText("Update");
                        id=student.getId();
                        nameField.setText(student.getName());
                        emailField.setText(student.getEmail());
                        if(student.getGender().equals("Female")){
                            femaleRadio.setSelected(true);
                        }

                        LocalDate localDate = student.getDob().toLocalDate();
                        dobField.setValue(localDate);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            private void tripleClickHandler(Object selectedItem) {
                // Retrieve the values from the selected item
                if (selectedItem != null) {
                    // Assuming your table contains Student objects
                    Student selectedStudent = (Student) selectedItem;

                    int studentID = selectedStudent.getId();

                    try {
                        dao.deleteStudent(studentID);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Student Successfully Deleted.");
                        setTableData();
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.show();
                        clearForm();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

}