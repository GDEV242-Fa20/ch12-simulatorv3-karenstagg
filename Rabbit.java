import java.util.List;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, get eaten by foxes, and die.
 * 
 * @author Karen Stagg
 * @version November 16, 2020
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    
    // Individual characteristics (instance fields).
    
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
     * Return the MAX_AGE for a rabbit.
     * 
     * @return the MAX_AGE for a rabbit.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Return the age at which a rabbit starts to breed.
     * 
     * @return the age at which a rabbit starts to breed.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability for a rabbit.
     * 
     * @return the breeding probability for a rabbit.
     */
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the max litter size for a rabbit.
     * 
     * @return the max litter size for a rabbit.
     */
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
        
}
