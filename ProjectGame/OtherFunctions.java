package ProjectGame;

import java.util.ArrayList;

import Combat.Skills;

public class OtherFunctions 
{
    //Czyszczemnie ekranu konsoli
    //------------------------------
    public static void clearScreen() 
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
    //Formatuje napis do określonej wielkości
    //-----------------------------------------------------------------------------------------------------
    public static String FormatValues(String emptycharacter, String word, Integer worldLength,Integer mode)
    {
        Integer L = worldLength;
        String newWord = new String("");
        L = worldLength - word.length();
        if(L>0)
        {
            if(mode == 1)
            {
                for(int i=0;i<L;i++)
                {
                    newWord += emptycharacter;  
                }
                newWord+=word;
            }
            else
            {
                newWord+=word;
                for(int i=0;i<L;i++)
                {
                    newWord += emptycharacter;  
                }
            }
        }
        else
        {
            newWord+=word;
            return newWord;
        }
        return newWord;
    }
    //Losowa Wartość w zakresie min Max
    //-----------------------------------
    public static int RandInt(int min, int max) 
    {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
