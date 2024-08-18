/**
 * module-info.java
 */

module com.mycompany.dietgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.apache.pdfbox; // added
    requires com.ibm.icu; // added

    opens com.mycompany.dietgenerator to javafx.fxml;
    opens GeneticAlgorithm to javafx.base; // added this line
    exports com.mycompany.dietgenerator;
}
