package com.company.dao;


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
import org.bson.types.ObjectId;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

            for (Document d : collection.find(eq("classname", classname))) {
                MyClass myClass = new MyClass(d.getObjectId("_id"), d.getString("classname"), d.getString("tutorname"), Boolean.parseBoolean(d.getString("isFilled")));
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

            for (Document d : collection.find()) {
                MyClass myClass = new MyClass(d.getObjectId("_id"), d.getString("classname"), d.getString("tutorname"), Boolean.parseBoolean(d.getString("isFilled")));
                myClasses.add(myClass);
                System.out.println(myClass.toStringMongo());
            }
        }
        return myClasses.stream();
    }

    @Override
    public void deleteClass(String id_mongo) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            Document doc = new Document();
            doc.append("_id", new ObjectId(id_mongo));
            collection.deleteOne(doc);
        }
    }

    @Override
    public void updateClass(String id_mongo, String newClassName, String tutorName, boolean isFilled){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("class");

            Document doc = new Document().append("_id", new ObjectId(id_mongo));

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

            for (Document d : collection.find(eq("name", name))) {
                java.util.Date date = d.getDate("borndate");
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                Student student = new Student(d.getObjectId("_id"), d.getString("name"), d.getInteger("age"), sqlDate);
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

            for (Document d : collection.find()) {
                java.util.Date date = d.getDate("borndate");
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                Student student = new Student(d.getObjectId("_id"), d.getString("name"), d.getInteger("age"), sqlDate);
                students.add(student);
                System.out.println(student.toStringMongo());
            }
        }
        return students.stream();
    }

    @Override
    public void deleteStudent(String id_mongo) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            // DELETE
            Document doc = new Document();
            doc.append("_id", new ObjectId(id_mongo));
            collection.deleteOne(doc);
        }
    }

    @Override
    public void updateStudent(String id_mongo, String newName, int age, java.sql.Date bornDate) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("students");

            Document doc = new Document().append("_id", new ObjectId(id_mongo));

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
    public void insertRelation(String id_student, String id_class) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            Document doc = new Document();
            doc.append("id_student", id_student);
            doc.append("id_class", id_class);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            doc.append("timeStamp", dtf.format(now));

            collection.insertOne(doc);
        }
    }

    @Override
    public void deleteRelation(String id_student, String id_class) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            Document doc = new Document();
            doc.append("id_student", id_student);
            doc.append("id_class", id_class);
            collection.deleteOne(doc);
        }
    }

    @Override
    public Relation queryRelation(String id_student, String id_class) {
        List<Relation> relations = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            for (Document d : collection.find(eq("id_student", id_student))) {
                for(Document d1: collection.find(eq("id_class", id_class))){
                    Relation relation = new Relation(d1.getString("id_student"), d1.getString("id_class"), d1.getString("timeStamp"));
                    relations.add(relation);
                    System.out.println(relation);
                }
            }

        }
        return relations.get(0);
    }

    @Override
    public Stream<Relation> queryAllRelations() {
        List<Relation> relations = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("sampledb");
            MongoCollection<Document> collection = database.getCollection("studentsClass");

            for (Document d : collection.find()) {
                Relation relation = new Relation(d.getString("id_student"), d.getString("id_class"), d.getString("timeStamp"));
                relations.add(relation);
                System.out.println(relation);
            }

        }
        return relations.stream();
    }
}
