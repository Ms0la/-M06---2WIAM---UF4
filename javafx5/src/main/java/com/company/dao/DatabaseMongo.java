package com.company.dao;


import com.company.Menu;
import com.company.model.*;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseMongo implements Database {

    String uri = "mongodb://localhost";
    MongoDatabase database;


    public void insertClass(String className, String tutorName, boolean isFilled) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            // INSERT
            Document doc = new Document();
            doc.append("classname", className);
            doc.append("tutorname", tutorName);
            doc.append("isfilled", isFilled);
            collection.insertOne(doc);
        }
    }

    public MyClass queryClass(String classname) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            //QUERY
            for (Document d : collection.find(eq("classname", classname))) {
                MyClass myClass = new MyClass(d.getObjectId("_id"), d.getString("classname"), d.getString("tutorname"), d.getBoolean("isFilled"));
                System.out.println(myClass.toStringMongo());
                return myClass;
            }
        }
        return null;
    }

    @Override
    public Stream<MyClass> queryAllClass() {
        List<MyClass> myClasses = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            //QUERY
            for (Document d : collection.find()) {
                MyClass myClass = new MyClass(d.getObjectId("_id"), d.getString("classname"), d.getString("tutorname"), d.getBoolean("isFilled"));
                myClasses.add(myClass);
                System.out.println(myClass.toStringMongo());
            }
        }
        return myClasses.stream();
    }

    @Override
    public void deleteClass(String className) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            // DELETE
            Document doc = new Document();
            doc.append("classname", className);
            collection.deleteOne(doc);
        }
    }

    @Override
    public void updateClass(String className, String newClassName, String tutorName, boolean isFilled){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            // INSERT
            Document doc = new Document().append("classname", className);

            Bson updates = Updates.combine(
                    Updates.set("classname", newClassName),
                    Updates.set("tutorname", tutorName),
                    Updates.set("isfilled", isFilled)
            );

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                UpdateResult result = collection.updateOne(doc, updates, options);
            } catch (MongoException e) {
                System.out.println("Unable to update due to an error: " + e);
            }
        }
    }

    @Override
    public void insertStudent(String name, int age, Date bornDate) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            // INSERT
            Document doc = new Document();
            doc.append("name", name);
            doc.append("age", age);
            doc.append("borndate", bornDate);
            collection.insertOne(doc);
        }
    }

    @Override
    public Student queryStudent(String name) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            //QUERY
            for (Document d : collection.find(eq("name", name))) {
                Student student = new Student(d.getObjectId("_id"), d.getString("name"), d.getInteger("age"), java.sql.Date.valueOf(d.getString("borndate")));
                System.out.println(student.toStringMongo());
                return student;

            }
        }
        return null;
    }

    @Override
    public Stream<Student> queryAllStudents() {
        List<Student> students = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            //QUERY
            for (Document d : collection.find()) {
                Student student = new Student(d.getObjectId("_id"), d.getString("name"), d.getInteger("age"), java.sql.Date.valueOf(d.getString("borndate")));
                students.add(student);
                System.out.println(student.toStringMongo());
            }
        }
        return students.stream();
    }

    @Override
    public void deleteStudent(String name) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            // DELETE
            Document doc = new Document();
            doc.append("name", name);
            collection.deleteOne(doc);
        }
    }

    @Override
    public void updateStudent(String name, String newName, int age, Date bornDate) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            // INSERT
            Document doc = new Document().append("name", name);

            Bson updates = Updates.combine(
                    Updates.set("name", newName),
                    Updates.set("age", age),
                    Updates.set("borndate", bornDate)
            );

            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                UpdateResult result = collection.updateOne(doc, updates, options);
            } catch (MongoException e) {
                System.out.println("Unable to update due to an error: " + e );
            }
        }
    }

    @Override
    public void insertRelation(int id_student, int id_class) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            // INSERT
            Document doc = new Document();
            doc.append("id_student", id_student);
            doc.append("id_movie", id_class);
            collection.insertOne(doc);
        }
    }

    @Override
    public void deleteRelation(int id_student, int id_movie) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            // DELETE
            Document doc = new Document();
            doc.append("id_student", id_student);
            doc.append("id_class", id_movie);
            collection.deleteOne(doc);
        }
    }

    @Override
    public Relation queryRelation(int id_actor, int id_class) {
        //TODO
        List<Relation> relations = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            //QUERY
            for (Document d : collection.find()) {
                Relation relation = new Relation(d.getString("id_student"), d.getString("id_class"), d.getString("timeStamp"));
                relations.add(relation);
                System.out.println(relation.toStringMongo());
            }
        }
        return relations.get(0);
    }

    @Override
    public Stream<Relation> queryAllRelations() {
        //TODO
        List<Relation> relations = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            //QUERY
            for (Document d : collection.find()) {
                Relation relation = new Relation(d.getString("id_student"), d.getString("id_class"), d.getString("timeStamp"));
                relations.add(relation);
                System.out.println(relation.toStringMongo());
            }
        }
        return relations.stream();
    }
}
