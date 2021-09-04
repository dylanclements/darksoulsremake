## Requirement 1

EstusFlask Item has Action called DrinkEstusAction<br>
The Player will be instantiated with EstusFlask added to his inventory and that EstusFlask will have DrinkEstusAction
added to EstusFlask. Therefore, the player will be prompted with an option to drink the estus every turn.

## Requirement 2

This requirement features the Bonfire, and it's resetting functionality. 
Some discussion about ResetManager is included too.

**Bonfire and BonfireRestAction**

The first thing that must be created in a Bonfire class which will inherit from the Ground abstract class in the engine. 
<br>
The reason for this is that the Bonfire is a feature of the game's map. It requires functionality that prevents the
player from entering it and prevents items being thrown. These are unique to classes of type Ground.

The player will be prompted with an option in their menu to rest at the Bonfire when they are adjacent to it. Therefore,
there must be an action that will trigger the Bonfire's reset functionality. This will be a class named 
BonfireRestAction, and it will inherit from the engine's abstract class, Action.

Bonfire will override `getAllowableActions()` method from Ground and return an `Actions()` instance that 
contains BonfireRestAction (just this class at this point, there could be more in the future). The game engine will 
check for the Bonfire's allowable actions when the player is next to the Bonfire and prompt the player to rest.

Because Bonfire `getAllowableActions()` returns an instance of `BonfireRestAction()` there will be a dependency
relationship from Bonfire to BonfireRestAction.<br>
BonfireRestAction's `execute()` will trigger the game's ResetManger singleton's method `run()` to soft-reset.

**ResetManager**

A dependency relationship is shown in the UML from BonfireRestAction to ResetManger because BonfireRestAction will 
trigger the ResetManager.

ResetManager's `run()` will traverse through the list of Resettable and call all instance's `resetInstance()` method.
Instances that are resettable (e.g. Undead, Skeleton, Yhorm) implement the Resettable interface. When these instances
are placed in the game, the `registerInstance()` method is called to store the instance in ResetManager.

## Requirement 3

These requirements feature Souls

An implied game rule can be discovered looking at the Soul interface:
*Soullable actors in the game must be mortal*<br>
In other words, Actor sub-classes that implement Soul must be able to die.

All classes that have the ability to contain and/or transfer souls (and therefore are mortal) to other soullable 
instances will implement the Soul interface.<br>
These include Player, enemies, Lords of Cinder, certain Ground sub-classes and potentially more.

A new overloaded method can be added to the Soul interface
- `die(Actor killer)`: Actor dies by getting killed by another Actor
- `die()` Actor dies by some other means

Actors have various ways in which they die. Each Actor sub-class will implement `die()` which will often involve 
`transferSouls()` being called.<br>
AttackAction subclass will call `die(Actor killer)` as well.

Say for example the Player slays an Undead, AttackAction calls `target.die(actor)` on the Undead which will undergo the
regular death procedure in addition to calling `transferSouls(actor)`.

Souls must also be transferred to a Ground sub-class when the Player dies. 
During the Player's death procedure, `transferSouls(ground)` will be called to transfer souls to the ground using 
DropItemAction

## Requirement 4

## Requirement 5

Cemeteries are Ground sub-classes because they require functionality such as `canActorEnter()` and `blockThrownObjects`<br>
Cemeteries must also store the location which they spawn in, therefore an associated is drawn in the UML between Cemetery
and Location in the game engine.

Cemeteries will also spawn Undead Actors in locations adjacent to them. Therefore, a dependency is drawn between Cemetery and Undead.

**make a decision about Valley feature here and write about it**

Valley will not have any new UML relationships. Simply, if the Player enters the valley, Player will `die()`.

## Requirement 6

When the player dies, the `die()` method from the Soul interface will be called which, among other things, calls ResetManager for a soft reset.<br>
One of these other things is dropping a SoulToken on the ground. The player will be able to pick this item back up after respawning.

SoulToken is a sub-class of Item, it will be portable and contain an attribute that describes the number of souls it holds.<br>
The reason being is that we want DropItemAction and PickUpItemAction. However, the Actor method `addItemToInventory()` 
will be overridden in Player, if the item is SoulToken, then do NOT add the item to inventory, rather add the amount of
souls to Player and print SOULS RETRIEVED.

If the player dies in a Valley, then SoulToken will be placed in a valid adjacent Ground. The `die()` method from Soul
interface will cater for this.


## Requirement 7