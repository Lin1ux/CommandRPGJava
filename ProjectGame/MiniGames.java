package ProjectGame;

import Combat.UnitStats;

import java.util.Scanner;

public class MiniGames 
{
    public static void Game1(Scanner s,Integer Bet,UnitStats Player)
    {
        Integer player1Value = OtherFunctions.RandInt(2,12); 
        Integer player2Value = OtherFunctions.RandInt(2,12); 
        //Przelosowanie przeciwnika
        if(player2Value<6)
        {
            player2Value = OtherFunctions.RandInt(2,12); 
        }
        Boolean Finished = false;
        String PlayerInput = "";
        OtherFunctions.clearScreen();
        while(!Finished)
        {
            System.out.println("Wylosowano: "+player1Value);
            System.out.println("Czy chcesz losować jeszcze raz?: ");
            System.out.println("[tak, nie]");
            PlayerInput = s.nextLine();
            //Przelosowanie gracza
            if(PlayerInput.equals("tak") || PlayerInput.equals("1"))
            {
                player1Value = OtherFunctions.RandInt(2,12); 
                Finished = true;
            }
            else if(PlayerInput.equals("nie") || PlayerInput.equals("2"))
            {
                Finished = true;
            }
            else
            {
                OtherFunctions.clearScreen();
                System.out.println("Nie znana komenda");
            }
        }
        //Wynik
        OtherFunctions.clearScreen();
        System.out.println("Twój wynik: "+player1Value);
        System.out.println("Przeciwnik wylosował: "+player2Value);
        if(player1Value>player2Value)
        {
            System.out.println("Wygrywasz! Zdobywasz złoto (+"+(Bet*2)+" złota)");
            Player.ChangeGold(Bet*2);
        }
        else if(player1Value<player2Value)
        {
            System.out.println("Przegrywasz! Tracisz złoto (-"+Bet+" złota)");
            Player.ChangeGold(-Bet);
        }
        else
        {
            System.out.println("Remis! Tracisz złoto (-"+Bet+" złota)");
            Player.ChangeGold(-Bet);
        }
        System.out.println("Wciśnij Enter aby kontynuować");
        PlayerInput = s.nextLine();
    }
}
