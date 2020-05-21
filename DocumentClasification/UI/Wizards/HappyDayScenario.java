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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException                     the io exception
     * @throws ClassNotFoundException          the class not found exception
     * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
     * @throws InstantiationException          the instantiation exception
     * @throws IllegalAccessException          the illegal access exception
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        gui=new GUI();

        if (args.length==6)
        {
            gui.setTrainedData(gui.loadTrainFiles(args[1]));
            gui.loadClasses(args[0],",");

            if (args[4].equals("bayes"))
            {
                if (args[3].equals("BackgroundWorkerBOW"))
                {
                    gui.generateBOW();
                    gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveBOW(args[5]);
                }
                else   if (args[3].equals("BackgroundWorkerIDF"))
                {
                    gui.generateIDF();
                    gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveIDF(args[5]);
                }
                else   if (args[3].equals("BackgroundWorkerTFIDF"))
                {
                    gui.generateTFIDF();
                    gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveTFIDF(args[5]);
                }
            }
            else if (args[4].equals("custom"))
            {
                if (args[3].equals("BackgroundWorkerBOW"))
                {
                    gui.generateBOW();
                    gui.customAccuracyBOWExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveBOW(args[5]);
                }
                else   if (args[3].equals("BackgroundWorkerIDF"))
                {
                    gui.generateIDF();
                    gui.customAccuracyIDFExecute(gui.loadTestFiles(args[2]));
                    System.out.println(gui.getInfoPanelBOW().getText());
                    gui.saveIDF(args[5]);
                }
                else   if (args[3].equals("BackgroundWorkerTFIDF"))
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

    /**
     * Naive bayes bow.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void naiveBayesBOW() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("NEXT STEP");
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateBOW();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelBOW().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Naive bayes idf.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void naiveBayesIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateIDF();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Naive bayes tfidf.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void naiveBayesTFIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateTFIDF();  System.out.println("NEXT STEP");
        gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelTFIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Custom bow.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void customBOW() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateBOW();  System.out.println("NEXT STEP");
        gui.customAccuracyBOWExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelBOW().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Custom idf.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void customIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateIDF();  System.out.println("NEXT STEP");
        gui.customAccuracyIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Custom tfidf.
     *
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public static void customTFIDF() throws IOException, ParserConfigurationException, SAXException {
        gui.setTrainedData(gui.loadTrainFiles());  System.out.println("NEXT STEP");
        gui.loadClasses();  System.out.println("NEXT STEP");
        gui.generateTFIDF();  System.out.println("NEXT STEP");
        gui.customAccuracyTFIDFExecute(gui.loadTestFiles());  System.out.println("NEXT STEP");
        System.out.println(gui.getInfoPanelTFIDF().getText());  System.out.println("NEXT STEP");
        gui.close();
    }

    /**
     * Custom tfidf loaded.
     *
     * @param TFIDF     the tfidf
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void customTFIDFLoaded(String TFIDF,String testFiles) throws IOException {
        gui.loadTFIDF(TFIDF);
        gui.customAccuracyTFIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelTFIDF().getText());
        gui.close();
    }

    /**
     * Custom bow loaded.
     *
     * @param BOW       the bow
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void customBOWLoaded(String BOW,String testFiles) throws IOException {
        gui.loadBOW(BOW);
        gui.customAccuracyBOWExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelBOW().getText());
        gui.close();
    }

    /**
     * Custom idf loaded.
     *
     * @param IDF       the idf
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void customIDFLoaded(String IDF,String testFiles) throws IOException {
        gui.loadDIF(IDF);
        gui.customAccuracyIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelIDF().getText());
        gui.close();
    }

    /**
     * Naive tfidf loaded.
     *
     * @param TFIDF     the tfidf
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void naiveTFIDFLoaded(String TFIDF,String testFiles) throws IOException {
        gui.loadTFIDF(TFIDF);
        gui.naiveBayesAccuracyTFIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelTFIDF().getText());
        gui.close();
    }

    /**
     * Naive bow loaded.
     *
     * @param BOW       the bow
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void naiveBOWLoaded(String BOW,String testFiles) throws IOException {
        gui.loadBOW(BOW);
        gui.naiveBayesAccuracyBOWExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelBOW().getText());
        gui.close();
    }

    /**
     * Naive idf loaded.
     *
     * @param IDF       the idf
     * @param testFiles the test files
     * @throws IOException the io exception
     */
    public static void naiveIDFLoaded(String IDF,String testFiles) throws IOException {
        gui.loadDIF(IDF);
        gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles(testFiles));
        System.out.println(gui.getInfoPanelIDF().getText());
        gui.close();
    }

    /**
     * Class loaded.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public static void classLoaded(String path) throws IOException {

        gui.loadClasses(path,",");
    }

}
