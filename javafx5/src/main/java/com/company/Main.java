package com.company;

import com.company.dao.Database;
import com.company.dao.DatabaseMongo;
import com.company.dao.DatabaseMysql;
import com.company.model.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.stream.Stream;


public class Main implements Database {

    static Scanner sc = new Scanner(System.in);
    static Database db = null;
    static int action = 1;


    public static void main(String[] args) {
        Main main = new Main();
        String nameTmp, newNameTmp, dateTmp, tutorNametmp, studentId, classId;
        int ageTmp;
        Boolean isFilledTmp;
        java.util.Date utilDate;
        java.sql.Date sqlDate;

        while (action != 0) {

            if (Menu.dbMenu() == 1) {
                db = new DatabaseMysql();
                action = Menu.actionMenu();
            } else {
                db = new DatabaseMongo();
                action = Menu.actionMenu();
            }

            switch (action) {
                case 1:
                    System.out.println("CLASS NAME");
                    nameTmp = sc.nextLine();
                    System.out.println("CLASS TUTORNAME");
                    tutorNametmp = sc.nextLine();
                    System.out.println("CLASS ISFILLED");
                    isFilledTmp = sc.nextBoolean();
                    main.insertClass(nameTmp, tutorNametmp, isFilledTmp);
                    sc.nextLine();
                    break;
                case 2:
                    System.out.println("CLASS NAME");
                    main.queryClass(sc.nextLine());
                    break;
                case 3:
                    main.queryAllClass();
                    break;
                case 4:
                    System.out.println("CLASS ID");
                    main.deleteClass(sc.nextLine());
                    break;
                case 5:
                    System.out.println("CLASS ID");
                    nameTmp = sc.nextLine();
                    System.out.println("CLASS NEW NAME");
                    newNameTmp = sc.nextLine();
                    System.out.println("CLASS NEW TUTORNAME");
                    tutorNametmp = sc.nextLine();
                    System.out.println("CLASS NEW ISFILLED");
                    isFilledTmp = sc.nextBoolean();
                    main.updateClass(nameTmp, newNameTmp, tutorNametmp, isFilledTmp);
                    sc.nextLine();
                    break;

                case 6:
                    System.out.println("STUDENT NAME");
                    nameTmp = sc.nextLine();
                    System.out.println("STUDENT AGE");
                    ageTmp = Integer.parseInt(sc.nextLine());
                    System.out.println("STUDENT BORNDATE( DD MM YYYY)");
                    dateTmp = sc.nextLine();//"01 04 2012";
                    utilDate = null;
                    try {
                        utilDate = new SimpleDateFormat("dd MM yyyy").parse(dateTmp);
                    } catch (ParseException e) {
                        System.out.println(e.toString());;
                    }
                    sqlDate = new java.sql.Date(utilDate.getTime());
                    main.insertStudent(nameTmp, ageTmp, sqlDate);
                    break;
                case 7:
                    System.out.println("STUDENT NAME");
                    main.queryStudent(sc.nextLine());
                    break;
                case 8:
                    main.queryAllStudents();
                    break;
                case 9:
                    System.out.println("STUDENT ID");
                    main.deleteStudent(sc.nextLine());
                    break;
                case 10:
                    System.out.println("STUDENT ID");
                    nameTmp = sc.nextLine();
                    System.out.println("STUDENT NEW NAME");
                    newNameTmp = sc.nextLine();
                    System.out.println("STUDENT NEW AGE");
                    ageTmp = Integer.parseInt(sc.nextLine());
                    System.out.println("STUDENT NEW BORNDATE(DD MM YYYY)");
                    dateTmp = sc.nextLine();
                    utilDate = null;
                    try {
                        utilDate = new SimpleDateFormat("dd MM yyyy").parse(dateTmp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    sqlDate = new java.sql.Date(utilDate.getTime());
                    main.updateStudent(nameTmp, newNameTmp, ageTmp, sqlDate);
                    break;
                case 11:
                    System.out.println("STUDENT_ID");
                    studentId = sc.nextLine();
                    System.out.println("CLASS_ID");
                    classId = sc.nextLine();
                    main.insertRelation(studentId, classId);
                    break;
                case 12:
                    System.out.println("STUDENT_ID");
                    studentId = sc.nextLine();
                    System.out.println("CLASS_ID");
                    classId = sc.nextLine();
                    main.deleteRelation(studentId, classId);
                    break;
                case 13:
                    System.out.println("STUDENT_ID");
                    studentId = sc.nextLine();
                    System.out.println("CLASS_ID");
                    classId = sc.nextLine();
                    main.queryRelation(studentId, classId);
                    break;
                case 14:
                    main.queryAllRelations();
                    break;

                case 0:
                    System.out.println("exit");
                    break;
            }
        }

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
    public void deleteClass(String className) {
        db.deleteClass(className);
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
    public Stream<Relation> queryAllRelations() {
        return db.queryAllRelations();
    }

}
