CREATE TABLE class (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     classname CHAR(255) NOT NULL,
     tutorname CHAR(255),
     isFilled TEXT,
     PRIMARY KEY (id)
);

CREATE TABLE students (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     name CHAR(255) NOT NULL,
     age int,
     borndate date,
     PRIMARY KEY (id)
);

CREATE TABLE studentsClass (
    id_student MEDIUMINT NOT NULL,
    id_class MEDIUMINT NOT NULL,
    FOREIGN KEY (id_student) REFERENCES students(id),
    FOREIGN KEY (id_class) REFERENCES class(id),
    PRIMARY KEY (id_student, id_class),
    insertDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);