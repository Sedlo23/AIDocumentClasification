package Classification.Algorithms;


import Classification.Interfaces.ILearningAlgorithm;
import Parametrization.BagOfWorld.BagOfWords;
import Parametrization.IDF.Word;
import Parametrization.TFIDF.TFIDF;
import Tools.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The type Custom tfidf.
 */
public class CustomTFIDF implements ILearningAlgorithm
{

    private final ArrayList<TFIDF> trainedClasses;

    /**
     * Instantiates a new Custom tfidf.
     *
     * @param trainData the train data
     */
    public CustomTFIDF(ArrayList<TFIDF> trainData)
    {
        this.trainedClasses = trainData;
    }

    /**
     * Gets string output.
     *
     * @param input the input
     * @return the string output
     */
    public String getStringOutput(String input)
    {

        resetWeight();

        BagOfWords bag =new BagOfWords(" ");


        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }

        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (TFIDF bag1: trainedClasses)
            {
                for (Word w:bag1.getWordsSets().getWordList())
                {
                    if (w.getValue().equals(key))
                    {


                        bag1.setWeight(bag1.getWeight()+Math.pow(2,w.getFreq()));

                        bag1.setFileCount(bag1.getFileCount()+1);

                    }
                }

            }

        }

        for (TFIDF bag1: trainedClasses)
        {
            bag1.setWeight(bag1.getWeight()/(bag1.getFileCount()+1));
        }



        trainedClasses.sort(new Comparator<TFIDF>() {
            @Override
            public int compare(TFIDF idf, TFIDF t1) {
                if (idf.getWeight()>t1.getWeight())
                        return -1;
                    else
                        return 1;
            }
        });

        String[] output=new String[trainedClasses.size()];

        for (int i = 0; i< trainedClasses.size(); i++)
        {
            output[i]= trainedClasses.get(i).getName();
        }

        return Text.formatOutput(output,this.getClass().getSimpleName());
    }

    /**
     * Gets accuracy with lab format.
     *
     * @param input the input
     * @return the accuracy with lab format
     */
    public double getAccuracyWithLabFormat(String input)
    {

        resetWeight();

        BagOfWords bag =new BagOfWords(" ");

        String tt=input;

        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }



        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (TFIDF bag1: trainedClasses)
            {
                for (Word w:bag1.getWordsSets().getWordList())
                {
                    if (w.getValue().equals(key))
                    {
                        bag1.setWeight(bag1.getWeight()+w.getFreq());
                        bag1.setFileCount(bag1.getFileCount()+1);
                    }
                }

            }

        }

        for (TFIDF bag1: trainedClasses)
        {
            bag1.setWeight(bag1.getWeight()/(bag1.getFileCount()+1));
        }

        int i= trainedClasses.size();

        for (TFIDF bag1: trainedClasses)
        {
            if (tt.split("\n")[0].contains(bag1.getName()))
            {
                i=Math.min(i, trainedClasses.indexOf(bag1));
            }

        }

        StringBuilder ret= new StringBuilder("(" + i + ")" + "\n");

        trainedClasses.sort(new Comparator<TFIDF>() {
            @Override
            public int compare(TFIDF idf, TFIDF t1) {
                if (idf.getWeight()>t1.getWeight())
                    return -1;
                else
                    return 1;
            }
        });

        for (TFIDF bag1: trainedClasses)
        {
            ret.append("  --> ").append(bag1.getName()).append(": ").append(bag1.getWeight()).append("\n");
        }

        return (100/Math.pow(i+1,2));

    }

    /**
     * Reset weight.
     */
    public void resetWeight()
    {
        for (TFIDF idf: trainedClasses)
        {
            idf.setFileCount(0);
            idf.setWeight(0);
        }

    }



}
