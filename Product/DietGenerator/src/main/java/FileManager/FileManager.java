package FileManager;

import GeneticAlgorithm.Diet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import GeneticAlgorithm.Aliment;
import GeneticAlgorithm.MealOption;
import com.ibm.icu.text.ArabicShapingException;
import com.mycompany.dietgenerator.App;
import com.mycompany.dietgenerator.Validation;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.ibm.icu.text.Bidi;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 * A static class which contains operations related to File Management. These operations
 * include, saving diets as an editable file, saving diets as a PDF, reading and writing
 * to the aliment.data file, and loading diets from editable files.
 * 
 * @author kxg708
 */
public class FileManager {
    /**
     * location of aliment.data file
     */
    public static final String ALIMENT_DATA_FILE_LOCATION = ".\\resources\\aliments.data";
    
    /**
     * location of PDF printing font file. The format for this location string
     * is different with the one above, because it is being passed to the 
     * App.class.getResource() method.
     */
    public static final String FONT_FILE_LOCATION = "font/B-NAZANIN.TTF";
    
    /**
     * 50 pixels margin on the PDF printing page.
     */
    public static float pdfPageMargin = 50;
    
    /**
     * The x-coordinate for printing a on PDF page.
     */
    public static float pdfStartX;
    
    /**
     * The y-coordinate for printing a on PDF page.
     */
    public static float pdfStartY;
    
    /**
     * 30 pixels space between lines.
     */
    public static float pdfLineHeight = 30;
    
    /**
     * Apache PDF box variable for font.
     */
    public static PDType0Font font;
    
    /**
     * This method opens a file chooser window where user can select a diet file,
     * and calls the private method {@link #readAlimentDataFile()} to read its
     * contents.
     * 
     * @return the Diet object read from file. If no file is chosen null is returned.
     * @throws FileNotFoundException in case of error.
     * @throws IOException in case of error.
     * @throws ClassNotFoundException in case of error.
     */
    public static Diet openDietFile() throws FileNotFoundException, IOException, ClassNotFoundException {
            Stage window = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open a Diet File");
            fileChooser.getExtensionFilters()
                       .addAll(new FileChooser.ExtensionFilter("Diet", "*.diet"));
            
            File file = fileChooser.showOpenDialog(window); // file chooser pop-up
            
            if (file != null) return readDietFile(file);
            
            // translation of text: no file was opened!
            Validation.showWarningAlert("هیچ فایلی باز نشد!");
            return null;
    }
    
