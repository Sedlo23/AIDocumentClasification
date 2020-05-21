package UI;

import Classification.Algorithms.*;
import Classification.Builders.*;
import Parametrization.BagOfWorld.BagOfWords;
import Parametrization.IDF.IDF;
import Parametrization.IDF.Word;
import Parametrization.TFIDF.TFIDF;
import Tools.IO;
import Tools.Text;
import UI.BackgroundWorkers.ACCTesting.*;
import UI.BackgroundWorkers.Generators.BackgroundWorkerBOW;
import UI.BackgroundWorkers.Generators.BackgroundWorkerIDF;
import UI.BackgroundWorkers.Generators.BackgroundWorkerTFIDF;
import org.xml.sax.SAXException;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 15.05.20
 */
public class GUI {

    /**
     * The Trained data.
     */
    private ArrayList<File> trainedData;
    /**
     * The Classes.
     */
    private String[] classes;

    private JPanel root;
    private JEditorPane inputTextBox;
    private JTextPane infoPanelBOW;
    private JMenuBar menu;
    private JTextPane infoPanelIDF;
    private JTextPane infoPanelTFIDF;
    private JTextPane systemOutput;
    private JProgressBar progressBar1;
    private JScrollPane JscrollPaneBow;
    private JScrollPane JscrollPaneIDf;
    private JScrollPane JscrollPaneTFIDF;
    private Boolean continuesBayesCheckBox = false;

    /**
     * The Custom BackgroundWorkerTFIDF.
     */
    private CustomTFIDF customTFIDF;
    /**
     * The Custom BackgroundWorkerIDF.
     */
    private CustomIDF customIDF;
    /**
     * The Custom BackgroundWorkerBOW.
     */
    private CustomBOW customBOW;

    /**
     * The Naive bayes BackgroundWorkerIDF.
     */
    private NaiveBayesIDF naiveBayesIDF;
    /**
     * The Naive bayes BackgroundWorkerTFIDF.
     */
    private NaiveBayesTFIDF naiveBayesTFIDF;
    /**
     * The Naive bayes BackgroundWorkerBOW.
     */
    private NaiveBayesBOW naiveBayesBOW;


