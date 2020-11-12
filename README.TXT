Project: foxes-and-rabbits-v3
Authors: Karen Stagg
Version: November 16, 2020


A predator-prey simulation involving foxes, bears, and rabbits in
an enclosed rectangular field.

This version of the simulation uses inheritance.  The super class is Animal, and there are three sub-classes: Fox, Rabbit, and Bear.

There is a visual simulator component to this program, which also gives the continual population status of each of the species.  In this visual simulator, the rabbits are orange, the fox are blue, and the bear are red.

Rabbits mostly just run around, breed, and die from either old age, overcrowding or being consumed as fox food.
Rabbit are prey for Fox.

Fox are predator to Rabbit, hunting for food to satisfy their hunger. Fox also breed, get hungry, and die from either old age, overcrowding, or being attacked from bear for territory invading. 
Fox are prey for Bear.

Bear are predator to Fox, protecting their den and habitat by attacking. They will attack all fox adjacent to their territory.  Bear also breed, and die from either old age or overcrowding.  They have no predators.

How to start:
    Create a Simulator object.
    Then call one of:
        + simulateOneStep - for a single step.
        + simulate - and supply a number (say 10) for that many steps.
        + runLongSimulation - for a simulation of 4000 steps.
