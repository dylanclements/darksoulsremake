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
					"...+.....+...#.+.....+++++...++.....+#___#++.................................++.",
					".............#.++++++++++++++.+++.++##_._##++++...............................++");

		List<String> anorLondoMap = Arrays.asList(
					"......+++++......................#...........#..................................",
					"...++++......................#####___________#####....+++.........+++++.........",
					".+++...............+++.......#...................#..++++......C.....+++++++.....",
					"..+++....C...........++......#####...........#####...+++..............+++.......",
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
					"#.................C.........................................C.................#.",
					"#.............................................................................#.",
					"####_______####........######......#.......#......######........#####_____####..",
					"...............########......#####################......##____##..#.....#.......",
					".+++.............++............####.........####...................##.....##....",
					"++.............+++...........##.................##....+++++++....##.........##..",
					".+++........#######........##...++.....+.....++...##....++++...##.............##",
					"..+........#.......########............+............##....+++....##..#####..##..",
					"...........#.................+++++...+++++...+++++..##...++........##.....##....",
					"...........#.......########............+............##....++....................",
					".....+......#######........##...++.....+.....++...##...............++...........",
					"....+........................##.................##...+..............+++.........",
					"..+++...............+++........#####.......#####...++.................++++......",
					"...++......C......+++++...........##.......##....+++.........C.........++.......",
					".....................++++.......###.........###....++.................+++.......",
					"....................++.......#####################..+++..............++++++++...");
			GameMap profaneCapital = new GameMap(groundFactory, profaneCapitalMap);
			GameMap anorLondo = new GameMap(groundFactory, anorLondoMap);

			// Create the maps
			world.addGameMap(profaneCapital);
			world.addGameMap(anorLondo);

			// Spawn the player
			Location playerSpawn = profaneCapital.at(38, 12);
			Actor player = new Player("Unkindled (Player)", '@', 200, playerSpawn);
			world.addPlayer(player, playerSpawn);

			// Spawn profane capital bonfire
			Location profaneCapitalBonfireLocation = profaneCapital.at(38, 11);
			Bonfire profaneCapitalBonfire = new Bonfire(profaneCapitalBonfireLocation, "Profane Capital");
			profaneCapitalBonfireLocation.setGround(profaneCapitalBonfire);

			// Spawn anor londo bonfire
			Location anorLondoBonfireLocation = anorLondo.at(39, 11);
			Bonfire anorLondoBonfire = new Bonfire(anorLondoBonfireLocation, "Anor Londo Bonfire");
			anorLondoBonfireLocation.setGround(anorLondoBonfire);

			// Place some skeletons around the map
			profaneCapital.at(38, 18).addActor(new Skeleton("Skeleton", profaneCapital.at(38, 18)));
			profaneCapital.at(38 + 15, 12 - 5).addActor(new Skeleton("Skeleton", profaneCapital.at(38 + 15, 12 - 5)));
			profaneCapital.at(38 + 35, 12).addActor(new Skeleton("Skeleton", profaneCapital.at(38 + 35, 12)));
			profaneCapital.at(38 - 20, 12 - 10).addActor(new Skeleton("Skeleton", profaneCapital.at(38 - 20, 12 - 10)));


			// Place Yhorm the Giant/boss in the map + storm ruler
			Location yhormSpawn = profaneCapital.at(6, 25);
			yhormSpawn.addActor(new Yhorm(yhormSpawn));
			StormRuler stormRuler = new StormRuler();
			profaneCapital.at(6,20).addItem(stormRuler);

			Location chestLocation = profaneCapital.at(39,14);
			Chest chest = new Chest(chestLocation.getGround(),chestLocation);
			chestLocation.setGround(chest);


			// TODO spawn fog doors:
			profaneCapital.at(39, 25).setGround(new FogDoor(profaneCapital.at(39, 24), anorLondo.at(39, 1)));
			anorLondo.at(39, 0).setGround(new FogDoor(anorLondo.at(39, 1), profaneCapital.at(39, 24)));

			Location aldrichFogDoorLocation = anorLondo.at(39, 17);
			FogDoor aldrichFogDoor = new FogDoor(anorLondo.at(39, 16), anorLondo.at(39, 18));
			aldrichFogDoorLocation.setGround(aldrichFogDoor);

			// TODO: Spawn Alrdich the Devourer in Anor Londo
			Location aldrichSpawn = anorLondo.at(39, 27);
			aldrichSpawn.addActor(new Aldrich(aldrichSpawn));

			// TODO: Spawn Skeletons + cemeteries in Anor Londo:
			//Second brackets don't do anything?
			anorLondo.at(7, 13).addActor(new Skeleton("Skeleton", anorLondo.at(7, 13)));
			anorLondo.at(71, 11).addActor(new Skeleton("Skeleton", anorLondo.at(71, 11)));
			anorLondo.at(71, 18).addActor(new Skeleton("Skeleton", anorLondo.at(71, 18)));
			anorLondo.at(26, 11).addActor(new Skeleton("Skeleton", anorLondo.at(26, 11)));
			anorLondo.at(54, 11).addActor(new Skeleton("Skeleton", anorLondo.at(26, 11)));
			anorLondo.at(17, 22).addActor(new Skeleton("Skeleton", anorLondo.at(17, 22)));
			anorLondo.at(66, 26).addActor(new Skeleton("Skeleton", anorLondo.at(66, 26)));
			anorLondo.at(58, 26).addActor(new Skeleton("Skeleton", anorLondo.at(58, 26)));
			anorLondo.at(26, 3).addActor(new Skeleton("Skeleton", anorLondo.at(26, 3)));
			anorLondo.at(9, 6).addActor(new Skeleton("Skeleton", anorLondo.at(9, 6)));
			anorLondo.at(55, 7).addActor(new Skeleton("Skeleton", anorLondo.at(55, 7)));
			anorLondo.at(72, 6).addActor(new Skeleton("Skeleton", anorLondo.at(72, 6)));

			// TODO: spawn some chests
			//Doesn't want to spawn more than 1 chest
			Location chestLocation1 = anorLondo.at(17,22);
			Chest chest1 = new Chest(chestLocation1.getGround(), chestLocation1);
			chestLocation1.setGround(chest1);

			world.run();

	}
}
