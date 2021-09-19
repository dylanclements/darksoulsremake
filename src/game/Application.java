package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(
					new Dirt(), new Wall(), new Floor(), new Valley(), new Cemetery(), new Bonfire()
			);

			List<String> map = Arrays.asList(
					"..++++++..+++...........................++++......+++.................+++.......",
					"........+++++..............................+++++++.................+++++........",
					"...........+++.......................C...............................+++++......",
					"........................................................................++......",
					".........................................................................+++....",
					"............................+.................................C...........+++...",
					".............................+++.......++++.....................................",
					"........C....................++.......+......................++++...............",
					".............................................................+++++++............",
					"..................................###___###...................+++...............",
					"..................................#_______#......................+++............",
					"...........++.....................#___B___#.......................+.............",
					".........+++......................#_______#........................++...........",
					"............+++...................####_####..........................+..........",
					"..............+......................................................++.........",
					"..............++.................................................++++++.........",
					"............+++...................................................++++..........",
					"+..................................................................++...........",
					"++...+++................C........................................++++...........",
					"+++......................................+++........................+.++........",
					"++++.......++++.........................++.........................+....++......",
					"#####___#####++++......................+..................C............+..+.....",
					"_..._........#.++......................+...................................+....",
					"...+.....+...#+++...........................................................+...",
					"...+.....+...#.+.....+++++...++..............................................++.",
					".............#.++++++++++++++.+++.............................................++");
			GameMap gameMap = new GameMap(groundFactory, map);
			world.addGameMap(gameMap);

			Actor player = new Player("Unkindled (Player)", '@', 200);
			player.addItemToInventory(new EstusFlask());
			world.addPlayer(player, gameMap.at(38, 12));

			// Place some skeletons around the map
			// TODO: Spawn 4-12 skeletons
			Location spawn = gameMap.at(38, 18);
			spawn.addActor(new Skeleton("Skeleton", spawn));

			// Place Yhorm the Giant/boss in the map
			gameMap.at(6, 25).addActor(new LordOfCinder("Yhorm the Giant", 'Y', 500));

			world.run();

	}
}
