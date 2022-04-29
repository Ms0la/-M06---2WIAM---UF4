package com.company.model;

import org.bson.types.ObjectId;

public class Relation {
    public String id_sql_actor;
    public String id_sql_movie;
    public ObjectId id_mongo_actor;
    public ObjectId id_mongo_movie;
    public String insertDate;

    public Relation(String id_sql_actor, String id_sql_movie, String insertDate) {
        this.id_sql_actor = id_sql_actor;
        this.id_sql_movie = id_sql_movie;
        this.insertDate = insertDate;
    }

    public Relation(ObjectId id_mongo_actor, ObjectId id_mongo_movie, String insertDate) {
        this.id_mongo_actor = id_mongo_actor;
        this.id_mongo_movie = id_mongo_movie;
        this.insertDate = insertDate;
    }

    public String toStringSql() {
        return "Relation{" +
                "id_sql_actor='" + id_sql_actor + '\'' +
                ", id_sql_movie='" + id_sql_movie + '\'' +
                ", insertDate='" + insertDate + '\'' +
                '}';
    }

    public String toStringMongo() {
        return "Relation{" +
                "id_mongo_actor=" + id_mongo_actor + '\'' +
                ", id_mongo_movie=" + id_mongo_movie + '\'' +
                ", insertDate='" + insertDate + '\'' +
                '}';
    }

}
