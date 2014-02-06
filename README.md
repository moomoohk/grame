Grame
=====

An easy to use, lightweight 2D grid based game engine!

[Click here](https://github.com/moomoohk/New-Grame/releases/latest) for the latest major release.

The JavaDocs can be downloaded [here](https://github.com/moomoohk/New-Grame/blob/master/JavaDocs.zip?raw=true) and can be viewed online [here](http://moomoohk.minelord.com/Documentation/Grame/).

My [YouTube channel](http://youtube.com/user/moomoohk) has some old demo videos on it, but they might be outdated.

#Getting started

On top of reading this documentation, I highly encourage you to have a look at the JavaDocs for a detailed list of classes and methods.

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

###Grids

Grids the map spaces of the engine. They will contain all your GrameObjects.

```java
Grid b = new Grid(20, 20);
```

By default Grids start with one `GrameObjectLayer` but more can be added to create 3 dimensions:

```java
g.addGrameObjectLayer(new GrameObjectLayer(20, 20));
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

