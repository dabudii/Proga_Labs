package server.utility;

import general.collection.Difficulty;
import general.collection.LabWork;
import general.exceptions.DatabaseHandlingException;
import general.utility.Printer;

import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * The class that works with the collections.
 */
public class CollectionMain {
    private TreeSet<LabWork> labcollection = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public CollectionMain(DatabaseCollectionMain databaseCollectionMain){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.databaseCollectionMain = databaseCollectionMain;

        loadCollection();
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
        return labcollection.stream().filter(labWork -> labWork.getId()==id).findFirst().orElse(null);
    }

    /**
     * @param labnumber Value of the lab.
     * @return Lab by its value.
     */
    public LabWork getByValue(LabWork labnumber){
        return labcollection.stream().filter(labWork -> labWork.equals(labnumber)).findFirst().orElse(null);
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
    public String filterStartsWithName(String str){
        return labcollection.stream().filter(labWork -> labWork.getName().equals(str)).reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
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
    public void removeAllByDifficulty(Difficulty difficulty) throws DatabaseHandlingException {
            for(LabWork lab : labcollection){
                if(lab.getDifficulty().equals(difficulty)){
                    databaseCollectionMain.deleteLabworkById(lab.getId());
                    labcollection.remove(lab);
                    break;
                }
            }
        labcollection.stream().filter(labWork -> labWork.getDifficulty().equals(difficulty)).findAny().ifPresent(this::removeFromCollection);
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
     * Load the collection.
     */
    public void loadCollection(){
        try{
            labcollection = databaseCollectionMain.getCollection();
            lastInitTime = LocalDateTime.now();
            Printer.println("Коллекция успешно загружена!");
        } catch (DatabaseHandlingException exception){
            labcollection = new TreeSet<>();
            Printer.printerror("Коллекция не может быть загружена!");
        }
    }

    /**
     * Show the collection.
     *
     * @return
     */
    public String showCollection(){
        return labcollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    /**
     * @param labDel Lab to compare with.
     */
    public void removeLower(LabWork labDel){
        labcollection.removeIf(labWork -> labWork.compareTo(labDel)<0);
    }
}
