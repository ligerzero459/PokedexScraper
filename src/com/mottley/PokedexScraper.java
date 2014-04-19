/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mottley;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan
 */
public class PokedexScraper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Objects
        FileWriter writer = null;
        String pageSource = "";

        try
        {
            // Opening cvs file to write data to
            File file = new File("pokedex.csv");
            
            // Deletes file if it already exists
            if (file.exists())
            {
                file.delete();
            }

            writer = new FileWriter(file);
            
            // Setting up the start of the file
            writer.append("Number, Name, National, Central Kalos, Coastal Kalos, Mountain Kalos, Gender Male, "
                    + "Gender Female, Type 1, Type 2, Classification, Height, Weight, Capture Rate, Base Egg Steps, "
                    + "Ability 1, Ability 2, Experience Growth, Base Happiness, Effort Values Earned, Location X, "
                    + "Location Y, Flavor X, Flavor Y, Base Stats, Base HP, Base Attack, Base Defense, Base Sp. Attack, "
                    + "Base Sp. Defense, Base Speed\n");
            writer.flush();
            
        } 
        catch (IOException ex)
        {
            // Writer failed to open, exit with error code -1
            Logger.getLogger(PokedexScraper.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }

        for (int pNum = 1; pNum <= 1; pNum++)
        {
            // Selecting page to read
//            if (pNum < 10)
//            {
//                pageSource = com.mottley.URLFunctions.downloadPage("http://www.serebii.net/pokedex-xy/00" + pNum + ".shtml");
//            }
//            else if (pNum < 100)
//            {
//                pageSource = com.mottley.URLFunctions.downloadPage("http://www.serebii.net/pokedex-xy/0" + pNum + ".shtml");
//            }
//            else
//            {
//                pageSource = com.mottley.URLFunctions.downloadPage("http://www.serebii.net/pokedex-xy/" + pNum + ".shtml");
//            }
            pageSource = com.mottley.URLFunctions.downloadPage("http://www.serebii.net/pokedex-xy/001.shtml");

            Scanner scanner = new Scanner(pageSource);
            String result = new String();
            try 
            {
                while (scanner.hasNextLine()) 
                {
                    result = scanner.nextLine();
                    
                    // Scanning line, looking for TITLE block
                    if (result.contains("<title>")) 
                    {
                        // Grabbing number and name
                        result = result.substring(7);
                        String splitResult[] = result.split("-");
                        
                        writer.append(splitResult[1].trim().replace("#", "") + ", ");
                        writer.append(splitResult[0].trim() + ", ");
                    }
                    else if (result.contains("<b>National"))
                    {
                        result = result.substring(109);
                        result = result.replace("</td></tr>", "");
                        writer.append(result.trim().replace("#", "") + ", ");
                        writer.flush();
                    }
                    else if (result.contains("<b>Central Kalos"))
                    {
                        result = result.substring(42);
                        result = result.replace("</td></tr>", "");
                        writer.append(result.trim().replace("#", "") + ", ");
                        writer.flush();
                    }
                    else if (result.contains("<b>Coastal Kalos"))
                    {
                        result = result.substring(42);
                        result = result.replace("</td></tr>", "");
                        writer.append(result.trim().replace("#", "") + ", ");
                        writer.flush();
                    }
                    else if (result.contains("<b>Mountain Kalos"))
                    {
                        result = result.substring(43);
                        result = result.replace("</td></tr>", "");
                        writer.append(result.trim().replace("#", "") + ", ");
                        writer.flush();
                    }
//                    else if (result.contains("<tr><td colspan=\"2\" width=\"14%\" class=\"fooinfo\">Base Stats")) 
//                    {
//                        for (int i = 0; i < 6; i++) 
//                        {
//                            // Parsing base stats
//                            result = scanner.nextLine();
//                            result = result.replace("<td align=\"center\" class=\"fooinfo\">", "");
//                            result = result.replace("</td>", "");
//                            if (i == 5) 
//                            {
//                                result = result.replace("</tr>", "");
//                            }
//                            writer.append(result);
//                            if (i < 5) 
//                            {
//                                // If not last stat, add a ',' to CSV
//                                writer.append(", ");
//                            } else if (i == 5) 
//                            {
//                                // If last stat, go to new line
//                                writer.append("\n");
//                            }
//                        }
//                        writer.flush();
//                        break;
//                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(PokedexScraper.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                scanner.close();
            }
        }
        writer.close();
    }
}
