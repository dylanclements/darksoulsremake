## Requirement 1

**Player and Estus Flask**

These requirements challenge us to come up with a way to display player's
health in the console such as "Unkindled (30/100)" we will be achieving this by adding a `displayHealth()` method
to the `Player` class which will be printing players health to the console at the beginning of each  turn.

Second task is to develop a unique potion called Estus Flask. It will be a separate class called
`EstusFlask` which will inherit from `Item` and implement `Resettable` interface
(because 'when the player chooses to rest' we have to *Refill* Estus Flask
to maximum charges) to be make sure that reset functionality is available to `EstusFlask`. 
Health potion has 3 charges, restores
40% of maximum hit points and cannot be dropped, meaning it is not a `PortableItem`.
We need to display the number of charges in the console which will be achieved by calling a
`displayCharges()` method from `DrinkEstusAction` class

The Player will be instantiated with `EstusFlask` added to his inventory and that EstusFlask will have `DrinkEstusAction`
added to EstusFlask. `DrinkEstusAction` will inherit from abstract `Action` which will give us 
access to `execute()`, `hotkey()`, `menuDescription()` methods allowing us to prompt the player
with an option to drink Estus Flask at the beginning of each turn.


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

**Enemies**

To fulfill this requirement we will have to implement one new enemy type:
`Skeleton` and improve the existing functionality of `Undead` and `Lord of Cinder` classes.

First we need to show hitpoints, maximum hitpoints, and the weapon that enemy
carries in the console history log by since enemies inherit from abstract `Actor` we
will have access to all these variables which we can then print to the console
at the end of each turn.

`Undead` will be spawned by cemetery and has a 10% chance to die instantly each turn which will be implemented via
`chanceToDie()` method. Undead should have access to a number of behaviors 
such as `WanderBehavior()`, `AggroBehavior` which is why it will
be implementing `Behavior` Interface. `AttackAction` is dependent
on `AggroBehavior()` which gives Undead the ability to attack the player. If the player
attacks the undead its behavior will change from wander to attack the player
until he/she dies or goes inside the Firelink Shrine.

4 to 12 `Skeleton` placed manually anywhere on the map. Skeleton walks around and follows the Player (plus attacks)
if the Player is within its radius (i.e., adjacent squares). Meaning it 
will be implementing `Behaviour` Interface as well. Skeleton will be able to attack
the player as soon as he/she is detected via `AttackBehaviour()`. The difference here
is the ability to attack the player preemptively, this will be implemented with additional
checks. `Skeleton` will have the ability to wield `Broadsword` which will be accessed
via `MeleeWeapon` therefore they will be associated.

Yhorm the Giant (Lord of Cinder) is the first boss in the game.
He will be spawned inside Yhorm's chamber. To give Yhorm the ability 
to follow and attack the player he will also be implementing `Behaviour`
Interface. Which will allow him to access `AttackBehaviour()`. Yhorm is 
weak to Storm Ruler which means Storm Ruler will be dealing additional
damage to him. He will be wielding `Yhorm'sGreatMachete` which cannot be dropped.
`Yhorm'sGreatMachete` inherits from `MeleeWeapon` and has the unique ability 
`EmberFormAction` an active skill which implements `WeaponAction` and gets triggered
in second faze when Yhorm health drops below 50% when triggered it will print a suitable message. 
`Yhorm'sGreatMachete` will be associated with `EmberFormAction`. 
Once defeated an appropriate message will be printed ***(not sure where)***
and `CindersOfALord` item will be dropped. It will inherit from 
`PortableItem` which implements `Item` interface.

All three enemy types will be implementing `Soul` interface since once defeated
they have to pass their soles to the player. Same goes for `Mortal`
interface which will contain methods responsible for death mechanic. ???


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

**Weapons**

We have 3 Unique weapons `BroadSword`, `StormRuler` and `Yhorm'sGreatMachete`.
Each weapon has 3 basic stats: Price, Damage(HP), Success hit rate. Except `Yhorm'sGreatMachete`
it cannot be dropped and can only be wielded by Yhorm therefore it is priceless.


All weapons inherit from `MeleeWeapon` which inherit from abstract `WeaponItem`.
Each weapon can have a Passive ability such as *Critical Strike* or Active abilities 
such as  *Charge*. Any type of passive ability will be implemented inside
the class dedicated to that weapon whilst Active abilities will inherit
from `WeaponAction` and will be associated with the corresponding weapon class.
For example `StormRuler` will have 2 Active abilities `WindSlashAction`,`ChargeAction` and
2 Passive abilities (coded inside `StormRuler` class) `CriticalStrike`, `Dullness`.


 
