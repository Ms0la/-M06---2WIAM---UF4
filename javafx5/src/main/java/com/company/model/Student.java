package com.company.model;

import org.bson.types.ObjectId;

import java.sql.Date;


public class Student {
    public String id_sql;
    public ObjectId id_mongo;
    public String name;
    public int age;
    public Date bornDate;

    // MySql Constructor
    public Student(String id_sql, String name, int age, Date bornDate) {
        this.id_sql = id_sql;
        this.name = name;
        this.age = age;
        this.bornDate = bornDate;
    }

    // Mongo Constructor
    public Student(ObjectId id_mongo, String name, int age, Date bornDate) {
        this.id_mongo = id_mongo;
        this.name = name;
        this.age = age;
        this.bornDate = bornDate;
    }

    // MySql ToString
    public String toStringSql() {
        return "Actor{" +
                "id_sql=" + id_sql +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bornDate=" + bornDate +
                '}';
    }

    // Mongo ToString
    public String toStringMongo() {
        return "Actor{" +
                "id_mongo=" + id_mongo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bornDate=" + bornDate +
                '}';
    }
}
