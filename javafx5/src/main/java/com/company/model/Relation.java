package com.company.model;

import org.bson.types.ObjectId;

public class Relation {
    public String id_sql_student;
    public String id_sql_class;
    public String insertDate;

    public Relation(String id_sql_student, String id_sql_class, String insertDate) {
        this.id_sql_student = id_sql_student;
        this.id_sql_class = id_sql_class;
        this.insertDate = insertDate;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "id_student='" + id_sql_student + '\'' +
                ", id_class='" + id_sql_class + '\'' +
                ", insertDate='" + insertDate + '\'' +
                '}';
    }

}
