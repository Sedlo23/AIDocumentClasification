package Parametrization.IDF;

import java.util.ArrayList;

/**
 * The type Words sets.
 */
public class WordsSets
{
    /**
     * The Name.
     */
    String name;

    /**
     * The Word list.
     */
    ArrayList<Word> wordList;

    /**
     * Instantiates a new Words sets.
     *
     * @param name the name
     */
    public WordsSets(String name) {
        this.name = name;
        this.wordList =new ArrayList<>();
    }

    /**
     * Gets word list.
     *
     * @return the word list
     */
    public ArrayList<Word> getWordList() {
        return wordList;
    }

    @Override
    public boolean equals(Object o) {
        return name.equals((String) o);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
