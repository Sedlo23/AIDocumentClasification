package Classification.Algorithms;


import Classification.Interfaces.ILearningAlgorithm;
import Parametrization.BagOfWorld.BagOfWords;
import Parametrization.IDF.IDF;
import Parametrization.IDF.Word;
import Tools.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The type Custom idf.
 */
public class CustomIDF implements ILearningAlgorithm
{

    private final ArrayList<IDF> trainedClasses;

    /**
     * Instantiates a new Custom idf.
     *
     * @param trainedClasses the trained classes
     */
    public CustomIDF(ArrayList<IDF> trainedClasses)
    {
        this.trainedClasses = trainedClasses;
    }

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

            for (IDF bag1: trainedClasses)
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

        for (IDF bag1: trainedClasses)
        {
            bag1.setWeight(bag1.getWeight()/(bag1.getFileCount()+1));
        }


        trainedClasses.sort(new Comparator<IDF>() {
            @Override
            public int compare(IDF idf, IDF t1) {
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

            for (IDF bag1: trainedClasses)
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

        for (IDF bag1: trainedClasses)
        {
            bag1.setWeight(bag1.getWeight()/(bag1.getFileCount()+1));
        }

        int i= trainedClasses.size();

        for (IDF bag1: trainedClasses)
        {
            if (tt.split("\n")[0].contains(bag1.getName()))
            {
                i=Math.min(i, trainedClasses.indexOf(bag1));
            }

        }

        StringBuilder ret= new StringBuilder("(" + i + ")" + "\n");

        trainedClasses.sort(new Comparator<IDF>() {
            @Override
            public int compare(IDF idf, IDF t1) {
                if (idf.getWeight()>t1.getWeight())
                    return -1;
                else
                    return 1;
            }
        });

        for (IDF bag1: trainedClasses)
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
        for (IDF idf: trainedClasses)
        {
            idf.setFileCount(0);
            idf.setWeight(0);
        }

    }

}
