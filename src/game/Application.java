package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	/**
	 * Game starts from here.
	 * @param args any command arguments
	 */
	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(
					new Dirt(), new Wall(), new Floor(), new Valley(), new Cemetery()
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
					"...........++.....................#_______#.......................+.............",
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
					".............#.++......................+...................................+....",
					"...+.....+...#+++...........................................................+...",
					"...+.....+...#.+.....+++++...++..............................................++.",
					".............#.++++++++++++++.+++.............................................++");
			GameMap gameMap = new GameMap(groundFactory, map);
			world.addGameMap(gameMap);

			Actor player = new Player("Unkindled (Player)", '@', 200);

			world.addPlayer(player, gameMap.at(38, 12));

			Location profaneCapitalBonfire = gameMap.at(38, 11);
			Bonfire bonfire = new Bonfire(profaneCapitalBonfire, "Profane Capital");
			profaneCapitalBonfire.setGround(bonfire);

			// Place some skeletons around the map
			// TODO: Spawn 4-12 skeletons
			gameMap.at(38, 18).addActor(new Skeleton("Skeleton", gameMap.at(38, 18)));
			gameMap.at(38 + 15, 12 - 5).addActor(new Skeleton("Skeleton", gameMap.at(38 + 15, 12 - 5)));
			gameMap.at(38 + 35, 12).addActor(new Skeleton("Skeleton", gameMap.at(38 + 35, 12)));
			gameMap.at(38 - 20, 12 - 10).addActor(new Skeleton("Skeleton", gameMap.at(38 - 20, 12 - 10)));

			// Place Yhorm the Giant/boss in the map + storm ruler
			Location yhormSpawn = gameMap.at(6, 25);
			yhormSpawn.addActor(new Yhorm(yhormSpawn));
			StormRuler stormRuler = new StormRuler();
			gameMap.at(6,20).addItem(stormRuler);

			world.run();

	}
}
