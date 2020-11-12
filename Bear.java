import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a bear.
 * Bear age, move, breed, protect den and territory against foxes, and die.
 * 
 * @author Karen Stagg
 * @version November 16, 2020
 */
public class Bear extends Animal
{
    // Characteristics shared by all bear (class variables).
    
    // The age at which a bear can start to breed.
    private static final int BREEDING_AGE = 4;
    // The age to which a bear can live.
    private static final int MAX_AGE = 25;
    // The likelihood of a bear breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    /**
     * Create a bear. A bear can be created as a new born (age zero
     * and not hungry) or with a random age.
     * 
     * @param randomAge If true, the bear will have random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bear(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * This is what the bear does most of the time: it protects its den 
     * and surrounding habitat.
     * In the process, it might breed, attack multiple fox as intruding enemy,
     * or die of old age or overcrowding.
     * @param field The field currently occupied.
     * @param newBear A list to return newly born bear.
     */
    public void act(List<Animal> newBear)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newBear);            
            // Move towards a source of fox enemy if found.
            Location newLocation = attackFox();
            if(newLocation == null) { 
                // No fox  found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * Return the MAX_AGE for a bear.
     * 
     * @return the MAX_AGE for a bear.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the age at which a bear starts to breed.
     * 
     * @return the age at which a bear starts to breed.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability for a bear.
     * 
     * @return the breeding probability for a bear.
     */
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the max litter size for a bear.
     * 
     * @return the max litter size for a bear.
     */
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Look for foxes adjacent to the current location.
     * All foxes that are found are attacked.
     * @return where fox was found, or null if it wasn't.
     */
    private Location attackFox()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location foxesLocation = null;
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive())
                { 
                    fox.setDead();
                    foxesLocation = where;
                }
            }
        }
        return foxesLocation;
    }
    
    /**
     * Check whether or not this bear is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newBear A list to return newly born bear.
     */
    private void giveBirth(List<Animal> newBear)
    {
        // New bear are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bear young = new Bear(false, field, loc);
            newBear.add(young);
        }
    }
     
}
