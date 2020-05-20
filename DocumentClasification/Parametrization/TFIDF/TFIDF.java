package Parametrization.TFIDF;


import Parametrization.BagOfWords;
import Parametrization.IDF.Word;
import Parametrization.IDF.WordsSets;

import java.util.ArrayList;
import java.util.Map;

public class TFIDF
{

    private WordsSets wordsSets;

    private ArrayList<BagOfWords>bagsOfWords;

    private String name;

    private  double weight;

    private  double fileCount;


    public TFIDF(ArrayList<BagOfWords> bagsOfWords, String name)
    {
        this.setFileCount(0);

        this.setWeight(0);

        this.bagsOfWords=bagsOfWords;

        BagOfWords all=new BagOfWords();

        for (BagOfWords tt :bagsOfWords)
        {
            for (Map.Entry entry:tt.getBagHashMap().entrySet())
            {
                all.add((String) entry.getKey());
            }
        }

        this.name=name;

        wordsSets= new WordsSets(name);

        for (BagOfWords bag:bagsOfWords)
        {
            WordsSets tmp =wordsSets;
            for (Word w:tmp.getWordList())
                w.setSet(false);

            for (Map.Entry entry:bag.getBagHashMap().entrySet())
            {
                Word word=new Word((String) entry.getKey());

                if (!tmp.getWordList().contains(word))
                        tmp.getWordList().add(word);

                for (Word w:tmp.getWordList())
                {
                    if (w.getValue().equals(entry.getKey()))
                        if (!w.isSet())
                        {
                            w.setSet(true);
                            w.addFreq(1);
                        }
                }

            }

            }



        double max=1;

        for (Map.Entry entry:all.getBagHashMap().entrySet())
        {
            max=Math.max(max, (int) entry.getValue());
        }

            for (Word w:wordsSets.getWordList())
            {
                double dt=(Math.log((double) bagsOfWords.size()/w.getFreq()));
                double tf=0.5f+0.5f*((double) all.countOccurrences(w.getValue())/max);

                w.setFreq(dt*tf);
            }

    }

    public WordsSets getWordsSets() {
        return wordsSets;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFileCount() {
        return fileCount;
    }

    public void setFileCount(double fileCount) {
        this.fileCount = fileCount;
    }
}
