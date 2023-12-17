package ItemObjects;

import ProjectGame.AllLocations;
import ProjectGame.OtherFunctions;

public class ItemFunctions 
{
    public static void CompassTarget(int Position)
    {
        if(Position == AllLocations.House || Position == AllLocations.Armadillo || Position == AllLocations.Cart 
        || Position == AllLocations.Village || Position == AllLocations.Road)
        {
            System.out.println("Kompas wskazuje kierunek Północno-Wschodni");
        }
        else if(Position == AllLocations.Village || Position == AllLocations.Lake)
        {
            System.out.println("Kompas wskazuje kierunek Wschodni");
        }
        else
        {
            System.out.println("Coś zakłuca działnie kompasu i nie możesz odczytać jednego kierunku");
        }
    }
    public static Integer Alcohol(int Position)
    {
        Integer newPosition;
        if(Position == AllLocations.House || Position == AllLocations.Armadillo || Position == AllLocations.Cart 
        || Position == AllLocations.Village || Position == AllLocations.Road)
        {
            newPosition = OtherFunctions.RandInt(0, 4);
        }
        else
        {
            newPosition=Position;
        }
        System.out.println("Zaczynasz pić i zapominasz co się działo. Budzisz się w nowej lokacji");
        //Losowanie nowej lokacji
        return newPosition;
    }
}
