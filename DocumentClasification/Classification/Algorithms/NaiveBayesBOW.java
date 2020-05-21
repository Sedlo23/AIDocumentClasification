package Classification.Algorithms;


import Classification.Interfaces.ILearningAlgorithm;
import Parametrization.BagOfWorld.BagOfWords;
import Tools.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The type Naive bayes bow.
 */
public class NaiveBayesBOW implements ILearningAlgorithm
{

    private final ArrayList<BagOfWords> trainedClasses;

    /**
     * Instantiates a new Naive bayes bow.
     *
     * @param trainedClasses the trained classes
     */
    public NaiveBayesBOW(ArrayList<BagOfWords> trainedClasses)
    {
        this.trainedClasses = trainedClasses;
    }

    /**
     * Gets string output.
     *
     * @param input the input
     * @return the string output
     */
    public String getStringOutput(String input)
    {

        BagOfWords bag =new BagOfWords(" ");


        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }

        for (BagOfWords bag1: trainedClasses)
        {
            bag1.setWeight(0);
        }

        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (BagOfWords bag1: trainedClasses)
            {
                if (bag1.countOccurrences(key)>0)
                {
                    double pX=bag.countOccurrences(key);

                    double pY=(bag.getBagHashMap().size());

                    double z=(pX/pY);

                    double pX2=bag1.countOccurrences(key);

                    double pY2=(bag1.getBagHashMap().size());

                    double z2=(pX2/pY2);

                    double c=z*z2;

                    bag1.setWeight( bag1.getWeight() + c);

                }

            }

        }



        trainedClasses.sort(new Comparator<BagOfWords>() {
            @Override
            public int compare(BagOfWords bagOfWords, BagOfWords t1) {

                if (bagOfWords.getWeight() < t1.getWeight())
                    return 1;
                else return -1;
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

        BagOfWords bag =new BagOfWords(" ");

        String tt=input;

        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }

        for (BagOfWords bag1: trainedClasses)
        {
            bag1.setWeight(0);
        }

        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (BagOfWords bag1: trainedClasses)
            {
                if (bag1.countOccurrences(key)>0)
                {
                    double pX=bag.countOccurrences(key);

                    double pY=(bag.getBagHashMap().size());

                    double z=(pX/pY);

                    double pX2=bag1.countOccurrences(key);

                    double pY2=(bag1.getBagHashMap().size());

                    double z2=(pX2/pY2);

                    double c=z*z2;

                    bag1.setWeight( bag1.getWeight() + c);

                }
            }

        }

        trainedClasses.sort(new Comparator<BagOfWords>() {
            @Override
            public int compare(BagOfWords bagOfWords, BagOfWords t1) {

                if (bagOfWords.getWeight() < t1.getWeight())
                    return 1;
                else return -1;
            }
        });

        int i= trainedClasses.size();

        for (BagOfWords bag1: trainedClasses)
        {
            if (tt.split("\n")[0].contains(bag1.getName()))
            {
                i=Math.min(i, trainedClasses.indexOf(bag1));
            }

        }

        return (100/Math.pow(i+1,2));
    }

    /**
     * Gets trained classes.
     *
     * @return the trained classes
     */
    public ArrayList<BagOfWords> getTrainedClasses() {
        return trainedClasses;
    }


}
