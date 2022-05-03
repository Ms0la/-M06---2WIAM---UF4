package com.example.uf4;

import com.company.dao.Database;
import com.company.dao.DatabaseMongo;
import com.company.dao.DatabaseMysql;
import com.company.model.MyClass;
import com.company.model.Relation;
import com.company.model.Student;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class THEController implements Database {
    static Database db = null;
    java.util.Date utilDate;
    java.sql.Date sqlDate;
    static String studentIdTmp, classIdTmp;
    static Boolean isEditing, isInserting, isSql;

    @FXML
    private TableView<Student> tableStudent = new TableView<>();
    @FXML
    private TableColumn<Student, String> colStudentId = new TableColumn<>("StudentID");
    @FXML
    private TableColumn<Student, String> colStudentName = new TableColumn<>("StudentName");
    @FXML
    private TableColumn<Student, Integer> colAge = new TableColumn<>("Age");
    @FXML
    private TableColumn<Student, java.sql.Date> colBornDate = new TableColumn<>("BornDate");

    @FXML
    private TableView<MyClass> tableMyClass = new TableView<>();
    @FXML
    private TableColumn<MyClass, String> colClassId = new TableColumn<>("ClassID");
    @FXML
    private TableColumn<MyClass, String> colClassName = new TableColumn<>("ClassName");
    @FXML
    private TableColumn<MyClass, String> colTutorName = new TableColumn<>("TutorName");
    @FXML
    private TableColumn<MyClass, Boolean> colIsFilled = new TableColumn<>("IsFilled");

    @FXML
    private TableView<Student> tableRelation = new TableView<>();


    @FXML
    private RadioButton sql;
    @FXML
    private RadioButton mongo;

    @FXML
    private TextField classText;
    @FXML
    private TextField studentText;

    @FXML
    private TextField textClassName;
    @FXML
    private TextField textTutorName;
    @FXML
    private CheckBox checkBoxFilled;
    @FXML
    private RadioButton insertClassSql;
    @FXML
    private RadioButton insertClassMongo;

    @FXML
    private TextField textStudentName;
    @FXML
    private TextField textStudentAge;
    @FXML
    private TextField textStudentBornDate;
    @FXML
    private RadioButton insertStudentSql;
    @FXML
    private RadioButton insertStudentMongo;
    @FXML
    private Button cancelInsertBtn;

    @FXML
    private Button btnStudentView;
    @FXML
    private Button btnMyClassView;

    @FXML
    private TextField textRelationStudentName;

    @FXML
    protected void searchStudent(){
        if (sql.isSelected()){
            db = new DatabaseMysql();
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("id_sql"));
        }else{
            db = new DatabaseMongo();
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("id_mongo"));
        }

        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBornDate.setCellValueFactory(new PropertyValueFactory<>("bornDate"));

        tableStudent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableStudent.getColumns().clear();
        tableStudent.getColumns().addAll(colStudentId, colStudentName, colAge, colBornDate);
        tableStudent.getItems().clear();
        if (studentText.getText().isBlank()){
            Stream<Student> a = db.queryAllStudents();
            tableStudent.getItems().addAll(a.toList());
        }else{
            Student student = db.queryStudent(studentText.getText());
            tableStudent.getItems().add(student);
        }
    }

    @FXML
    protected void searchClass(){
        if (sql.isSelected()){
            db = new DatabaseMysql();
            colClassId.setCellValueFactory(new PropertyValueFactory<>("id_sql"));
            isSql = true;
        }else{
            db = new DatabaseMongo();
            colClassId.setCellValueFactory(new PropertyValueFactory<>("id_mongo"));
            isSql = false;
        }

        colClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
        colTutorName.setCellValueFactory(new PropertyValueFactory<>("tutorName"));
        colIsFilled.setCellValueFactory(new PropertyValueFactory<>("isFilled"));

        tableMyClass.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableMyClass.getColumns().clear();
        tableMyClass.getColumns().addAll(colClassId, colClassName, colTutorName, colIsFilled);
        tableMyClass.getItems().clear();
        if (classText.getText().isBlank()){
            Stream<MyClass> a = db.queryAllClass();
            tableMyClass.getItems().addAll(a.toList());
        }else{
            MyClass myClass = db.queryClass(classText.getText());
            tableMyClass.getItems().add(myClass);
        }

    }

    @FXML
    protected void searchRelation(){
        if (isSql){
            db = new DatabaseMysql();
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("id_sql"));
        }else{
            db = new DatabaseMongo();
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("id_mongo"));
        }

        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBornDate.setCellValueFactory(new PropertyValueFactory<>("bornDate"));

        tableRelation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableRelation.getColumns().clear();
        tableRelation.getColumns().addAll(colStudentId, colStudentName, colAge, colBornDate);
        tableRelation.getItems().clear();

            List<Relation> r = db.queryRelationsByClassID(classIdTmp).toList();
            List<Student> a = new ArrayList<>();
            for (int i = 0; i< r.size(); i++){
                Relation rel = r.get(i);
                Student s = db.queryStudentByID(rel.id_sql_student);
                a.add(s);
            }
            tableRelation.getItems().addAll(a);

    }

    @FXML
    public void clickDeleteStudent(){
        if(!tableStudent.getSelectionModel().getSelectedCells().isEmpty()){
            TablePosition pos = tableStudent.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Student student = tableStudent.getItems().get(row);
            if(sql.isSelected()){
                db.deleteStudent(student.id_sql);
            }else if(mongo.isSelected()) {
                db.deleteStudent(String.valueOf(student.id_mongo));
            }
            searchStudent();
        } else {
            notSelected();
        }
    }

    @FXML
    public void clickUpdateStudent(){
        if(!tableStudent.getSelectionModel().getSelectedCells().isEmpty()){
            TablePosition pos = tableStudent.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Student student = tableStudent.getItems().get(row);
            if(sql.isSelected()){
                studentIdTmp = student.id_sql;
            }else if(mongo.isSelected()) {
                studentIdTmp = String.valueOf(student.id_mongo);
            }
            openUpdateStudent();
        } else {
            notSelected();
        }
    }

    @FXML
    public void clickDeleteClass(){
        if(!tableMyClass.getSelectionModel().getSelectedCells().isEmpty()){
            TablePosition pos = tableMyClass.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            MyClass myClass = tableMyClass.getItems().get(row);
            if(sql.isSelected()){
                db.deleteClass(myClass.id_sql);
            }else if(mongo.isSelected()) {
                db.deleteClass(String.valueOf(myClass.id_mongo));
            }
            searchClass();
        }  else {
            notSelected();
        }
    }

    @FXML
    public void clickUpdateClass(){
        if(!tableMyClass.getSelectionModel().getSelectedCells().isEmpty()){
            TablePosition pos = tableMyClass.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            MyClass myClass = tableMyClass.getItems().get(row);
            if(sql.isSelected()){
                classIdTmp = myClass.id_sql;
            }else if(mongo.isSelected()) {
                classIdTmp = String.valueOf(myClass.id_mongo);
            }
            openUpdateClass();
        }  else {
            notSelected();
        }
    }

    @FXML
    protected void openStudentView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStudent.fxml"));
            Stage stage = (Stage) btnStudentView.getScene().getWindow();
            stage.setTitle("View Students");
            Scene scene = new Scene(loader.load(), 640, 400);
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void openAltaStudent(){
        try {
            this.isEditing = false;
            this.isInserting = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AltaStudent.fxml"));
            Stage stage = new Stage();
            stage.setTitle("INSERT STUDENT");
            Scene scene = new Scene(loader.load(), 455, 325);
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void openUpdateStudent(){
        try {
            this.isEditing = true;
            this.isInserting = false;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStudent.fxml"));
            Stage stage = new Stage();
            stage.setTitle("UPDATE STUDENT");
            Scene scene = new Scene(loader.load(), 455, 260);
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void openMyClassView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMyClass.fxml"));
            Stage stage = (Stage) btnMyClassView.getScene().getWindow();
            stage.setTitle("View MyClass");
            Scene scene = new Scene(loader.load(), 640, 400);
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void openAltaMyClass(){
        try {
            this.isEditing = false;
            this.isInserting = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AltaMyClass.fxml"));
            Stage stage = new Stage();
            stage.setTitle("INSERT MYCLASS");
            Scene scene = new Scene(loader.load(), 420, 230);
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void openUpdateClass(){
        try {
            this.isEditing = true;
            this.isInserting = false;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateClass.fxml"));
            Stage stage = new Stage();
            stage.setTitle("UPDATE MYCLASS");
            Scene scene = new Scene(loader.load(), 600, 385);
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void notSelected(){
        Label secondLabel = new Label("Select some field");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Error");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    @FXML
    protected void insertStudentClick(){
        if(!isEditing && isInserting) {
            if (insertStudentSql.isSelected()) {
                db = new DatabaseMysql();
            } else if (insertStudentMongo.isSelected()){
                db = new DatabaseMongo();
            }
        }

        String nameTmp = textStudentName.getText();
        int ageTmp = Integer.parseInt(textStudentAge.getText());

        utilDate = null;
        String dateTmp = textStudentBornDate.getText();
        try {
            utilDate = new SimpleDateFormat("dd MM yyyy").parse(dateTmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sqlDate = new java.sql.Date(utilDate.getTime());

        if(!isEditing && isInserting){
            db.insertStudent(nameTmp, ageTmp, sqlDate);
        } else if(isEditing && !isInserting){
            db.updateStudent(studentIdTmp, nameTmp, ageTmp, sqlDate);
        }
        stopInsertStudent();
    }

    @FXML
    protected void checkNumbers() throws IllegalArgumentException{
        textStudentAge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    Platform.runLater(() -> {
                        textStudentAge.clear();
                    });
                }
            }
        });
    }

    @FXML
    protected void stopInsertStudent(){
        Stage stage = (Stage) cancelInsertBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void insertClassClick(){
        if(!isEditing && isInserting) {
            if (insertClassSql.isSelected()) {
                db = new DatabaseMysql();
            } else if (insertClassMongo.isSelected()){
                db = new DatabaseMongo();
            }
        }

        String classNameTmp = textClassName.getText();
        String tutorNameTmp = textTutorName.getText();
        boolean isFilledTmp = checkBoxFilled.isSelected();

        if(!isEditing && isInserting){
            db.insertClass(classNameTmp, tutorNameTmp, isFilledTmp);
        } else if(isEditing && !isInserting){
            db.updateClass(classIdTmp, classNameTmp, tutorNameTmp, isFilledTmp);
        }
        stopInsertStudent();
    }

    @FXML
    protected void openAltaRelation(){
        try {
            this.isEditing = false;
            this.isInserting = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AltaRelation.fxml"));
            Stage stage = new Stage();
            stage.setTitle("INSERT RELATION");
            Scene scene = new Scene(loader.load(), 420, 185);
            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void insertRelationClick(){
        String studentNameTmp = textRelationStudentName.getText();
        Student student = db.queryStudent(studentNameTmp);
        String studentid = "";

        if(isSql){
            studentid = student.id_sql;
        } else{
            studentid = String.valueOf(student.id_mongo);
        }


        db.insertRelation(studentid, classIdTmp);
    }

    @FXML
    public void clickDeleteRelation(){
        if(!tableRelation.getSelectionModel().getSelectedCells().isEmpty()){
            TablePosition pos = tableRelation.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Student student = tableRelation.getItems().get(row);
            if(isSql){
                db.deleteRelation(student.id_sql, classIdTmp);
            }else {
                db.deleteRelation(String.valueOf(student.id_mongo), classIdTmp);
            }
        }  else {
            notSelected();
        }
    }

    @FXML
    protected void onsqlbuttonclick(){
        mongo.setSelected(false);
    }
    @FXML
    protected void onmongobuttonclick(){
        sql.setSelected(false);
    }

    @FXML
    protected void onsqlbuttonclick2(){
        insertStudentMongo.setSelected(false);
    }
    @FXML
    protected void onmongobuttonclick2(){
        insertStudentSql.setSelected(false);
    }

    @FXML
    protected void onsqlbuttonclick3(){
        insertClassMongo.setSelected(false);
    }
    @FXML
    protected void onmongobuttonclick3(){
        insertClassSql.setSelected(false);
    }

    @Override
    public void insertClass(String className, String tutorName, boolean isFilled) {
        db.insertClass(className, tutorName, isFilled);
    }

    @Override
    public MyClass queryClass(String className){
        return db.queryClass(className);
    }

    @Override
    public Stream<MyClass> queryAllClass() {
        return db.queryAllClass();
    }

    @Override
    public void deleteClass(String classid) {
        db.deleteClass(classid);
    }

    @Override
    public void updateClass(String className, String newClassName, String tutorName, boolean isFilled) {
        db.updateClass(className, newClassName, tutorName, isFilled);
    }

    @Override
    public void insertStudent(String name, int age, Date bornDate) {
        db.insertStudent(name, age, bornDate);
    }

    @Override
    public Student queryStudent(String name) {
        return db.queryStudent(name);
    }

    @Override
    public Student queryStudentByID(String id) {
        return db.queryStudentByID(id);
    }

    @Override
    public Stream<Student> queryAllStudents() {
        return db.queryAllStudents();
    }

    @Override
    public void deleteStudent(String name) {
        db.deleteStudent(name);
    }

    @Override
    public void updateStudent(String name, String newName, int age, Date bornDate) {
        db.updateStudent(name, newName, age, bornDate);
    }

    @Override
    public void insertRelation(String id_student, String id_class) {
        db.insertRelation(id_student, id_class);
    }

    @Override
    public void deleteRelation(String id_student, String id_class) {
        db.deleteRelation(id_student, id_class);
    }

    @Override
    public Relation queryRelation(String id_student, String id_class) {
        return db.queryRelation(id_student, id_class);
    }

    @Override
    public Stream<Relation> queryRelationsByClassID(String id_class) {
        return db.queryRelationsByClassID(id_class);
    }

    @Override
    public Stream<Relation> queryAllRelations() {
        return db.queryAllRelations();
    }

}