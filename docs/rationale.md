# UML Class Diagram Rationale

## Requirement 1

**Player**

The Application creates Player and places Player on the map in the Firelink Shrine.

The Player initially holds a MeleeWeapon (BroadSword). This will be added in the Player's inventory during instantiation of Player.

**EstusFlask**

EstusFlask is an Item, and it has Action called DrinkEstusAction<br>
The Player will be instantiated with EstusFlask added to his inventory and that EstusFlask will be instantiated with 
DrinkEstusAction added to `allowableActions`
Therefore, the Player will be prompted with an option to drink the estus every turn.

DrinkEstusAction will heal an Actor (Player) and decrement the charges in EstusFlask.<br>
DrinkEstusAction must target both an Actor and the EstusFlask instance it was declared in.

**Resulting UML relationships**
- Dependency from Application to Player
- Dependency from Player to EstusFlask
- Dependency from EstusFlask to DrinkEstusAction
- Association from DrinkEstusAction to Actor (1-1 Relationship)
- Association from DrinkEstusAction to EstusFlask (1-1 Relationship)

## Requirement 2

This requirement features the Bonfire, and it's resetting functionality. 
Some discussion about ResetManager is included too.

**Bonfire and BonfireRestAction**

The first thing that must be created is a Bonfire class which will inherit from the Ground abstract class in the engine.<br>
The reason for this is that the Bonfire is a feature of the game's map. It requires functionality that prevents the
player from entering it and prevents items being thrown. These are unique to classes of type Ground.

The Application driver class will create and place the Bonfire in its correct position.

The player will be prompted with an option in their menu to rest at the Bonfire when they are adjacent to it. Therefore,
there must be an action that will trigger the Bonfire's reset functionality. This will be a class named 
BonfireRestAction.<br>

Bonfire will override `getAllowableActions()` method from Ground and return an `Actions()` instance that 
contains BonfireRestAction (just this action at the moment, there could be more in the future). The game engine will 
check for the Bonfire's allowable actions when the Player is next to the Bonfire and prompt the player to rest.

BonfireRestAction's `execute()` will trigger the game's ResetManger singleton's method `run()` to soft-reset.

**ResetManager**

ResetManager's `run()` will traverse through the list of Resettable and call all instance's `resetInstance()` method.
Instances should be reset (e.g. Undead, Skeleton, Player) implement the Resettable interface. <br>
When these classes are instantiated, the `registerInstance()` method is called to store the instance in ResetManager.

**Resulting UML Relationships**
- Dependency from Application to Bonfire
- Dependency from Bonfire to BonfireRestAction
- Dependency from BonfireRestAction to ResetManager
- Dependency from Player to BonfireRestAction

## Requirement 3

These requirements feature Souls

All classes that have the ability to contain and/or transfer souls to other soullable instances will implement the Soul interface.<br>
These include Player, Undead, Skeleton, Lords of Cinder, certain Ground sub-classes and potentially more.

During death procedures, Actors will call `transferSouls(Soul soulObject)`. Some scenarios include:
1. If Player dies, transfer souls to SoulToken (see requirement 6) and drop this item on the Ground the Player died in (except for Valley)
2. If Player dies in Valley, transfer souls to SoulToken and drop this item on a floor/dirt Ground adjacent to the Valley
3. If soullable Actor dies at the hands of Player, transfer souls to the player

In the UML, all Actors and Ground sub-classes that are soullable will show a generalisation with a dashed arrow from that
sub-class to the Soul interface.

**Resulting UML Relationships**
- Realisation drawn from all soullable Actors/Grounds to Soul interface

## Requirement 4

**Undead**

**Skeleton**

**Yhorm**

There may be more Lords of Cinders added to the game in the future. Each of which will have unique implementations.<br>
For this reason LordOfCinder class will become an abstract class that inherits from Actor. Yhorm will inherit from this LordOfCinder.

Yhorm's great machete (see Requirement 7) will also contain an active skill which is triggered when Yhorm reaches 50% health.<br>
This will be expressed as an Action called EmberFormAction. It is a weapon action and therefore will inherit from the engine's
WeaponAction abstract class.

Yhorm's behaviour is unique as well. In Yhorm's `playTurn()`, Yhorm will do DoNothingBehaviour until the Player enters his adjacent locations.<br>
When this happens, Yhorm will begin AggroBehaviour.

