# UML Class Diagram Rationale


## Requirement 1


**Player**

Player must display their health, their weapon and their souls in the menu.<br>
There are no new classes needed to add this feature, a method called `displayStatus()` will be added in Player.

The Application creates Player and places Player on the map in the Firelink Shrine.
The Player initially holds a MeleeWeapon (BroadSword). 
This will be added in the Player's inventory during instantiation of Player. 

**EstusFlask**

EstusFlask is an Item, and it has Action called DrinkEstusAction<br>
The Player will be instantiated with EstusFlask added to his inventory and that EstusFlask will be instantiated with 
DrinkEstusAction added to `allowableActions`<br>
Therefore, the Player will be prompted with an option to drink the estus every turn.
EstusFlask will also refill its charges when the Player rests at the Bonfire. Therefore, it must implement Resettable.

DrinkEstusAction will heal an Actor (Player) and decrement the charges in EstusFlask.<br>
DrinkEstusAction must target both an Actor and the EstusFlask instance it was declared in.

**Resulting UML relationships**
- Dependency from Application to Player
- Dependency from Player to EstusFlask
- Dependency from EstusFlask to DrinkEstusAction
- Association from DrinkEstusAction to Actor (1-1 Relationship)
- Association from DrinkEstusAction to EstusFlask (1-1 Relationship)
- Realisation from EstusFlask to Resettable interface


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

Enemies in this game must show their health in the console.<br>
However there are no new classes required to do this, just new methods.

**Undead**


`Undead` will be spawned by cemetery and has a 10% chance to die instantly each turn which will be implemented via
`chanceToDie()` method. Undead should have access to a number of behaviors 
such as `WanderBehavior()`, `AggroBehavior` which is why it will
be implementing `Behavior` Interface. `AttackAction` is dependent
on `AggroBehavior()` which gives Undead the ability to attack the player. If the player
attacks the undead its behavior will change from wander to attack the player
until he/she dies or goes inside the Firelink Shrine.

Undead wields an IntrinsicWeapon and cannot wield any other weapons.

**Skeleton**

4 to 12 `Skeleton` placed manually anywhere on the map. Skeleton walks around and follows the Player (plus attacks)
if the Player is within its radius (i.e., adjacent squares). Meaning it 
will be associated with `Behaviour` Interface as well. Skeleton will be able to attack
the player as soon as he/she is detected via `AggroBehaviour()`. The difference here
is the ability to attack the player unprovoked.
`Skeleton` will have the ability to wield `Broadsword` which will be accessed
via `MeleeWeapon` abstract class.

Skeleton also must know their Location of spawn for resetting purposes.

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
- Realisations drawn from Undead, Skeleton and Yhorm to Mortal, Soul
- Realisations drawn from Undead and Skeleton Resettable
- Generalisation drawn from LordOfCinder to Actor abstract class
- Generalisation drawn from Yhorm to LordOfCinder abstract class
- Dependency from Yhorm to GreatMachete to indicate how Yhorm can influence GreatMachine
- Dependency from GreatMachete to EmberFormAction to indicate how Yhorm can trigger his ember form as a result of holding GreatMachete
- Dependency from Undead to IntrinsicWeapon
- Dependency from Skeleton to MeleeWeapon
- Associations drawn from Undead, Skeleton, LordOfCinder to Behaviour interface to indicate that LordOfCinders have an attribute that is a list of behaviours.
- Association from Skeleton to Location to indicate that Skeletons store an attribute of Location type to remember their spawn.

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

**Weapons**

We have 3 Unique weapons `BroadSword`, `StormRuler` and `GreatMachete`.
Each weapon has 3 basic stats: Price, Damage(HP), Success hit rate. Except `GreatMachete`
it cannot be dropped and can only be wielded by Yhorm therefore it is priceless.


All weapons inherit from `MeleeWeapon` which inherit from abstract `WeaponItem`.<br>
MeleeWeapon should not be able to be instantiated. Therefore, MeleeWeapon should become an abstract class.<br>
Each weapon can have a Passive ability such as *Critical Strike* or Active abilities 
such as  *Charge*. Any type of passive ability will be implemented inside
the class dedicated to that weapon whilst Active abilities will inherit
from `WeaponAction` and will be associated with the corresponding weapon class.<br>
For example `StormRuler` will have 2 Active abilities `WindSlashAction`,`ChargeAction` and
2 Passive abilities (coded inside `StormRuler` class) `CriticalStrike`, `Dullness`.


