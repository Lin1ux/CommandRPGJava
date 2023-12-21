package ProjectGame;

import java.util.ArrayList;
import java.util.Arrays;

public class AllLocations 
{
    public static int House = 0;
    public static int Armadillo = 1;
    public static int Road = 2;
    public static int Village = 3;
    public static int Cart = 4;
    public static int Town = 5;
    public static int Lake = 6;
    public static int CaveEntrance = 7;
    public static int CaveQuest = 8;
    public static int LastRoom = 9;

    public static Location SwordLocation = new Location(House,"Pokój z mieczem",Visuals.SwordVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"drzwi","miecz"})));

    public static Location SmallHouseLocation = new Location(Armadillo,"Mała Chatka",Visuals.ArmadilloVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"polnoc","zachod","chatka","postac"})));

    public static Location RoadLocation = new Location(Road,"Droga",Visuals.GramlinVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"wschod","walka","zachod"})));   //Dokończyć usunąć komendę zachod

    public static Location VillageLocation = new Location(Village,"Wioska Dróżka",Visuals.VillageVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"wschod","platnerz","alchemik","karczma"})));

    public static Location CartLocation = new Location(Cart,"Zniszczony wóz",Visuals.RaptorVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"poludnie","walka","polnoc"}))); //Dokończyć usunąć komendę polnoc

    public static Location TownLocation = new Location(Town,"Miasteczko Piachy",Visuals.TownVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"poludnie","wschod","zachod","karczma","kowal"})));

    public static Location LakeLocation = new Location(Lake,"Jezioro",Visuals.BloodThisterVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"wschod","walka"})));

    public static Location CaveLocation = new Location(CaveEntrance,"Wejście do jaskini",Visuals.CaveEntranceVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"zachod","wrota"})));

    public static Location CaveQuestLocation = new Location(CaveQuest,"Statua",Visuals.ChestVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"powrot","tekst","skrzynka"})));

    public static Location LastRoomLocation = new Location(LastRoom,"Pokój z artefaktem",Visuals.EmptyVisual(),
    new ArrayList<String>(Arrays.asList(new String[]{"tekst","kielich"})));
}