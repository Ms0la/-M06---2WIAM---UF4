package com.company.dao;

import com.company.model.MyClass;
import com.company.model.Relation;
import com.company.model.Student;

import java.sql.Date;
import java.util.stream.Stream;

public interface Database {
    void insertClass(String className, String tutorName, boolean isFilled);
    MyClass queryClass(String className);
    Stream<MyClass> queryAllClass();
    void deleteClass(String className);
    void updateClass(String className, String newClassName, String tutorName, boolean isFilled);

    void insertStudent(String name, int age, Date bornDate);
    Student queryStudent(String name);
    Stream<Student> queryAllStudents();
    void deleteStudent(String name);
    void updateStudent(String name, String newName, int age, Date bornDate);

    void insertRelation(String id_student, String id_class);
    void deleteRelation(String id_student, String id_class);
    Relation queryRelation(String id_student, String id_class);
    Stream<Relation> queryAllRelations();
}