**Resulting UML Relationships**
- Generalisations from BroadSword, GreatMachete and StormRuler to MeleeWeapon
- Generalisation from MeleeWeapon to WeaponItem
- Generalisation from WindSlashAction to AttackAction
- Generalisations from ChargeAction and EmberFormAction to WeaponAction
- Dependency from GreatMachete to EmberFormAction
- Dependency from StormRuler to ChargeAction and WindSlashAction
- Association from ChargeAction to Actor


## Other Changes


Two new behaviours have been added.
AggroBehaviour generate actions that follow an actor if the Actor is not within the radius,
else execute an AttackAction<br>
DoNothingBehaviour will simply always return a DoNothingAction.

For the requirements in this assignment, there will be no use for Actors such as Undead, Skeleton or Yhorm
to execute solely FollowBehaviour. All of these Actors must also attack the Player if they follow.<br>
AggroBehaviour will return actions from FollowBehaviour unless the target is reachable, which it will then return an AttackBehaviour.
Therefore, AggroBehaviour is composed of FollowBehaviour.

Yhorm is the only Actor that requires DoNothingBehaviour. 
Yhorm will execute this behaviour until he is provoked, after that he will begin AggroBehaviour.

**Resulting UML Relationships**
- Composition drawn from FollowBehaviour to AggroBehaviour to show that AggroBehaviour is composed of FollowBehaviour
- Realisations drawn from AggroBehaviour and DoNothingBehaviour to Behaviour interface to show they implement this interface.
- Association drawn from AggroBehaviour to an Actor because AggroBehaviour must target an Actor
- Dependency drawn from DoNothingBehaviour to DoNothingAction as DoNothingBehaviour will return DoNothingAction
- Dependency from AggroBehaviour to AttackAction as AggroBehaviour executes AttackAction if Actor is reachable.


# Assignment 2 - Design Changes Rationale

## Mortal Interface => DeathAction

During assignment 1, it was decided that deaths in the game should be implemented via an interface named 'mortal' that
only mortal actors would implement.<br>
It became apparent during implementation that deaths should be an action rather than implemented methods via an interface.<br>

DeathAction handles all scenarios of death:
1. Player dies (naturally or by getting killed): reset manager is triggered, soul token is placed and YOU DIED is printed
2. Actor dies at the hands of player: transfer souls and remove dying actor from the game
3. Actor dies a natural death: remove actor from the map
4. Actor dies at the hands of another actor that is not player: not implemented because it was optional but could have been here

All deaths in the game including deaths in future features will boil down to one of these four situations, 
therefore it was decided that it was NOT necessary to split DeathAction in to 4 different death classes. 
We do not believe we are violating the single responsibility principle here, DeathAction has the sole responsibility of handling deaths in the game.

## EmberFormAction => EmberForm interface

Assignment 1 proposed that Yhorm's great machete would have a WeaponAction sub-class called EmberFormAction.
During implementation, it was found that an EmberFormAction was in fact not necessary, for the following reasons:
- EmberFormAction will (and should) only affect a LordOfCinder. EmberFormAction will not affect the map, or any other actors or items.
- EmberFormAction will never be triggered as a result of behaviour or by player menu.

