Grame
=====

An easy to use, lightweight 2D grid based game engine!

[Click here](https://github.com/moomoohk/grame/releases/latest) for the latest major release.

The JavaDocs can be downloaded [here](https://github.com/moomoohk/grame/blob/master/JavaDocs.zip?raw=true) and can be viewed online [here](http://moomoohk.github.io/grame/docs/) (although the online version might be outdated!).

My [YouTube channel](http://youtube.com/user/moomoohk) has some [old demo videos](https://www.youtube.com/user/moomoohk/search?query=grame) on it, but they might be outdated.

#Getting started

On top of reading this documentation, I highly encourage you to have a look at the JavaDocs for a detailed list of classes and methods.

##Dependencies

Grame relies on two libraries I wrote:

1. [MooCommands](https://github.com/moomoohk/MooCommands)
2. [MooConsole](https://github.com/moomoohk/MooConsole)

Unfortunately I haven't yet found a way to bundle dependencies into one JAR file so you'll have to add all three to your classpath.

##Writing your game

Create a new main game class. This class will contain the starting point for your code as well as some metainfo for the engine and will also register your class as a main game class.

```java
public class Game implements MainGrameClass
{
    public static void main(String[] args)
    {
        GrameManager.initialize(new MainGameClass());
    }

    @Override
    public void newGame()
    {
        System.out.println("Hello World!");
    }
    
    @Override
    public String getGameName()
    {
        return "Hello world";
    }
}
```

Running this code will produce a main menu which will display what the `getGameName()` method returns and will run the `newGame()` method when the player hits play.

The engine is split up into two parts:

* **The core**: The core contains all the core classes which you'll have to use.
* **The basics**: The basics are a collection of helpful classes I wrote which utilize the core and will probably save you a lot of time.

##The core

###IDs

The two main components of the engine are the `Grid`s and the `GrameObject`s. Every instance of both of those components will automatically get a unique ID number associated with them by the `GrameManager` when they're created. The IDs are created successively. The ID successions for `Grid`s and `GrameObject`s are independent (meaning you can have a `Grid` object with ID:1 and a `GrameObject` object with ID:1).

###GrameManager

The `GrameManager` class contains the central engine clock as well as the `GrameObject` and `Base` list. It also keeps tracks of user input so you don't have to.

`GrameObject`s and `Grid`s can be retrieved by ID from the `GrameManager`'s lists using `GrameManager.findGrameObject(int goID)` and `GrameManager.findGrid(int gID)` respectively.

###GrameObjects

GrameObjects are the entities that occupy spaces in your Base. They can be players, NPCs, walls, anything.

The `GrameObject` class is an abstract one. Instantiate one like so:

```java
import java.awt.Color;

public class Example extends GrameObject
{
    private static final long serialVersionUID = 1L;

    private boolean collidable;

    public Example(String name, int speed, Color color, boolean paused, boolean collidable)
    {
        super(name, speed, color, paused);
        this.collidable = collidable;
    }

	@Override
	public boolean isCollidable()
	{
		return this.collidable; //Will normally be hardcoded
	}

	@Override
	public void tick(int gID)
	{
	}

	@Override
	public void consume(GrameObject go)
	{
	} 
}
```

The speed of `GrameObject`s will determine how often the `GrameManager`'s clock will call their `tick(int gID)` method (in 60/ths of a second).

The `tick(int gID)` method should be used to calculate things like movement. The `gID` parameter represents one of the IDs of the `Grid`s that this `GrameObject` has been added to. If your `GrameObject` has been added to more than one `Grid`, the `GrameManager` will call the `tick(int bID)` method using all of the `Grid` IDs one by one.

The `consume(GrameObject go)` method dictates to the `GrameObject` what to do in the event that it is collidable and it moves into a square that is occupied by a non collidable `GrameObject`. Leaving this method blank will simply overwrite the old non collidable `GrameObject` with your new collidable `GrameObject`.

###Grids

Grids are the map spaces of the engine. They will contain all your GrameObjects.

```java
Grid g = new Grid(20, 20);
```

By default Grids start with one `GrameObjectLayer` but more can be added to create 3 dimensions:

```java
g.addGrameObjectLayer(new GrameObjectLayer(20, 20));
```

`GrameObjectLayer`s are organized in a list inside the `Grid`. Using the default `addGrameObjectLayer(GrameObjectLayer gol)` method will add the parameterized `GrameObjectLayer` to the end of the list. The overloaded `addGrameObjectLayer(GrameObjectLayer gol, int place)` lets you manually place `GrameObjectLayer`s in specific places in the list. The lowest `GrameObjectLayer` is the one at place 0 and any other ones that get added later get stacked on top of it.

Adding a `GrameObject` to a `Grid`:

```java
g.addGrameObject(new Example("example", 2, Color.blue, false, true), new Coordinates(0, 0));
```

###Coordinates

These do what their name suggests which is contain an x and y value. These are used extensively to reference squares in `Grid`s.

```java
Coordinates c = new Coordinates(0, 0);
System.out.println(g.isInGrid(c));
```

###Dirs

Dir objects represent directions. They contain x and y values which can be either -1, 0, 1.

```java
Dir up = new Dir(0, -1);
Dir down = new Dir(0, 1);
Dir left = new Dir(-1, 0);
Dir right = new Dir(1, 0);
```

Alternatively:

```java
Dir up = Dir.UP;
Dir down = Dir.DOWN;
Dir left = Dir.LEFT;
Dir right = Dir.RIGHT;
```

These make coding AI a lot easier.

##The basics

Can be found in the `com.moomoohk.Grame.Basics` package.

###MovementAI

The `MovementAI` abstract class lets you create AI classes for use with `Entity`s (and other `GrameObject`s).

Grame comes with five ready to use AIs (which can be found at `com.moomoohk.Grame.Basics.AI`):

* `AStartPathfindingMovementAI` - Implementation of the A* pathfinding algorithm
* `PlayerMovementAI` - Reads user input from the `GrameManager` and can be used to control `Entity`s using WASD or the arrow keys
* (Debug) `PlayerSimAI` - Will pick a random direction and move in it for a random number of squares. If it hits an obstacle it will switch directions
* `SimpleChaseAI` - "Stupid" pathfinding. Will try to move in a straight line between itself and its target. If it hits an obstacle it will attempt to "slide" along it
* `SimpleStrollAI` - Will move in random directions. Useful for peaceful NPCs

Check out the source code for `PlayerMovementAI` for a simple `MovementAI` implementation.

###Entity

Entities are generic `GrameObject`s which support AI and a few other useful features.

Their AI system works with "stacking". You provide them with a list of `MovementAI`s (in order of preference) and every time they get ticked by the `GrameManager` they will go through the list (in the order of preference) and evaluate their `MovementAI`s using the `isValid` method. The first "valid" `MovementAI` in its list will be used to determine its next movement.

These following lines of code will produce a player `Entity` an `Entity` which will stroll randomly by default but will start chasing the player once the player enters its range:

```java
Entity player = new Entity("player", 1); //name, speed
player.addAI(new PlayerMovementAI(1)); //1 for WASD, 2 for arrow keys

Entity monster = new Entity("monster", 2);
monster.addAI(new SimpleStrollAI());
monster.setRange(5); //Range is set to a 5 square radius
monster.addAI(new SimpleChaseAI());
````

The `EntityGenerator` interface lets you create `Entity` generation classes. These are useful when you'd like to procedurally generate them. Grame comes with one implementation of it. The `DefaultRandomGen` will randomly generate names based on some basic language heuristics and types by picking a random spot in an array containing "human", "orc" and "elf".

The `Entity` AI system makes creating player `GrameObject`s really easy:

```java
ent.makePlayer(1, true, g.ID);
```

###Wall

`Wall`s are what you'd expect them to be. They are collidable, static `GrameObject`s.

```java
Wall w1 = new Wall(); //Default wall color is Color.black
Wall w2 = new Wall(Color.green);
```


I should probably include a picture of a sheep:

![alt text](http://images.fineartamerica.com/images-medium-large-5/sheep-in-summer-meadow-elena-elisseeva.jpg "Baaaah")
