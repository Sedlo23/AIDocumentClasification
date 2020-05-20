package Tools;

import Parametrization.BagOfWords;
import Parametrization.IDF.IDF;
import Parametrization.TFIDF.TFIDF;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * The type Io.
 */
public class IO
{

    /**
     * Load bag of words array list.
     *
     * @param trainData the train data
     * @param classes   the classes
     * @return the array list
     * @throws IOException the io exception
     */
    public static ArrayList<BagOfWords> loadBagOfWords(ArrayList<File> trainData, String[] classes, JProgressBar progressBar) throws IOException {
        ArrayList<BagOfWords> bags=new ArrayList<>();
        progressBar.setMaximum(trainData.size());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        for (String name:classes)
        {
            bags.add(new BagOfWords(name));
        }


        for (File file:trainData)
        {
            progressBar.setValue(progressBar.getValue()+1);
            progressBar.setString(file.getName());

            String str= Files.readString(Paths.get(file.getPath()));

            String cl= str.split("\n")[0];


            String ad= Text.format(str);

            for (String rr:cl.split(" "))
            {
                for (BagOfWords bb:bags)
                {
                    if (bb.getName().equals(rr))
                        for (String s:ad.split(" "))
                        {
                            if (s.length()>2)
                                bb.add(s);
                        }

                }

            }

        }


        return bags;
    }

    /**
     * Load idf array list.
     *
     * @param trainData the train data
     * @param classes   the classes
     * @return the array list
     * @throws IOException the io exception
     */
    public static ArrayList<IDF> loadIDF(ArrayList<File> trainData, String[] classes, JProgressBar progressBar) throws IOException {
        progressBar.setMaximum(trainData.size());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);


        ArrayList<ArrayList<BagOfWords>> bags= new ArrayList<>();

        for (String s :classes)
        {
            bags.add(new ArrayList<>());
        }


        for (File file:trainData)
        {
            progressBar.setValue(progressBar.getValue()+1);
            progressBar.setString(file.getName());
            String str= Files.readString(Paths.get(file.getPath()));


            String cl= str.split("\n")[0];

            String ad= Text.format(str);

            for (String rr:cl.split(" "))
            {

                for (int i=0;i<classes.length;i++)
                    if (classes[i].equals(rr))
                    {
                        BagOfWords t =new BagOfWords();

                        for (String s:ad.split(" "))
                        {
                            if (s.length()>2)
                                t.add(s);
                        }

                        bags.get(i).add(t);
                    }

            }


        }

        ArrayList<IDF>idf=new ArrayList<>();

        progressBar.setMaximum(bags.size());
        progressBar.setValue(0);

        for (int i= 0;i<bags.size();i++)
        {
            idf.add(new IDF(bags.get(i),classes[i]));
            progressBar.setString(idf.get(idf.size()-1).getName());
            progressBar.setValue(progressBar.getValue()+1);
        }

        return idf;
    }

    /**
     * Load tfidf array list.
     *
     * @param trainData the train data
     * @param classes   the classes
     * @return the array list
     * @throws IOException the io exception
     */
    public static ArrayList<TFIDF> loadTFIDF(ArrayList<File> trainData, String[] classes, JProgressBar progressBar) throws IOException {
        progressBar.setMaximum(trainData.size());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        ArrayList<ArrayList<BagOfWords>> bags= new ArrayList<>();

        for (String s :classes)
        {
            bags.add(new ArrayList<>());
        }


        for (File file:trainData)
        {
            progressBar.setValue(progressBar.getValue()+1);
            progressBar.setString(file.getName());
            String str= Files.readString(Paths.get(file.getPath()));

            String cl= str.split("\n")[0];

            String ad= Text.format(str);

            for (String rr:cl.split(" "))
            {

                for (int i=0;i<classes.length;i++)
                    if (classes[i].equals(rr))
                    {
                        BagOfWords t =new BagOfWords();

                        for (String s:ad.split(" "))
                        {
                            if (s.length()>2)
                                t.add(s);
                        }

                        bags.get(i).add(t);
                    }

            }


        }

        ArrayList<TFIDF>idf=new ArrayList<>();

        progressBar.setMaximum(bags.size());
        progressBar.setValue(0);
        for (int i= 0;i<bags.size();i++)
        {

            idf.add(new TFIDF(bags.get(i),classes[i]));
            progressBar.setString(idf.get(idf.size()-1).getName());
            progressBar.setValue(progressBar.getValue()+1);
        }


        return idf;
    }
}