What made more sense is an interface called EmberForm that a LordOfCinder implements. This interface has a method called ```emberForm()``` and
the implementation of this method will have access to whatever the LordOfCinder has access to (Yhorm's great machete for example).
Implementing Yhorm's EmberForm means triggered it in ```playTurn()``` when yhorm's health is 1/2 (an AttackAction can trigger it too). 
The ```emberForm()``` method in Yhorm will trigger ```rageMode()``` in his GreatMachete which will boost accuracy.

Aldrich's ember form implementation in Assignment 3 will be easier as a result of this change too.

## Behaviour 

In Assignment 1 we thought Behaviours will have a 1-many relationship. Behaviours are 1/1 relationship now. Actors will
only be executing 1 behaviour at a time, behaviours will be swapped out when they need to change.

## Exceptions

One exception was added. A package has been created (edu.monash.fit2099.game.exceptions) to add potentially more in the future.

MissingWeaponException is thrown when an actor has to possess a weapon and for some reason it is missing.

## ActorStatus interface

This was added so that other classes could access an actor's health, max health and weapon.

This was used so that an actor's status could be displayed in player's menu when prompting an attack action.

Player's status is displayed via this interface too.

## Aggressor interface

Actors that have Behaviours, and can switch to AggroBehaviour implement this interface for the method ```switchAggroBehaviour()```.

## Player locations

In order to implement Valley deaths, the player should track it's current and previous location.

When player dies in the valley, SoulToken must be played at the spot the player was located before they died in the Valley.<br>
A SoulToken is to be placed at the previous location.

Player class now has associations with 2 location instances, for current and previous.

Previous UML diagram, ResetManager depended on Player due to player triggering ResetManager on death.<br>
This has been removed as the DeathAction class handles this responsibility now.

## SoulToken, PickUpSoulsAction

During implementation, it was discovered that a SoulToken should not be a PortableItem, but a Ground type.

PortableItems can only be picked up if the player stands directly on top of it. Whereas Ground types can generate actions
when the player is next to it.

To circumvent this, we tried to make player scan for items in its current location exit's every turn, but PickUpItemActions
only work when the Player is directly on top of the item. SoulToken had to be a ground type after this discovery.

SoulToken as a ground type must remember the old ground that it replaced. An association is drawn from SoulToken to Ground.<br>

SoulToken allows action PickUpSoulsAction which was created so that the player can pick up the soul token off the ground and transfer souls.<br>
PickUpSoulsAction knows the location the SoulToken is placed in and the oldGround that it replaced so that the oldGround can be swapped back in.

## WindSlashAction: WeaponAction => AttackAction

Previous WindSlashAction was planned to be a WeaponAction.
However, we decided to refactor it into AttackAction since WeaponAction did not have the methods
necessary to implement the requirements. Such as knowing the location and direction of the enemy (Yhorm).
Instead of implementing all this functionality inside `WindSlashAction()` we decided it would be easier to
extend upon `AttackAction()` thus minimising code repetition.

## EstusFlask + DrinkEstusAction

In the first part of the assignment we had an issue with EstusFlask and DrinkEstusAction having circular dependency. 

To fix this problem we decided
to introduce a `Consumable()` interface responsible for reducing charges after
`DrinkEstusAction()` occurred. By keeping all the method implementation inside
`EstusFlask()` whilst allowing `DrinkEstusAction()` to interact with them via interface
we demonstrated Dependency Inversion Principle by adding a layer of abstraction 
limiting the amount of effort required to modify the system and eliminating circular 
dependency.

## StormRuler + PickUpStormRulerAction + ChargeAction

In attempt to improve our design StormRuler is now implementing 2 new interfaces 
`Resettable()` and `IWindSlash()` instead of interacting with `ChargeAction()` and `WindSlashAction()`
directly to avoid creating instances of circular dependencies. 

`Resettable()` gives the StormRuler the ability
to reset all charges to 0 and reduce parameters back to normal in case the character died or the `WindSlashAction()` 
was executed. `IWindSlash()` contains all the important method headers necessary to perform
Charge or WindSlash actions. 

During the implementation stage we ran into the issue where the StormRuler would 
prompt the player with `ChargeAction()` before the player picked it up. Which meant
we had to avoid using normal pick up item action and had to introduce a new class
`PickUpStormRulerAction()` which extends `SwapWeaponAction()` which is responsible
for preventing the player from using `ChargeAction()` until the weapon is picked up
via Capabilities. 

# Assignment 3 - Further Design + Implementation

## AggroBehaviour + Aggressor & enabling ranged attacks

A minor refactor was made prior to the Aldrich implementation. 
Previously, AggroBehaviour scanned the actor's exits for the target, and if the target appeared in one of the exits, 
the attack would be executed. This will be inadequate for the ranged attacks that Aldrich will use, however only this 
needed to be changed to allow AggroBehaviour to execute a ranged attack. 

An additional method was added to Aggressor: ```boolean isWithinRange(Location targetLocation, GameMap map)```.
This will return true if the Aggressor's target is within its attack range, else false. AggroBehaviour will simply check
```isWithinRange``` and return an AttackAction if true.

Each Aggressor defines their own attack range. To adhere to DRY principles and the O in solid, a 
```public static final int``` should be written under each aggressor's class signature to define their attack range. 



