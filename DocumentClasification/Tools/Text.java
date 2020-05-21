package Tools;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 * The type Text.
 */
public class Text
{

    /**
     * Format string.
     *
     * @param input the input
     * @return the string
     */
    public static String format(String input)
    {
        String ad= input.replaceAll("\n"," ");

        ad= ad.replaceAll("[^A-Za-z0-9]", " ");

        ad =ad.toLowerCase();

        return ad;
    }

    /**
     * Format output string.
     *
     * @param input the input
     * @param title the title
     * @return the string
     */
    public static String formatOutput(String[] input,String title)
    {
      StringBuilder tmp= new StringBuilder(title + "\n");

        for (int i= 0;i<input.length;i++)
        {
            tmp.append("[").append(i).append("]").append(" ").append(input[i]).append("\n");
        }

        return tmp.toString();


    }

    /**
     * Convert sgm array list.
     *
     * @param fileXML the file xml
     * @param Classes the classes
     * @return the array list
     * @throws ParserConfigurationException the parser configuration exception
     * @throws IOException                  the io exception
     * @throws SAXException                 the sax exception
     */
    public static ArrayList<File> convertSgm(File fileXML,String[] Classes) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<File> fileArrayList=new ArrayList<>();

    try {
       String[] ALL =Files.readString(Paths.get(fileXML.getPath())).split("<REUTERS");

        for (String ss:ALL)
            {
                try
                {
                String topics = ss.substring(ss.indexOf("<TOPICS>") + 1, ss.indexOf("</TOPICS>"));
                    topics+= ss.substring(ss.indexOf("<PLACES>") + 1, ss.indexOf("</PLACES>"));
                    topics+= ss.substring(ss.indexOf("<PEOPLE>") + 1, ss.indexOf("</PEOPLE>"));
                    topics+=  ss.substring(ss.indexOf("<ORGS>") + 1, ss.indexOf("</ORGS>"));
                    topics+= ss.substring(ss.indexOf("<EXCHANGES>") + 1, ss.indexOf("</EXCHANGES>"));
                    topics+= ss.substring(ss.indexOf("<COMPANIES>") + 1, ss.indexOf("</COMPANIES>"));
                String body = ss.substring(ss.indexOf("<BODY>") + 1, ss.indexOf("</BODY>"));

                File tmp = File.createTempFile("sqmConvert", ".lab");

                PrintStream fileStream = new PrintStream(tmp);

                    for(String s:Classes)
                        if (topics.contains(s))
                            fileStream.println(s);

                        fileStream.println(body);

                fileArrayList.add(tmp);
                }catch (Exception e)
                {}
            }
    }catch (Exception e)
    {}
        return fileArrayList;
    }
}
