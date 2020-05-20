package UI.Wizards;

import Tools.IO;
import UI.GUI;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * UI.Wizards
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 18.05.20
 */
public class HappyDayScenario
{

    private static GUI gui;

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        gui=new GUI();

        if (args.length==6)
        {
            gui.setTrainedData(gui.loadTrainFiles(args[1]));
            gui.loadClasses(args[0],",");

            if (args[4].equals("bayes"))
            {
                if (args[3].equals("bow"))
                {
                    gui.generateBOW();
                    gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveBOW(args[5]);
                }
                else   if (args[3].equals("idf"))
                {
                    gui.generateIDF();
                    gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveIDF(args[5]);
                }
                else   if (args[3].equals("tfidf"))
                {
                    gui.generateTFIDF();
                    gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveTFIDF(args[5]);
                }
            }
            else if (args[4].equals("custom"))
            {
                if (args[3].equals("bow"))
                {
                    gui.generateBOW();
                    gui.customAccuracyBOWExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveBOW(args[5]);
                }
                else   if (args[3].equals("idf"))
                {
                    gui.generateIDF();
                    gui.customAccuracyIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveIDF(args[5]);
                }
                else   if (args[3].equals("tfidf"))
                {
                    gui.generateTFIDF();
                    gui.customAccuracyTFIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveTFIDF(args[5]);
                }
            }
        }

        if (args.length==0)
        {
            gui.main(new String[]{});
        }

    }

    public static void naiveBayesBOW() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("NEXT STEP");
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateBOW();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelBOW().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void naiveBayesIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateIDF();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void naiveBayesTFIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateTFIDF();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelTFIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void customBOW() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateBOW();  System.out.println("NEXT STEP");
        gui.customAccuracyBOWExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelBOW().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void customIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateIDF();  System.out.println("NEXT STEP");
        gui.customAccuracyIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void customTFIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateTFIDF();  System.out.println("NEXT STEP");
        gui.customAccuracyTFIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelTFIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    public static void customTFIDFLoaded(String TFIDF,String testFiles) throws IOException {
        gui.loadTFIDF(TFIDF);
        gui.customAccuracyTFIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelTFIDF().getText());
        gui.close();
    }

    public static void customBOWLoaded(String BOW,String testFiles) throws IOException {
        gui.loadBOW(BOW);
        gui.customAccuracyBOWExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelBOW().getText());
        gui.close();
    }

    public static void customIDFLoaded(String IDF,String testFiles) throws IOException {
        gui.loadDIF(IDF);
        gui.customAccuracyIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelIDF().getText());
        gui.close();
    }

    public static void naiveTFIDFLoaded(String TFIDF,String testFiles) throws IOException {
        gui.loadTFIDF(TFIDF);
        gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelTFIDF().getText());
        gui.close();
    }

    public static void naiveBOWLoaded(String BOW,String testFiles) throws IOException {
        gui.loadBOW(BOW);
        gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelBOW().getText());
        gui.close();
    }

    public static void naiveIDFLoaded(String IDF,String testFiles) throws IOException {
        gui.loadDIF(IDF);
        gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelIDF().getText());
        gui.close();
    }

    public static void classLoaded(String path) throws IOException {

        gui.loadClasses(path,",");
    }

}
