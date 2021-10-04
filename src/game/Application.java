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

			List<String> profaneCapitalMap = Arrays.asList(
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

		List<String> anorLondoMap = Arrays.asList(
					"......+++++......................#...........#..................................",
					"...++++......................#####___________#####....+++.........+++++.........",
					".+++...............+++.......#...................#..++++............+++++++.....",
					"..+++................++......#####...........#####...+++..............+++.......",
					".++................+++...........#...........#......++..................++......",
					"+..............+++++++.......#####...........#####..............++..............",
					"..................+++++++....#...................#.............+++++............",
					"................+++..........#####___________#####...........+++...##.....##....",
					".................................#...........#.............+++...##..#####..##..",
					"....#_____#............######..###...........###..##__##....++.##.............##",
					"...#.......#..........#......##..#...........#..##......#........##.........##..",
					"...##.....##....######........#...#.........#...#........######....##.....##....",
					"...#.......#...#......#............#.......#............#......#....#.....#.....",
					"#####.....#####........######......#.......#......######........#####_____####..",
					"#.............................................................................#.",
					"#.............................................................................#.",
					"####_______####........######......#.......#......######........#####_____####..",
					"...............########......#######_______#######......##____##..#.....#.......",
					".+++.............++............####.........####...................##.....##....",
					"++.............+++...........##.................##....+++++++....##.........##..",
					".+++........#######........##...++.....+.....++...##....++++...##.............##",
					"..+........#.......########............+............##....+++....##..#####..##..",
					"...........#.................+++++...+++++...+++++..##...++........##.....##....",
					"...........#.......########............+............##....++....................",
					".....+......##___##........##...++.....+.....++...##...............++...........",
					"....+........................##.................##...+..............+++.........",
					"..+++...............+++........#####.......#####...++.................++++......",
					"...++.............+++++...........##.......##....+++...................++.......",
					".....................++++.......###.........###....++.................+++.......",
					"....................++.......#####################..+++..............++++++++...");
			GameMap profaneCapital = new GameMap(groundFactory, profaneCapitalMap);
			GameMap anorLondo = new GameMap(groundFactory, anorLondoMap);

			world.addGameMap(profaneCapital);
			world.addGameMap(anorLondo);

			Location playerSpawn = profaneCapital.at(38, 12);
			Actor player = new Player("Unkindled (Player)", '@', 200, playerSpawn);
			world.addPlayer(player, playerSpawn);

			Location profaneCapitalBonfireLocation = profaneCapital.at(38, 11);
			Bonfire profaneCapitalBonfire = new Bonfire(profaneCapitalBonfireLocation, "Profane Capital");
			profaneCapitalBonfireLocation.setGround(profaneCapitalBonfire);

			Location anorLondoBonfireLocation = anorLondo.at(38, 11);
			Bonfire anorLondoBonfire = new Bonfire(anorLondoBonfireLocation, "Anor Londo Bonfire");
			anorLondoBonfireLocation.setGround(anorLondoBonfire);

			// Place some skeletons around the map
			// TODO: Spawn 4-12 skeletons
			profaneCapital.at(38, 18).addActor(new Skeleton("Skeleton", profaneCapital.at(38, 18)));
			profaneCapital.at(38 + 15, 12 - 5).addActor(new Skeleton("Skeleton", profaneCapital.at(38 + 15, 12 - 5)));
			profaneCapital.at(38 + 35, 12).addActor(new Skeleton("Skeleton", profaneCapital.at(38 + 35, 12)));
			profaneCapital.at(38 - 20, 12 - 10).addActor(new Skeleton("Skeleton", profaneCapital.at(38 - 20, 12 - 10)));

			// Place Yhorm the Giant/boss in the map + storm ruler
			Location yhormSpawn = profaneCapital.at(6, 25);
			yhormSpawn.addActor(new Yhorm(yhormSpawn));
			StormRuler stormRuler = new StormRuler();
			profaneCapital.at(6,20).addItem(stormRuler);

			// TODO spawn fog doors:
			profaneCapital.at(39, 25).setGround(new FogDoor(profaneCapital.at(39, 24), anorLondo.at(39, 1)));
			anorLondo.at(39, 0).setGround(new FogDoor(anorLondo.at(39, 1), profaneCapital.at(39, 24)));

			// TODO: Spawn Alrdich the Devourer in Anor Londo

			// TODO: Spawn Skeletons + cemeteries in Anor Londo

			world.run();

	}
}