    /**
     * Instantiates a new Gui.
     */
    public GUI( )
    {
        inputTextBox.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    ArrayList<File> droppedFiles = (ArrayList<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    inputTextBox.setText("");
                    for (File file : droppedFiles)
                    {
                        inputTextBox.setText(inputTextBox.getText()+"\n"+Files.readString(Paths.get(file.getAbsolutePath())));
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        inputTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (continuesBayesCheckBox){
                    if (naiveBayesBOW !=null)
                        infoPanelBOW.setText(naiveBayesBOW.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelBOW.setText("NOT LEARNED YET");

                    if (naiveBayesIDF!=null)
                        infoPanelIDF.setText(naiveBayesIDF.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelIDF.setText("NOT LEARNED YET");

                    if (naiveBayesTFIDF!=null)
                        infoPanelTFIDF.setText(naiveBayesTFIDF.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelTFIDF.setText("NOT LEARNED YET");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if (continuesBayesCheckBox){
                    if (naiveBayesBOW !=null)
                        infoPanelBOW.setText(naiveBayesBOW.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelBOW.setText("NOT LEARNED YET");

                    if (naiveBayesIDF!=null)
                        infoPanelIDF.setText(naiveBayesIDF.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelIDF.setText("NOT LEARNED YET");

                    if (naiveBayesTFIDF!=null)
                        infoPanelTFIDF.setText(naiveBayesTFIDF.getStringOutput(inputTextBox.getText()));
                    else
                        infoPanelTFIDF.setText("NOT LEARNED YET");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent)
            {
if (continuesBayesCheckBox){
                if (naiveBayesBOW !=null)
                    infoPanelBOW.setText(naiveBayesBOW.getStringOutput(inputTextBox.getText()));
                else
                    infoPanelBOW.setText("NOT LEARNED YET");

                if (naiveBayesIDF!=null)
                    infoPanelIDF.setText(naiveBayesIDF.getStringOutput(inputTextBox.getText()));
                else
                    infoPanelIDF.setText("NOT LEARNED YET");

                if (naiveBayesTFIDF!=null)
                    infoPanelTFIDF.setText(naiveBayesTFIDF.getStringOutput(inputTextBox.getText()));
                else
                    infoPanelTFIDF.setText("NOT LEARNED YET");
}
            }
        });

        progressBar1.setStringPainted(true);

        DefaultCaret caret = (DefaultCaret) infoPanelBOW.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        DefaultCaret caret2 = (DefaultCaret) infoPanelIDF.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        DefaultCaret caret3 = (DefaultCaret) infoPanelTFIDF.getCaret();
        caret3.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws ClassNotFoundException          the class not found exception
     * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
     * @throws InstantiationException          the instantiation exception
     * @throws IllegalAccessException          the illegal access exception
     */
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents()
    {
        menuInit();
    }

    private void menuInit()
    {
        menu=new JMenuBar();

        JMenu s=new JMenu("System");
        s.setBorderPainted(true);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit application");
        exit.addActionListener((event) -> System.exit(0));

        JMenuItem cca = new JMenuItem("Real time testing ["+false+"]");
        cca.addActionListener((event) ->{
            continuesBayesCheckBox =!continuesBayesCheckBox;
            cca.setText("Real time testing ["+ continuesBayesCheckBox +"]");
            systemOutput.setText(systemOutput.getText()+"Real time testing status: "+continuesBayesCheckBox+"\n");
        });

        JMenuItem rr = new JMenuItem("Clear Track");
        rr.addActionListener((event) ->{
          systemOutput.setText("");
        });

        s.add(rr);

        s.add(cca);

        s.add(exit);

        JMenu ll=new JMenu("Learning");
        ll.setBorderPainted(true);

        JMenu file=new JMenu("File");
        file.setBorderPainted(true);

        JMenuItem bagofwordsS = new JMenuItem("Save [Bag of Words]");
        bagofwordsS.setToolTipText("Save bag of words file");
        bagofwordsS.addActionListener((event) -> {
            try {
                saveBOW();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem idfS = new JMenuItem("Save [IDF]");
        idfS.setToolTipText("Save IDF file");
        idfS.addActionListener((event) -> {
            try {
                saveIDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem tfidfS = new JMenuItem("Save [TFIDF]");
        tfidfS.setToolTipText("Save TFIDF file");
        tfidfS.addActionListener((event) -> {
            try {
                saveTFIDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem bagofwords = new JMenuItem("Read [Bag of Words]");

        bagofwords.setToolTipText("Read saved bag of words file");

        bagofwords.addActionListener((event) -> {
            try {
                loadBOW();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem idf = new JMenuItem("Read [IDF]");
        idf.setToolTipText("Read saved IDF file");
        idf.addActionListener((event) -> {
            try {
                loadDIF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem tfidf = new JMenuItem("Read [TFIDF]");
        tfidf.setToolTipText("Read saved TFIDF file");
        tfidf.addActionListener((event) -> {
            try {
                loadTFIDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JMenuItem bagofwordsC = new JMenuItem("Learn by [Bag of Words]");
        bagofwordsC.setToolTipText("Learn by Bag of words method");
        bagofwordsC.addActionListener((event) -> {
            new BackgroundWorkerBOW(this).execute();
        });

        JMenuItem idfC = new JMenuItem("Learn by  [IDF]");
        idfC.setToolTipText("Learn by TFIDF method");
        idfC.addActionListener((event) -> {
            new BackgroundWorkerIDF(this).execute();
        });

        JMenuItem tfidfC = new JMenuItem("Learn by  [TFIDF]");
        tfidfC.setToolTipText("Learn by TFIDF method");
        tfidfC.addActionListener((event) -> {
            new BackgroundWorkerTFIDF(this).execute();
        });

        JMenuItem tttt = new JMenuItem("Read Test data");
        tttt.addActionListener((event) -> {
            try {
                loadTestFiles();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });

        JMenuItem lllll = new JMenuItem("Read Train data");
        lllll.addActionListener((event) -> {
            try {
                trainedData = loadTrainFiles();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        JMenuItem cc = new JMenuItem("Read Classes");
        cc.addActionListener((event) -> {
            try {
                loadClasses();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        file.add(bagofwordsS);
        file.add(idfS);
        file.add(tfidfS);
        file.addSeparator();
        file.add(bagofwords);
        file.add(idf);
        file.add(tfidf);

        ll.add(bagofwordsC);
        ll.add(idfC);
        ll.add(tfidfC);
        ll.addSeparator();
        // ll.add(cc);
        //file.add(tttt);
        ll.add(lllll);



        JMenu options=new JMenu("Evaluate");
        options.setBorderPainted(true);

        JMenuItem nav = new JMenuItem("Naive Bayes Algorithm [TextBox input]");
        nav.addActionListener((event) -> naiveBayesExecute());

        JMenuItem cus = new JMenuItem("Sedlo Algorithm [TextBox input]");
        cus.setToolTipText("Use custom algorithm");
        cus.addActionListener((event) -> customExecute());

        JMenuItem nav2 = new JMenuItem("Naive Bayes [Accuracy Test] [ALL]");
        nav2.addActionListener((event) -> {
            new BackgroundWorkerNaiveBayesALL(this).execute();
        });
        JMenuItem nav21 = new JMenuItem("   - [BOW]");
        nav21.addActionListener((event) -> {
            new BackgroundWorkerNaiveBayesBOW(this).execute();
        });
        JMenuItem nav22 = new JMenuItem("   - [IDF]");
        nav22.addActionListener((event) -> {
            new BackgroundWorkerNaiveBayesIDF(this).execute();
        });
        JMenuItem nav23 = new JMenuItem("   - [TFIDF]");
        nav23.addActionListener((event) -> {
            new BackgroundWorkerNaiveBayesTFIDF(this).execute();
        });

        JMenuItem cus2 = new JMenuItem("Sedlo Algorithm [Accuracy Test] [ALL]");
        cus2.addActionListener((event) -> {
            new BackgroundWorkerCustomALL(this).execute();
        });
        JMenuItem cus21 = new JMenuItem("   - [BOW]");
        cus21.addActionListener((event) -> {
            new BackgroundWorkerCustomBOW(this).execute();
        });
        JMenuItem cus22 = new JMenuItem("   - [IDF]");
        cus22.addActionListener((event) -> {
            new BackgroundWorkerCustomIDF(this).execute();
        });
        JMenuItem cus23 = new JMenuItem("   - [TFIDF]");
        cus23.addActionListener((event) -> {
            try {
                new BackgroundWorkerCustomTFIDF(this).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        options.add(nav);
        options.add(cus);
        options.addSeparator();
        options.add(nav2);
        options.add(nav21);
        options.add(nav22);
        options.add(nav23);
        options.add(cus2);
        options.add(cus21);
        options.add(cus22);
        options.add(cus23);

        menu.add(file);
        menu.add(ll);
        menu.add(options);
        menu.add(s);

    }

    /**
     * Load classes.
     *
     * @throws IOException the io exception
     */
    public void loadClasses() throws IOException {
        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setDialogTitle("Load CLASS File");
        FileFilter filter = new FileNameExtensionFilter("File with classes names .cn", "cn");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(filter);

        int returnVal = fc.showOpenDialog(root);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");
                classes =everything.split(",");
            } finally {
                br.close();
            }

            systemOutput.setText(systemOutput.getText()+"Classes File loaded: "+classes.length+"\n");
        }
    }

    /**
     * Load classes.
     *
     * @throws IOException the io exception
     */
    public void loadClassesReuters() throws IOException {
        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setDialogTitle("Load CLASS File");

        int returnVal = fc.showOpenDialog(root);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("","");
                classes =everything.split("\n");
            } finally {
                br.close();
            }

            systemOutput.setText(systemOutput.getText()+"Classes File loaded: "+classes.length+"\n");
        }
    }

    /**
     * Load classes.
     *
     * @param path     the path
     * @param splitter the splitter
     * @throws IOException the io exception
     */
    public void loadClasses(String path,String splitter) throws IOException {

            File file =new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");
                classes =everything.split(splitter);
            } finally {
                br.close();
            }

            systemOutput.setText(systemOutput.getText()+"Classes File loaded: "+classes.length+"\n");

    }

    /**
     * Load train files array list.
     *
     * @return the array list
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    public  ArrayList<File> loadTrainFiles() throws IOException, SAXException, ParserConfigurationException {
        ArrayList<File> trainData=new ArrayList<>();

        JFileChooser fc=new JFileChooser();
        fc.setDialogTitle("Load TRAIN Files");
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setMultiSelectionEnabled(true);
        FileFilter lab = new FileNameExtensionFilter("Text file with class name .lab", "lab");
        FileFilter sgm = new FileNameExtensionFilter("Read reuters file .sgm", "sgm");
        fc.setAcceptAllFileFilterUsed(false);

        fc.addChoosableFileFilter(sgm);
        fc.addChoosableFileFilter(lab);

        Boolean rSet=false;

        int returnVal = fc.showOpenDialog(root);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            for (File file:fc.getSelectedFiles())
            {
                if (lab.accept(file))
                {
                    if (!rSet)
                    {
                        loadClasses();
                        rSet=true;
                    }
                    trainData.add(file);
                }
                else if (sgm.accept(file))
                {

                    if (!rSet)
                    {
                        loadClassesReuters();
                        rSet=true;
                    }
                    if (classes!=null)
                        trainData.addAll(Text.convertSgm(file,classes));

                }
            }

            systemOutput.setText(systemOutput.getText()+"Train File loaded: "+trainData.size()+"\n");
        }

        return trainData;

    }

    /**
     * Load train files array list.
     *
     * @param path the path
     * @return the array list
     */
    public  ArrayList<File> loadTrainFiles(String path)
    {
        ArrayList<File> trainData=new ArrayList<>();

            trainData.addAll(Arrays.asList(new File(path).listFiles()));

            systemOutput.setText(systemOutput.getText()+"Train File loaded: "+trainData.size()+"\n");

        return trainData;

    }

    /**
     * Load test files array list.
     *
     * @return the array list
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException                 the sax exception
     */
    public ArrayList<File> loadTestFiles() throws IOException, ParserConfigurationException, SAXException {

        ArrayList<File>testData=new ArrayList<>();

        JFileChooser fc=new JFileChooser();
        fc.setDialogTitle("Load TEST Files");
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setMultiSelectionEnabled(true);
        FileFilter lab = new FileNameExtensionFilter("Text file with class name .lab", "lab");
        FileFilter sgm = new FileNameExtensionFilter("Read reuters file .sgm", "sgm");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(lab);
        fc.addChoosableFileFilter(sgm);

        int returnVal = fc.showOpenDialog(root);

        boolean rSet=false;


        if (returnVal == JFileChooser.APPROVE_OPTION) {
            for (File file:fc.getSelectedFiles())
            {
                if (lab.accept(file))
                {
                    testData.add(file);
                }
                else if (sgm.accept(file))
                {

                    if (!rSet)
                    {
                        loadClassesReuters();
                        rSet=true;
                    }
                    if (classes!=null)
                        testData.addAll(Text.convertSgm(file,classes));

                }
            }

            systemOutput.setText(systemOutput.getText()+"Test File loaded: "+testData.size()+"\n");
        }

        return testData;


    }

    /**
     * Load test files array list.
     *
     * @param path the path
     * @return the array list
     */
    public ArrayList<File> loadTestFiles(String path)  {

        ArrayList<File>testData=new ArrayList<>();

        File file =new File(path);

            testData.addAll(Arrays.asList(file.listFiles()));

            systemOutput.setText(systemOutput.getText()+"Test Files loaded: "+testData.size()+"\n");


        return testData;


    }

    /**
     * Generate BackgroundWorkerBOW.
     *
     * @throws IOException the io exception
     */
    public void generateBOW() throws IOException
    {



                if (trainedData !=null)
                    {
                        naiveBayesBOW = new NaiveBayesBOWBuilder().setTrainedClasses(IO.loadBagOfWords(trainedData, classes,progressBar1)).createNaiveBayesBOW();

                        customBOW = new CustomBOWBuilder().setTrainedClasses(IO.loadBagOfWords(trainedData, classes,progressBar1)).createCustomBOW();
                    }
                    else
                    {


                        systemOutput.setText(systemOutput.getText()+"Bag of Word NOT Created"+"\n");
                        return;
                    }

                    systemOutput.setText(systemOutput.getText()+"Bag of Word Created"+"\n");

    }

    /**
     * Generate BackgroundWorkerIDF.
     *
     * @throws IOException the io exception
     */
    public void generateIDF() throws IOException {

        if (trainedData !=null)
        {
        naiveBayesIDF= new NaiveBayesIDFBuilder().setTrainedClasses(IO.loadIDF(trainedData, classes,progressBar1)).createNaiveBayesIDF();
        customIDF = new CustomIDFBuilder().setTrainedClasses(IO.loadIDF(trainedData, classes,progressBar1)).createCustomIDF();
    }
                    else
    {


        systemOutput.setText(systemOutput.getText()+"Bag of Word NOT Created"+"\n");
        return;
    }

        systemOutput.setText(systemOutput.getText()+"IDF Created"+"\n");
    }

    /**
     * Generate BackgroundWorkerTFIDF.
     *
     * @throws IOException the io exception
     */
    public void generateTFIDF() throws IOException {

        if (trainedData !=null)
        {
        naiveBayesTFIDF= new NaiveBayesTFIDFBuilder().setTrainedClasses(IO.loadTFIDF(trainedData, classes, progressBar1)).createNaiveBayesTFIDF();
        customTFIDF = new CustomTFIDFBuilder().setTrainData(IO.loadTFIDF(trainedData, classes,progressBar1)).createCustomTFIDF();
        }
                    else
                            {

                            root.setCursor(Cursor.getDefaultCursor());

                            systemOutput.setText(systemOutput.getText()+"Bag of Word NOT Created"+"\n");
                            return;
                            }

        systemOutput.setText(systemOutput.getText()+"TF-IDF Created"+"\n");
    }

    /**
     * Custom execute.
     */
    public void customExecute()
    {
        if (customBOW !=null)
            infoPanelBOW.setText(customBOW.getStringOutput(inputTextBox.getText()));
        else
            infoPanelBOW.setText("NOT LEARNED YET");

        if (customIDF !=null)
            infoPanelIDF.setText(customIDF.getStringOutput(inputTextBox.getText()));
        else
            infoPanelIDF.setText("NOT LEARNED YET");

        if (customTFIDF !=null)
            infoPanelTFIDF.setText(customTFIDF.getStringOutput(inputTextBox.getText()));
        else
            infoPanelTFIDF.setText("NOT LEARNED YET");


    }

    /**
     * Naive bayes execute.
     */
    public void naiveBayesExecute()
    {
        if (naiveBayesBOW !=null)
            infoPanelBOW.setText(naiveBayesBOW.getStringOutput(inputTextBox.getText()));
        else
            infoPanelBOW.setText("NOT LEARNED YET");

        if (naiveBayesIDF!=null)
            infoPanelIDF.setText(naiveBayesIDF.getStringOutput(inputTextBox.getText()));
        else
            infoPanelIDF.setText("NOT LEARNED YET");

        if (naiveBayesTFIDF!=null)
            infoPanelTFIDF.setText(naiveBayesTFIDF.getStringOutput(inputTextBox.getText()));
        else
            infoPanelTFIDF.setText("NOT LEARNED YET");
    }

    /**
     * Custom accuracy execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void customAccuracyExecute(ArrayList<File> testData) throws IOException {


        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());
        double x=0;
        double z=0;
        double y=0;

        StringBuilder x1= new StringBuilder();
        StringBuilder z1= new StringBuilder();
        StringBuilder y1= new StringBuilder();

        for (File file:testData)
        {

            if (customBOW !=null)
            {
               x+=customBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
               x1.append(" -->").append(Math.round(customBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append("[%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }

            if (customIDF !=null)
            {
                y+=customIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                y1.append(" -->").append(Math.round(customIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append("[%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }

            if (customTFIDF !=null)
            {
                z+=customTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                z1.append(" -->").append(Math.round(customTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append("[%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }


            progressBar1.setString(file.getName());
            progressBar1.setValue(progressBar1.getValue()+1);

        }

        if (customBOW!=null)
            x1.insert(0, "Custom BOW Total AC= " + Math.round(x/(double)testData.size()) + "[%]" + "\n");
        if (customIDF!=null)
            y1.insert(0, "Custom IDF Total AC= " + Math.round(y/(double)testData.size()) + "[%]" + "\n");
        if (customTFIDF!=null)
            z1.insert(0, "Custom TFIDF Total AC= " + Math.round(z/(double)testData.size()) + "[%]" + "\n");

        infoPanelBOW.setText(x1.toString());
        infoPanelIDF.setText(y1.toString());
        infoPanelTFIDF.setText(z1.toString());

    }

    /**
     * Custom accuracy BackgroundWorkerBOW execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void customAccuracyBOWExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());
        double x=0;


        StringBuilder x1= new StringBuilder();

        for (File file:testData)
        {


            if (customBOW !=null)
            {
                x+=customBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                x1.append(" -->").append(Math.round(customBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append("[%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }


            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());
        }

        if (customBOW!=null)
            x1.insert(0, "Custom BOW Total AC= " + Math.round(x/(double)testData.size()) + "[%]" + "\n");


        infoPanelBOW.setText(x1.toString());


    }

    /**
     * Custom accuracy BackgroundWorkerIDF execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void customAccuracyIDFExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());

        double y=0;


        StringBuilder y1= new StringBuilder();

        for (File file:testData)
        {




            if (customIDF !=null)
            {
                y+=customIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                y1.append(" -->").append(Math.round(customIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelIDF.setText("NOT LEARNED YET");
            }




            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());
        }


        if (customIDF!=null)
            y1.insert(0, "Custom IDF Total AC= " + Math.round(y/(double)testData.size()) + "[%]" + "\n");



        infoPanelIDF.setText(y1.toString());


    }

    /**
     * Custom accuracy BackgroundWorkerTFIDF execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void customAccuracyTFIDFExecute(ArrayList<File> testData) throws IOException {


        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());

        double z=0;

        StringBuilder z1= new StringBuilder();

        for (File file:testData)
        {

            if (customTFIDF !=null)
            {
                z+=customTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                z1.append(" -->").append(Math.round(customTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelTFIDF.setText("NOT LEARNED YET");
            }



            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());
        }


        if (customTFIDF!=null)
            z1.insert(0, "Custom TFIDF Total AC= " + Math.round(z/(double) testData.size()) + "[%]" + "\n");

        infoPanelTFIDF.setText(z1.toString());

    }

    /**
     * Naive bayes accuracy execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void naiveBayesAccuracyExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());
        double x=0;
        double z=0;
        double y=0;

        StringBuilder x1= new StringBuilder();
        StringBuilder z1= new StringBuilder();
        StringBuilder y1= new StringBuilder();

        for (File file:testData)
        {


            if (naiveBayesBOW !=null)
            {
                x+=naiveBayesBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                x1.append(" -->").append(Math.round(naiveBayesBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }

            if (naiveBayesIDF !=null)
            {
                y+=naiveBayesIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                y1.append(" -->").append(Math.round(naiveBayesIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }

            if (naiveBayesTFIDF !=null)
            {
                z+=naiveBayesTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                z1.append(" -->").append(Math.round(naiveBayesTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }



            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());

        }

        if (naiveBayesBOW!=null)
            x1.insert(0, "BOW Total AC= " + Math.round(x/(double)testData.size()) + "[%]" + "\n");
        if (naiveBayesIDF!=null)
            y1.insert(0, "IDF Total AC= " + Math.round(y/(double)testData.size()) + "[%]" + "\n");
        if (naiveBayesTFIDF!=null)
            z1.insert(0, "TFIDF Total AC= " + Math.round(z/(double)testData.size()) + "[%]" + "\n");

        infoPanelBOW.setText(x1.toString());
        infoPanelIDF.setText(y1.toString());
        infoPanelTFIDF.setText(z1.toString());
    }

    /**
     * Naive bayes accuracy BackgroundWorkerBOW execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void naiveBayesAccuracyBOWExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());
        double x=0;

        StringBuilder x1= new StringBuilder();

        for (File file:testData)
        {


            if (naiveBayesBOW !=null)
            {
                x+=naiveBayesBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                x1.append(" -->").append(Math.round(naiveBayesBOW.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelBOW.setText("NOT LEARNED YET");
            }




            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());
        }

        if (naiveBayesBOW!=null)
            x1.insert(0, "BOW Total AC= " + Math.round(x/(double)testData.size()) + "[%]" + "\n");

        infoPanelBOW.setText(x1.toString());
    }

    /**
     * Naive bayes accuracy BackgroundWorkerIDF execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void naiveBayesAccuracyIDFExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());
        double y=0;


        StringBuilder y1= new StringBuilder();

        for (File file:testData)
        {

            if (naiveBayesIDF !=null)
            {
                y+=naiveBayesIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                y1.append(" -->").append(Math.round(naiveBayesIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelIDF.setText("NOT LEARNED YET");
            }



            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());

        }


        if (naiveBayesIDF!=null)
            y1.insert(0, "IDF Total AC= " + Math.round(y/(double)testData.size()) + "[%]" + "\n");



        infoPanelIDF.setText(y1.toString());

    }

    /**
     * Naive bayes accuracy BackgroundWorkerTFIDF execute.
     *
     * @param testData the test data
     * @throws IOException the io exception
     */
    public void naiveBayesAccuracyTFIDFExecute(ArrayList<File> testData) throws IOException {

        progressBar1.setValue(0);
        progressBar1.setMaximum(testData.size());

        double z=0;



        StringBuilder z1= new StringBuilder();


        for (File file:testData)
        {


            if (naiveBayesTFIDF !=null)
            {
                z+=naiveBayesTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath())));
                z1.append(" -->").append(Math.round(naiveBayesTFIDF.getAccuracyWithLabFormat(Files.readString(Paths.get(file.getPath()))))).append(" [%]").append(" \n");
            }
            else
            {
                infoPanelTFIDF.setText("NOT LEARNED YET");
            }



            progressBar1.setValue(progressBar1.getValue()+1);
            progressBar1.setString(file.getName());

        }


        if (naiveBayesTFIDF!=null)
            z1.insert(0, "TFIDF Total AC= " + Math.round(z/(double)testData.size()) + "[%]" + "\n");


        infoPanelTFIDF.setText(z1.toString());
    }

    /**
     * Save BackgroundWorkerBOW.
     *
     * @throws IOException the io exception
     */
    public void saveBOW() throws IOException {
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        chooser.setDialogTitle("Save BOW");
        chooser.setApproveButtonText("save");
        int returnVal =chooser.showOpenDialog(root);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        try(FileWriter writer = new FileWriter(chooser.getSelectedFile()+".bow"))  {
            for (BagOfWords bagOfWords: naiveBayesBOW.getTrainedClasses())
            {
             writer.write(bagOfWords.getName()+"-----b-----"+"\n");

             for(HashMap.Entry<String, Integer> entry : bagOfWords.getBagHashMap().entrySet())
                {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    writer.write(key+"-----k-----"+value+"-----v-----"+"\n");
                }

                writer.write("-----n-----"+"\n");
            }

        }

    }

    /**
     * Save BackgroundWorkerIDF.
     *
     * @throws IOException the io exception
     */
    public void saveIDF() throws IOException {
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        chooser.setDialogTitle("Save IDF");
        chooser.setApproveButtonText("save");
        int returnVal =chooser.showOpenDialog(root);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        try(FileWriter writer = new FileWriter(chooser.getSelectedFile()+".idf"))
        {
        for (IDF bagOfWords:naiveBayesIDF.getTrainedClasses())
            {
                writer.write(bagOfWords.getName()+"-----b-----"+"\n");

              for (Word w:bagOfWords.getWordsSets().getWordList())

                    writer.write(w.getValue()+"-----k-----"+w.getFreq()+"-----v-----"+"\n");

                writer.write("-----n-----"+"\n");
            }
        }

    }

    /**
     * Save BackgroundWorkerTFIDF.
     *
     * @throws IOException the io exception
     */
    public void saveTFIDF() throws IOException {

        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        chooser.setDialogTitle("Save TFIDF");
        chooser.setApproveButtonText("save");
        int returnVal =chooser.showOpenDialog(root);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        try(FileWriter writer = new FileWriter(chooser.getSelectedFile()+".tfidf"))
        {
            for (TFIDF bagOfWords:naiveBayesTFIDF.getTrainedClasses())
            {
                writer.write(bagOfWords.getName()+"-----b-----"+"\n");

                for (Word w:bagOfWords.getWordsSets().getWordList())

                    writer.write(w.getValue()+"-----k-----"+w.getFreq()+"-----v-----"+"\n");

                writer.write("-----n-----"+"\n");
            }

        }

    }

    /**
     * Save BackgroundWorkerBOW.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void saveBOW(String path) throws IOException {

            try(FileWriter writer = new FileWriter(path+".bow"))  {
                for (BagOfWords bagOfWords: naiveBayesBOW.getTrainedClasses())
                {
                    writer.write(bagOfWords.getName()+"-----b-----"+"\n");

                    for(HashMap.Entry<String, Integer> entry : bagOfWords.getBagHashMap().entrySet())
                    {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        writer.write(key+"-----k-----"+value+"-----v-----"+"\n");
                    }

                    writer.write("-----n-----"+"\n");
                }

            }

    }

    /**
     * Save BackgroundWorkerIDF.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void saveIDF(String path) throws IOException {

            try(FileWriter writer = new FileWriter(path+".idf"))
            {
                for (IDF bagOfWords:naiveBayesIDF.getTrainedClasses())
                {
                    writer.write(bagOfWords.getName()+"-----b-----"+"\n");

                    for (Word w:bagOfWords.getWordsSets().getWordList())

                        writer.write(w.getValue()+"-----k-----"+w.getFreq()+"-----v-----"+"\n");

                    writer.write("-----n-----"+"\n");
                }
            }

    }

    /**
     * Save BackgroundWorkerTFIDF.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void saveTFIDF(String path) throws IOException {

            try(FileWriter writer = new FileWriter(path+".tfidf"))
            {
                for (TFIDF bagOfWords:naiveBayesTFIDF.getTrainedClasses())
                {
                    writer.write(bagOfWords.getName()+"-----b-----"+"\n");

                    for (Word w:bagOfWords.getWordsSets().getWordList())

                        writer.write(w.getValue()+"-----k-----"+w.getFreq()+"-----v-----"+"\n");

                    writer.write("-----n-----"+"\n");
                }

            }

    }

    /**
     * Load BackgroundWorkerBOW.
     *
     * @throws IOException the io exception
     */
    public void loadBOW() throws IOException
    {

        ArrayList<BagOfWords>bagOfWords=new ArrayList<>();

        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setDialogTitle("Load BOW");
        FileFilter filter = new FileNameExtensionFilter("File with bag of words .bow", "bow");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(filter);
        int returnVal = fc.showOpenDialog(root);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    BagOfWords bag =new BagOfWords(s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                    for (String tt:s.split("-----b-----")[1].split("[v]"))
                    {
                        if (tt.split("-----k-----").length==2)
                        bag.getBagHashMap().put(tt.split("-----k-----")[0], Integer.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", ""))) ;
                    }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesBOW = new NaiveBayesBOWBuilder().setTrainedClasses(bagOfWords).createNaiveBayesBOW();
            customBOW = new CustomBOWBuilder().setTrainedClasses(bagOfWords).createCustomBOW();

            systemOutput.setText(systemOutput.getText()+"Bag File loaded: "+bagOfWords.size()+"\n");
        }

    }

    /**
     * Load dif.
     *
     * @throws IOException the io exception
     */
    public void loadDIF() throws IOException
    {

        ArrayList<IDF>bagOfWords=new ArrayList<>();

        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        fc.setDialogTitle("Load IDF");
        FileFilter filter = new FileNameExtensionFilter("File with IDF .idf", "idf");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(filter);
        int returnVal = fc.showOpenDialog(root);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    IDF bag =new IDF(new ArrayList<>(),s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                        for (String tt:s.split("-----b-----")[1].split("[v]"))
                        {
                            if (tt.split("-----k-----").length==2)
                                bag.getWordsSets().getWordList().add(new Word(tt.split("-----k-----")[0], Double.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", "")))) ;
                        }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesIDF = new NaiveBayesIDFBuilder().setTrainedClasses(bagOfWords).createNaiveBayesIDF();
            customIDF = new CustomIDFBuilder().setTrainedClasses(bagOfWords).createCustomIDF();

            systemOutput.setText(systemOutput.getText()+"IDF File loaded: "+bagOfWords.size()+"\n");
        }

    }

    /**
     * Load BackgroundWorkerTFIDF.
     *
     * @throws IOException the io exception
     */
    public void loadTFIDF() throws IOException
    {

        ArrayList<TFIDF>bagOfWords=new ArrayList<>();

        JFileChooser fc=new JFileChooser();
        fc.setDialogTitle("Load TFIDF");
        fc.setCurrentDirectory(new File(String.valueOf(Paths.get("."))));
        FileFilter filter = new FileNameExtensionFilter("File witch TF-IDF .tfidf", "tfidf");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(filter);
        int returnVal = fc.showOpenDialog(root);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    TFIDF bag =new TFIDF(new ArrayList<>(),s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                        for (String tt:s.split("-----b-----")[1].split("[v]"))
                        {
                            if (tt.split("-----k-----").length==2)
                                bag.getWordsSets().getWordList().add(new Word(tt.split("-----k-----")[0], Double.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", "")))) ;
                        }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesTFIDF = new NaiveBayesTFIDFBuilder().setTrainedClasses(bagOfWords).createNaiveBayesTFIDF();
            customTFIDF = new CustomTFIDFBuilder().setTrainData(bagOfWords).createCustomTFIDF();

            systemOutput.setText(systemOutput.getText()+"IDF File loaded: "+bagOfWords.size()+"\n");
        }

    }

    /**
     * Load BackgroundWorkerBOW.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void loadBOW(String path) throws IOException
    {

        ArrayList<BagOfWords>bagOfWords=new ArrayList<>();

        File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    BagOfWords bag =new BagOfWords(s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                        for (String tt:s.split("-----b-----")[1].split("[v]"))
                        {
                            if (tt.split("-----k-----").length==2)
                                bag.getBagHashMap().put(tt.split("-----k-----")[0], Integer.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", ""))) ;
                        }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesBOW = new NaiveBayesBOWBuilder().setTrainedClasses(bagOfWords).createNaiveBayesBOW();
            customBOW = new CustomBOWBuilder().setTrainedClasses(bagOfWords).createCustomBOW();

            systemOutput.setText(systemOutput.getText()+"Bag File loaded: "+bagOfWords.size()+"\n");


    }

    /**
     * Load dif.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void loadDIF(String path) throws IOException
    {

        ArrayList<IDF>bagOfWords=new ArrayList<>();


            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    IDF bag =new IDF(new ArrayList<>(),s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                        for (String tt:s.split("-----b-----")[1].split("[v]"))
                        {
                            if (tt.split("-----k-----").length==2)
                                bag.getWordsSets().getWordList().add(new Word(tt.split("-----k-----")[0], Double.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", "")))) ;
                        }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesIDF = new NaiveBayesIDFBuilder().setTrainedClasses(bagOfWords).createNaiveBayesIDF();
            customIDF = new CustomIDFBuilder().setTrainedClasses(bagOfWords).createCustomIDF();

            systemOutput.setText(systemOutput.getText()+"IDF File loaded: "+bagOfWords.size()+"\n");


    }

    /**
     * Load BackgroundWorkerTFIDF.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    public void loadTFIDF(String path) throws IOException
    {

            ArrayList<TFIDF>bagOfWords=new ArrayList<>();

            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString().replace("\n","");

                String[] cc = everything.split("-----n-----");

                for (String s:cc)
                {
                    TFIDF bag =new TFIDF(new ArrayList<>(),s.split("-----b-----")[0]);

                    if (s.split("-----b-----").length==2)
                        for (String tt:s.split("-----b-----")[1].split("[v]"))
                        {
                            if (tt.split("-----k-----").length==2)
                                bag.getWordsSets().getWordList().add(new Word(tt.split("-----k-----")[0], Double.valueOf(tt.split("-----k-----")[1].replaceAll("[^\\d.]", "")))) ;
                        }

                    bagOfWords.add(bag);

                }

            }
            finally
            {
                br.close();
            }


            naiveBayesTFIDF = new NaiveBayesTFIDFBuilder().setTrainedClasses(bagOfWords).createNaiveBayesTFIDF();
            customTFIDF = new CustomTFIDFBuilder().setTrainData(bagOfWords).createCustomTFIDF();

            systemOutput.setText(systemOutput.getText()+"IDF File loaded: "+bagOfWords.size()+"\n");

    }


    /**
     * Gets trained data.
     *
     * @return the trained data
     */
    public  ArrayList<File> getTrainedData() {
        return trainedData;
    }

    /**
     * Sets trained data.
     *
     * @param trainedData the trained data
     */
    public void setTrainedData(ArrayList<File> trainedData) {
        this.trainedData = trainedData;
    }

    /**
     * Get classes string [ ].
     *
     * @return the string [ ]
     */
    public String[] getClasses() {
        return classes;
    }

    /**
     * Sets classes.
     *
     * @param classes the classes
     */
    public void setClasses(String[] classes) {
        this.classes = classes;
    }

    /**
     * Gets root.
     *
     * @return the root
     */
    public JPanel getRoot() {
        return root;
    }

    /**
     * Sets root.
     *
     * @param root the root
     */
    public void setRoot(JPanel root) {
        this.root = root;
    }

    /**
     * Gets input text box.
     *
     * @return the input text box
     */
    public JEditorPane getInputTextBox() {
        return inputTextBox;
    }

    /**
     * Sets input text box.
     *
     * @param inputTextBox the input text box
     */
    public void setInputTextBox(JEditorPane inputTextBox) {
        this.inputTextBox = inputTextBox;
    }

    /**
     * Gets info panel 1.
     *
     * @return the info panel 1
     */
    public JTextPane getInfoPanelBOW() {
        return infoPanelBOW;
    }

    /**
     * Sets info panel 1.
     *
     * @param infoPanelBOW the info panel 1
     */
    public void setInfoPanelBOW(JTextPane infoPanelBOW) {
        this.infoPanelBOW = infoPanelBOW;
    }

    /**
     * Gets menu.
     *
     * @return the menu
     */
    public JMenuBar getMenu() {
        return menu;
    }

    /**
     * Sets menu.
     *
     * @param menu the menu
     */
    public void setMenu(JMenuBar menu) {
        this.menu = menu;
    }

    /**
     * Gets info panel 2.
     *
     * @return the info panel 2
     */
    public JTextPane getInfoPanelIDF() {
        return infoPanelIDF;
    }

    /**
     * Sets info panel 2.
     *
     * @param infoPanelIDF the info panel 2
     */
    public void setInfoPanelIDF(JTextPane infoPanelIDF) {
        this.infoPanelIDF = infoPanelIDF;
    }

    /**
     * Gets info panel 3.
     *
     * @return the info panel 3
     */
    public JTextPane getInfoPanelTFIDF() {
        return infoPanelTFIDF;
    }

    /**
     * Sets info panel 3.
     *
     * @param infoPanelTFIDF the info panel 3
     */
    public void setInfoPanelTFIDF(JTextPane infoPanelTFIDF) {
        this.infoPanelTFIDF = infoPanelTFIDF;
    }

    /**
     * Gets system output.
     *
     * @return the system output
     */
    public JTextPane getSystemOutput() {
        return systemOutput;
    }

    /**
     * Sets system output.
     *
     * @param systemOutput the system output
     */
    public void setSystemOutput(JTextPane systemOutput) {
        this.systemOutput = systemOutput;
    }

    /**
     * Gets progress bar 1.
     *
     * @return the progress bar 1
     */
    public JProgressBar getProgressBar1() {
        return progressBar1;
    }

    /**
     * Sets progress bar 1.
     *
     * @param progressBar1 the progress bar 1
     */
    public void setProgressBar1(JProgressBar progressBar1) {
        this.progressBar1 = progressBar1;
    }

    /**
     * Gets continues bayes check box.
     *
     * @return the continues bayes check box
     */
    public Boolean getContinuesBayesCheckBox() {
        return continuesBayesCheckBox;
    }

    /**
     * Sets continues bayes check box.
     *
     * @param continuesBayesCheckBox the continues bayes check box
     */
    public void setContinuesBayesCheckBox(Boolean continuesBayesCheckBox) {
        this.continuesBayesCheckBox = continuesBayesCheckBox;
    }

    /**
     * Gets custom BackgroundWorkerTFIDF.
     *
     * @return the custom BackgroundWorkerTFIDF
     */
    public CustomTFIDF getCustomTFIDF() {
        return customTFIDF;
    }

    /**
     * Sets custom BackgroundWorkerTFIDF.
     *
     * @param customTFIDF the custom BackgroundWorkerTFIDF
     */
    public void setCustomTFIDF(CustomTFIDF customTFIDF) {
        this.customTFIDF = customTFIDF;
    }

    /**
     * Gets custom BackgroundWorkerIDF.
     *
     * @return the custom BackgroundWorkerIDF
     */
    public CustomIDF getCustomIDF() {
        return customIDF;
    }

    /**
     * Sets custom BackgroundWorkerIDF.
     *
     * @param customIDF the custom BackgroundWorkerIDF
     */
    public void setCustomIDF(CustomIDF customIDF) {
        this.customIDF = customIDF;
    }

    /**
     * Gets custom BackgroundWorkerBOW.
     *
     * @return the custom BackgroundWorkerBOW
     */
    public CustomBOW getCustomBOW() {
        return customBOW;
    }

    /**
     * Sets custom BackgroundWorkerBOW.
     *
     * @param customBOW the custom BackgroundWorkerBOW
     */
    public void setCustomBOW(CustomBOW customBOW) {
        this.customBOW = customBOW;
    }

    /**
     * Gets naive bayes BackgroundWorkerIDF.
     *
     * @return the naive bayes BackgroundWorkerIDF
     */
    public NaiveBayesIDF getNaiveBayesIDF() {
        return naiveBayesIDF;
    }

    /**
     * Sets naive bayes BackgroundWorkerIDF.
     *
     * @param naiveBayesIDF the naive bayes BackgroundWorkerIDF
     */
    public void setNaiveBayesIDF(NaiveBayesIDF naiveBayesIDF) {
        this.naiveBayesIDF = naiveBayesIDF;
    }

    /**
     * Gets naive bayes BackgroundWorkerTFIDF.
     *
     * @return the naive bayes BackgroundWorkerTFIDF
     */
    public NaiveBayesTFIDF getNaiveBayesTFIDF() {
        return naiveBayesTFIDF;
    }

    /**
     * Sets naive bayes BackgroundWorkerTFIDF.
     *
     * @param naiveBayesTFIDF the naive bayes BackgroundWorkerTFIDF
     */
    public void setNaiveBayesTFIDF(NaiveBayesTFIDF naiveBayesTFIDF) {
        this.naiveBayesTFIDF = naiveBayesTFIDF;
    }

    /**
     * Gets naive bayes BackgroundWorkerBOW.
     *
     * @return the naive bayes BackgroundWorkerBOW
     */
    public NaiveBayesBOW getNaiveBayesBOW() {
        return naiveBayesBOW;
    }

    /**
     * Sets naive bayes BackgroundWorkerBOW.
     *
     * @param naiveBayesBOW the naive bayes BackgroundWorkerBOW
     */
    public void setNaiveBayesBOW(NaiveBayesBOW naiveBayesBOW) {
        this.naiveBayesBOW = naiveBayesBOW;
    }

    /**
     * Close.
     */
    public void close()
    {
        System.exit(0);
    }
}

