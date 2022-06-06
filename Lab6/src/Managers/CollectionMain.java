package Managers;

import Collection.Difficulty;
import Collection.LabWork;

import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * The class that works with the collections.
 */
public class CollectionMain {
    private TreeSet<LabWork> labcollection = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;
    private XMLParser xmlParser;

    /**
     * Constructor of the class.
     */
    public CollectionMain(FileManager fileManager, XMLParser xmlParser){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
        this.xmlParser = xmlParser;

        loadCollection();
    }

    public LabWork getFirst(){
        if(labcollection.isEmpty()){
            return null;
        }
        return labcollection.first();
    }

    /**
     * @return The collection itself.
     */
    public TreeSet<LabWork> getLabcollection(){
        return labcollection;
    }

    /**
     * @return The last initialization time.
     */
    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }

    /**
     * @return The last save time.
     */
    public LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }

    /**
     * @return The type of collection.
     */
    public String collectionType() {
        return labcollection.getClass().getName();
    }

    /**
     * @return The size of the collection.
     */
    public int collectionSize(){
        return labcollection.size();
    }

    /**
     * @param id ID of the lab.
     * @return Lab by its id.
     */
    public LabWork getById(long id){
        for(LabWork lab : labcollection){
            if(lab.getId()==id) {
                return lab;
            }
        }
        return null;
    }

    /**
     * @param labnumber Value of the lab.
     * @return Lab by its value.
     */
    public LabWork getByValue(LabWork labnumber){
        for(LabWork lab : labcollection){
            if(lab.equals(labnumber)){
                return lab;
            }
        }
        return null;
    }

    /**
     * @param lab Lab to add.
     */
    public void addToCollection(LabWork lab){
        labcollection.add(lab);
    }

    /**
     * @param str Name of the lab.
     */
    public void filterStartsWithName(String str){
        for(LabWork lab : labcollection){
            if(lab.getName().startsWith(str)){
                Console.println(lab);
            }
        }
    }

    /**
     * @param lab Lab to remove.
     */
    public void removeFromCollection(LabWork lab){
        labcollection.remove(lab);
    }

    /**
     * @param difficulty Difficluty of the lab.
     */
    public void removeAllByDifficulty(Difficulty difficulty){
        boolean proverka = false;
        for(LabWork lab : labcollection){
            if(lab.getDifficulty().equals(difficulty)){
                labcollection.remove(lab);
                proverka = true;
                break;
            }
        }
        if(proverka){
            removeAllByDifficulty(difficulty);
        }
    }

    /**
     * Clears the collection.
     */
    public void clearCollection(){
        labcollection.clear();
    }

    /**
     * @return Next ID.
     */
    public long generateNextId(){
        if(labcollection.isEmpty()){
            return 1;
        }
        else return  labcollection.last().getId()+1;
    }

    /**
     * Save the collection.
     */
    public void saveCollection(){
        String str = xmlParser.parseToXml(labcollection);
        System.out.println(str);
        fileManager.writeCollection(str);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Load the collection.
     */
    public void loadCollection(){
        labcollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Show the collection.
     */
    public void showCollection(){
        for(LabWork lab : labcollection){
            System.out.println(lab.toString());
        }
    }

    /**
     * @param labDel Lab to compare with.
     */
    public void removeLower(LabWork labDel){
        boolean proverka = false;
        for(LabWork lab : labcollection){
            if(lab.getId()<labDel.getId()){
                labcollection.remove(lab);
                proverka = true;
                break;
            }
        }
        if(proverka){
            removeLower(labDel);
        }
        labcollection.removeIf(lab -> lab.compareTo(labDel)<0);
    }
}
