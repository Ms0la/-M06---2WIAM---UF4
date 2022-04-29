package com.company.model;

import org.bson.types.ObjectId;

public class MyClass {
    public String id_sql;
    public ObjectId id_mongo;
    public String className;
    public String tutorName;
    public boolean isFilled;

    // MySql Constructor
    public MyClass(String id_sql, String title, String tutorName, boolean isFilled) {
        this.id_sql = id_sql;
        this.className = title;
        this.isFilled = isFilled;
        this.tutorName = tutorName;
    }

    // Mongo Constructor
    public MyClass(ObjectId id_mongo, String title, String tutorName, boolean isFilled) {
        this.id_mongo = id_mongo;
        this.className = title;
        this.isFilled = isFilled;
        this.tutorName = tutorName;
    }

    // MySql ToString
    public String toStringSql() {
        return "Movie{" +
                "id_sql='" + id_sql + '\'' +
                ", title='" + className + '\'' +
                ", tutorName='" + tutorName + '\'' +
                ", isFilled='" + isFilled + '\'' +
                '}';
    }

    // Mongo ToString
    public String toStringMongo() {
        return "Movie{" +
                ", id_mongo=" + id_mongo +
                ", title='" + className + '\'' +
                ", tutorName='" + tutorName + '\'' +
                ", isFilled='" + isFilled + '\'' +
                '}';
    }
}
