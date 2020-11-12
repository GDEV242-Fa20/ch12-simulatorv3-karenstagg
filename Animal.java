import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Karen Stagg
 * @version November 16, 2020
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    //The animal's age.
    private int age;
    //A random number generator for age and breeding.
    private static final Random rand = Randomizer.getRandom();
        
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        if (randomAge == true)
        {
            age = rand.nextInt(getMaxAge());
        }
        else
        {
            age = 0;
        }    
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    
    /**
     * Return the max age of this animal.
     * @return the max age of this animal.
     */
    abstract public int getMaxAge();

    /**
     * Return the breeding age of this animal.
     * 
     * @return the breeding age of this animal.
     */
    abstract public int getBreedingAge();
    
    /**
     * Return the breeding probability of this animal.
     * 
     * @return the breeding probability of this animal.
     */
    abstract public double getBreedingProbability();
    
    /**
     * Return the max litter size of this animal.
     * 
     * @return the max litter size of this animal.
     */
    abstract public int getMaxLitterSize();
    
    /**
     * Set the age of this animal.
     * @param age is the age of this animal.
     */
    public void setAge(int newAge)
    {
        age = newAge;
    }    
    
    /**
     * Return the age of this animal.
     * @return the age of this animal.
     */
    public int getAge()
    {
        return age;
    } 
    
     /**
     * An animal can breed if it has reached its breeding age.
     *
     * @return  true of the animal can breed.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
     /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return  the number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) 
        {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
            
    /**
     * Increase the age of the animal.
     * This could result in death.
     */
    protected void incrementAge()
    {
        age++;
        if (age > getMaxAge())
        {
            setDead();
        }    
    } 
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
