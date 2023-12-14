package ProjectGame;

import java.util.ArrayList;

public class QuickTexts 
{
    public static void WhatToDo(ArrayList<String> Commands)
    {
        System.out.println("Dostępne komendy:");
        System.out.println(Commands);
        System.out.println("Co robisz?");
    }
    public static void WhatToDoV(String visual,ArrayList<String> Commands)
    {
        System.out.println(visual);
        System.out.println("Dostępne komendy:");
        System.out.println(Commands);
        System.out.println("Co robisz?");
    }
}