    /**
     * This method takes in a diet file object and reads and returns its content.
     * 
     * @return the read Diet object from file.
     * @throws FileNotFoundException in case of error.
     * @throws IOException in case of error.
     * @throws ClassNotFoundException in case of error.
     */
    private static Diet readDietFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream inFileStream = new FileInputStream(file);
        ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);
        
        Diet diet = (Diet) inObjectStream.readObject();
        return diet;
    }
    

    /**
     * This method takes a diet and opens a file chooser window for the user to
     * save the diet as a file. It calls the {@link #saveDietFile(java.io.File, GeneticAlgorithm.Diet)}
     * to do this.
     * 
     * This method is called when a diet is being saved in a new file.
     * 
     * @param diet the Diet object which should be written to the file.
     */
    public static void saveAsDietFile(Diet diet) {
        try {
            Stage window = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save a Diet File");
            fileChooser.getExtensionFilters()
                       .addAll(new FileChooser.ExtensionFilter("Diet", "*.diet"));
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                diet.filename = file.getName();
                diet.path = file.getAbsolutePath();
                
                saveDietFile(file, diet);
            }
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
    
    /**
     * Takes a Diet object and a File object, and writes the diet to the file. It
     * is used in {@link #saveAsDietFile(GeneticAlgorithm.Diet)} and also in
     * dietDisplayWindow when a previously saved Diet file has been modified and
     * should be saved. In this case, a file chooser is not needed to be displayed.
     * 
     * @param diet the Diet object which should be written.
     * @param file the file to which the Diet object should be written.
     * @throws FileNotFoundException in case of error.
     * @throws IOException in case of error.
     */
    public static void saveDietFile(File file, Diet diet) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(diet);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    
    
    /**
     * This method draws a horizontal line separator on the PDF page using the
     * PDPageContentStream on a specified line number. This is done to implement
     * Apache PDF Box Printing operations easier, and reduces code redundancy.
     * 
     * @param stream the stream for printing to the PDF page.
     * @param lineNum the line number on which it should be drawn.
     * @throws IOException in case of error.
     */
    private static void drawSeperator(PDPageContentStream stream, int lineNum) throws IOException {
        stream.moveTo(pdfPageMargin, pdfStartY - pdfLineHeight*(lineNum - 1)); // Starting point of separator line
        stream.lineTo(pdfStartX, pdfStartY - pdfLineHeight*(lineNum - 1)); // Ending point of separator line
        stream.setStrokingColor(0, 0, 0); // Set color of the separator line
        stream.setLineWidth(1); // Set the width of separator line
        stream.stroke(); // Draw separator line
    }
    
    /**
     * This method prints a specified text on a specified line number on the
     * PDF page. It is used to implement Apache PDF Box printing operations
     * easier, and reduces code redundancy.
     * 
     * @param stream the stream for printing to the PDF page.
     * @param text the text which should be printed.
     * @param lineNum the line number on which it should be drawn.
     * @param fontSize the size of the text font.
     * @throws IOException in case of error.
     */
    private static void printText(PDPageContentStream stream, String text, int lineNum, int fontSize) throws IOException {
        text = processRTLText(text);
        // Calculate the width of the text (AI generated)
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        
        stream.beginText();
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(pdfStartX - textWidth, pdfStartY - pdfLineHeight*(lineNum - 1));
        stream.showText(text);
        stream.endText();
    }
    
    /**
     * This method prints a single page of the PDF. It takes in the PDF document,
     * the name of the meal (breakfast, lunch, etc.), and the meal options, and
     * creates a new page and adds it to the document.
     * 
     * @param document the PDF document to which the page should be printed.
     * @param options the list of meal options which should be printed on each line.
     * @param mealName the name of the meal.
     * @throws IOException in case of error.
     */
    private static void printPage(PDDocument document, ArrayList<MealOption> options, String mealName) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream stream = new PDPageContentStream(document, page);
        
        pdfStartX = page.getMediaBox().getWidth() - pdfPageMargin;
        pdfStartY = 700;
        
        printText(stream, mealName, 1, 24);
        drawSeperator(stream, 2);
        // translation of text: please choose one of the options below
        printText(stream, "یکی از گزینه های زیر را انتخاب کنید", 3, 12);
        drawSeperator(stream, 4);
        
        //print mealOptions
        for (int i = 0; i < options.size();i++) {
            printText(stream, options.get(i).toString(), i + 5, 12);
        }
        
        stream.close();
    }
    
    /**
     * This method takes in a Diet object, and prints the meal options of each meal
     * on a separate page in a PDF, using {@link #printPage(org.apache.pdfbox.pdmodel.PDDocument, java.util.ArrayList, java.lang.String)}
     * and opens a file chooser to save the PDF location.
     * 
     * If no file is selected by file chooser, it is not saved anywhere.
     * 
     * @param diet the Diet object which should be printed to the PDF. 
     */
    public static void saveAsPDF(Diet diet) {
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, App.class.getResourceAsStream(FONT_FILE_LOCATION));
            
            printPage(document, diet.getBreakfast(), "صبحانه");
            printPage(document, diet.getSnack(), "میان وعده");
            printPage(document, diet.getLunch(), "ناهار");
            printPage(document, diet.getDinner(), "شام");
            
            
            Stage window = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as PDF");
            fileChooser.getExtensionFilters()
                       .addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                document.save(file);
            }

        } catch (IOException e) {
            Validation.showErrorAlert(e);
        }
    }
    /**
     * To be able to print RTL Persian text with Apache PDF Box, this method had to be
     * included. It is taken from source:
     * https://stackoverflow.com/questions/48284888/writing-arabic-with-pdfbox-with-correct-characters-presentation-form-without-bei
     * 
     * @param rawText text to be processed
     * @returns formatted text.
     */
    private static String processRTLText(String rawText) {
        try {
            Bidi bidi = new Bidi((new PersianShaping(PersianShaping.LETTERS_SHAPE)).shape(rawText), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        }
        catch (ArabicShapingException ase3) {
            return rawText;
        }
    }
    
    /**
     * This method reads the list of aliments in the aliments.data file, and saves it in
     * the App.aliments global variable. It is called each time the program is launched.
     */
    public static void readAlimentDataFile() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ALIMENT_DATA_FILE_LOCATION));
            App.aliments = (List<Aliment>) inputStream.readObject();
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
    
    /**
     * This method takes an aliment object and adds it to the App.aliments global variable.
     * It also updates the data file by running {@link #rewriteToAlimentDataFile()}
     * 
     * It is used when creating a new aliment.
     * @param aliment the aliment to be added.
     */
    public static void addToAlimentDataFile(Aliment aliment) {
        App.aliments.add(aliment);
        rewriteToAlimentDataFile();
    }
    
    /**
     * This method takes an aliment object and removes it from the App.aliments global variable.
     * It also updates the data file by running {@link #rewriteToAlimentDataFile()}
     * 
     * It is used when deleting an aliment.
     * @param aliment the aliment to be removed.
     */
    public static void removeFromAlimentDataFile(Aliment aliment) {
        App.aliments.remove(aliment);
        rewriteToAlimentDataFile();
    }
    
    /**
     * This method writes the App.aliments list to the data file, in order to capture any additions
     * or removal of aliments.
     */
    private static void rewriteToAlimentDataFile() {
        try {
            File file = new File(ALIMENT_DATA_FILE_LOCATION);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            
            oos.writeObject(App.aliments);
            oos.close();
            
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
}