Yhorm must also not be resurrected after he has been killed; Yhorm is not Resettable.
Therefore, when ResetManger's `run()` is triggered, Yhorm will no longer be resurrected.

**Resulting UML Relationships**
- Realisations drawn from Yhorm to Mortal and Soul as Yhorm will implement these interfaces
- Generalisation drawn from LordOfCinder to Actor abstract class
- Generalisation drawn from Yhorm to LordOfCinder abstract class
- Dependency from Yhorm to GreatMachete to indicate how Yhorm can influence GreatMachine
- Dependency from GreatMachete to EmberFormAction to indicate how Yhorm can trigger his ember form as a result of holding GreatMachete
- Association drawn from LordOfCinder to Behaviour interface to indicate that LordOfCinders have an attribute that is a list of behaviours.

## Requirement 5

**Cemetery**

Cemeteries are Ground sub-classes because they require functionality such as `canActorEnter()` and `blockThrownObjects`<br>
Cemeteries must also store the location which they spawn.<br>
Cemeteries will also spawn Undead Actors in locations adjacent to them.

**Valley**

Valley will not have any new UML relationships. Simply, if the Player enters the valley, Player will `die()` (see Requirement 6)
Valley `canActorEnter()` will be modified to accommodate for this.

**Resulting UML Relationships**
- Generalisations are drawn between Cemetery and Ground and Valley and Ground.
- Dependency is drawn from Cemetery to Undead to indicate that Cemeteries can spawn Undead instances.
- Dependency from Application to Cemetery to show that Application driver class creates cemeteries and places them in the map.
- Association is drawn between Cemetery and Location in the game engine to indicate that cemeteries have an attribute of Location type.

## Requirement 6

To implement deaths in the game, a new interface can be used. The reason for this is that Actors die in various ways.<br>
Each Actor that can die will implement this interface to implement a method performs their death procedure.
This interface will be called Mortal.

A method will be added to Mortal called `die()`. It will be overloaded (and possibly more overloaded methods can be added):
1. `die(Actor killer)` is a death procedure when death is caused by another Actor
2. `die()` is a death procedure when death is not caused by another Actor.

Some examples:
- Player is killed by another Actor. `die(Actor killer)` defined in Player will `transferSouls()` to the Ground the player is standing on. After that, soft reset is initiated.
- Player dies in a Valley. `die()` defined in Player will assess the Ground the player died on and if it is a Valley, it will `transferSouls()` to the nearest non-Valley Ground. Then soft reset is initiated.
- Skeleton gets killed by Player. `die(Actor killer)` defined in Skeleton will `transferSouls()` to Player and remove Skeleton from game.
- Yhorm get killed by Player. `die(Actor killer)` defined in Yhorm will `transferSouls()` to Player and drop CindersOfALord and print LORD OF CINDER FALLEN defined in Yhorm will `transferSouls()` to Player and drop CindersOfALord and print LORD OF CINDER FALLEN among many other things.

To implement souls being dropped on the Ground, a new item will be created called SoulToken.<br>
SoulToken is a sub-class of PortableItem. It will contain an additional attribute that describes the number of souls it holds.<br>
The reason being is that we want DropItemAction and PickUpItemAction. However, the Actor method `addItemToInventory()` 
will be overridden in Player, if the item is SoulToken, then do NOT add the item to inventory, rather add the amount of
souls to Player and print SOULS RETRIEVED.

All Actors that can die will show a generalisation with dashed line pointing towards the interface, Mortal, in the UML.<br>
SoulToken inherits from PortableItem and a generalisation will be shown to indicate this.<br>
There will be a dependency drawn going from Player to SoulToken to indicate how Player's `addItemToInventory()` will check if a SoulToken is being picked up.<br>
Additionally, since the Player must trigger ResetManager when they die, a dependency will be drawn from Player to ResetManager indicating this.<br>

**Resulting UML Relationships**
- Generalisation drawn from SoulToken to PortableItem
- Dependency drawn from Player to PortableItem to show how Player can interact with PortableItem by dropping/picking (and therefore SoulToken too)
- Realisations drawn from all Mortal Actors to Mortal interface to show that these Actors implement the `die()` methods.


## Requirement 7

MeleeWeapon should not be able to be instantiated. Therefore, MeleeWeapon should become an abstract class.
