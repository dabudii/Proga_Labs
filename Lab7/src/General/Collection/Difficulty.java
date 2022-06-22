package General.Collection;

/**
 * Enumeration with difficulties of the lab.
 */
public enum Difficulty {
    EASY,
    NORMAL,
    HARD,
    INSANE;

    /**
     * @return Difficulties of the lab.
     */
    public static String nameList(){
        String nameList = "";
        Integer i=1;
        for(Difficulty difficulty : values()){
            nameList += i + " - " + difficulty.name() + "\n";
            i=i+1;
        }
        return nameList.substring(0, nameList.length()-1);
    }
}
