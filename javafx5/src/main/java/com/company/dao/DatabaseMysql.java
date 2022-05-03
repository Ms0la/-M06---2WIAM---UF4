package com.company.dao;

import com.company.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseMysql implements Database{

    String uri ="jdbc:mysql://localhost/mydatabase?user=myuser&password=mypass";

    @Override
    public void insertClass(String className, String tutorName, boolean isFilled) {
        try(Connection conn = DriverManager.getConnection(uri)){
            //INSERT
            PreparedStatement statement = conn.prepareStatement("INSERT INTO class(classname, tutorname, isfilled) VALUES(?, ?, ?)");
            statement.setString(1, className);
            statement.setString(2, tutorName);
            statement.setString(3, String.valueOf(isFilled));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<MyClass> queryAllClass() {
        List<MyClass> classes = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(uri)){
            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM class");
            while (resultSet.next()) {
                MyClass myClass = new MyClass(resultSet.getString("id"), resultSet.getString("classname"), resultSet.getString("tutorname"), Boolean.parseBoolean(resultSet.getString("isfilled")));
                classes.add(myClass);
                System.out.println(myClass.toStringSql());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes.stream();
    }

    @Override
    public MyClass queryClass(String className) {
        MyClass myClass = null;
        try(Connection conn = DriverManager.getConnection(uri)){
            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM class WHERE classname = '" + className + "'");
            while (resultSet.next()) {
                myClass = new MyClass(resultSet.getString("id"), resultSet.getString("classname"), resultSet.getString("tutorname"), Boolean.parseBoolean(resultSet.getString("isfilled")));
                System.out.println(myClass.toStringSql());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myClass;
    }

    @Override
    public void deleteClass(String classid) {
        try(Connection conn = DriverManager.getConnection(uri)){
            //DELETE
            PreparedStatement statement = conn.prepareStatement("DELETE FROM class WHERE id = (?)");
            statement.setString(1, classid);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClass(String class_id, String newClassName, String tutorName, boolean isFilled) {
        try(Connection conn = DriverManager.getConnection(uri)){
            //UPDATE
            PreparedStatement statement = conn.prepareStatement("UPDATE class SET classname = (?), tutorname = (?), isfilled = (?)  WHERE id = (?)");
            statement.setString(1, newClassName);
            statement.setString(2, tutorName);
            statement.setString(3, String.valueOf(isFilled));
            statement.setString(4, class_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertStudent(String name, int age, Date bornDate) {
        try(Connection conn = DriverManager.getConnection(uri)){
            //INSERT
            PreparedStatement statement = conn.prepareStatement("INSERT INTO students(name, age, borndate) VALUES(?, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setDate(3, bornDate);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student queryStudent(String name) {
        Student student = null;
        try(Connection conn = DriverManager.getConnection(uri)){
            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM students WHERE name = '" + name + "'");
            while (resultSet.next()) {
                student = new Student(resultSet.getString("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDate("borndate"));
                System.out.println(student.toStringSql());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student queryStudentByID(String id) {
        Student student = null;
        try(Connection conn = DriverManager.getConnection(uri)){
            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM students WHERE id = '" + id + "'");
            while (resultSet.next()) {
                student = new Student(resultSet.getString("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDate("borndate"));
                System.out.println(student.toStringSql());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Stream<Student> queryAllStudents() {
        List<Student> students = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(uri)){
            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDate("borndate"));
                students.add(student);
                System.out.println(student.toStringSql());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students.stream();
    }

    @Override
    public void deleteStudent(String student_id) {
        try(Connection conn = DriverManager.getConnection(uri)){
            PreparedStatement statement = conn.prepareStatement("DELETE FROM students WHERE id = (?)");
            statement.setString(1, student_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(String student_id, String newName, int age, Date bornDate) {
        try(Connection conn = DriverManager.getConnection(uri)){
            PreparedStatement statement = conn.prepareStatement("UPDATE students SET name = (?), age = (?), bornDate = (?) WHERE id = (?)");
            statement.setString(1, newName);
            statement.setInt(2, age);
            statement.setDate(3, bornDate);
            statement.setString(4, student_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertRelation(String id_student, String id_class) {
        try(Connection conn = DriverManager.getConnection(uri)){
            PreparedStatement statement = conn.prepareStatement("INSERT INTO studentsClass(id_student, id_class) VALUES(?, ?)");
            statement.setString(1, id_student);
            statement.setString(2, id_class);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRelation(String id_student, String id_class) {
        try(Connection conn = DriverManager.getConnection(uri)){
            PreparedStatement statement = conn.prepareStatement("DELETE FROM studentsClass WHERE id_student = (?) AND id_class = (?)");
            statement.setString(1, id_student);
            statement.setString(2, id_class);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Relation queryRelation(String id_student, String id_class) {
        Relation relation = null;
        try(Connection conn = DriverManager.getConnection(uri)){
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM studentsClass WHERE id_student = '" + id_student + "' AND id_class = '" + id_class + "'");
            while (resultSet.next()) {
                relation = new Relation(resultSet.getString("id_student"), resultSet.getString("id_class"), resultSet.getString("insertDate"));
                System.out.println(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relation;
    }

    @Override
    public Stream<Relation> queryRelationsByClassID(String id_class) {
        List<Relation> relations = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(uri)){
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM studentsClass WHERE id_class = '" + id_class + "'");
            while (resultSet.next()) {
                Relation relation = new Relation(resultSet.getString("id_student"), resultSet.getString("id_class"), resultSet.getString("insertDate"));
                relations.add(relation);
                System.out.println(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations.stream();
    }

    @Override
    public Stream<Relation> queryAllRelations() {
        List<Relation> relations = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(uri)){
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM studentsClass");
            while (resultSet.next()) {
                Relation relation = new Relation(resultSet.getString("id_student"), resultSet.getString("id_class"), resultSet.getString("insertDate"));
                relations.add(relation);
                System.out.println(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relations.stream();
    }
}
