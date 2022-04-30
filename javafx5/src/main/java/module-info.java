module com.example.uf4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.sql;
    requires org.mongodb.driver.core;
    requires javafx.base;
    requires org.slf4j;

    opens com.company.dao to javafx.fxml, javafx.base;
    opens com.company.model to javafx.fxml, javafx.base;

}