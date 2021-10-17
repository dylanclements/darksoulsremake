package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {
	/**
	 * Spawn the player
	 * @param spawnLocation location the player will spawn in
	 * @param world the world
	 */
	private static void spawnPlayer(Location spawnLocation, World world) {
		Actor player = new Player("Unkindled (Player)", '@', 200, spawnLocation);
		world.addPlayer(player, spawnLocation);
	}

	/**
	 * Spawn a bonfire
	 * @param location location the bonfire will exist in
	 * @param bonfireName name of the bonfire
	 */
	private static void spawnBonfire(Location location, String bonfireName) {
		location.setGround(new Bonfire(location, bonfireName));
	}

	/**
	 * Spawn a Skeleton
	 * @param location location the skeleton will be spawned on
	 * @param skeletonName name of the skeleton
	 */
	private static void spawnSkeleton(Location location, String skeletonName) {
		location.addActor(new Skeleton(skeletonName, location));
	}

	/**
	 * Spawn a Yhorm
	 * @param spawnLocation location yhorm will be spawned on
	 */
	private static void spawnYhorm(Location spawnLocation) {
		spawnLocation.addActor(new Yhorm(spawnLocation));
	}

	/**
	 * Spawn storm ruler
	 * @param spawnLocation location that storm ruler will be spawned on
	 */
	private static void spawnStormRuler(Location spawnLocation) {
		spawnLocation.addItem(new StormRuler());
	}

	/**
	 * Spawn a fog door that leads to another map
	 * @param mapASpawnLocation location on map A that the fog door will exist on
	 * @param mapBSpawnLocation location on map B that the fog door will exist on
	 * @param front location that grants the action
	 * @param back other location that grants the action
	 */
	private static void spawnInterMapFogDoor(Location mapASpawnLocation, Location mapBSpawnLocation,
											 Location front, Location back) {
		assert front.map() != back.map();
		FogDoor mapAFogDoor = new FogDoor(front, back);
		mapASpawnLocation.setGround(mapAFogDoor);
		FogDoor mapBFogDoor = new FogDoor(back, front);
		mapBSpawnLocation.setGround(mapBFogDoor);
	}

	/**
	 * Spawn a fog door that leads to the same map
	 * @param spawnLocation location that the fog door will exist on
	 * @param front location that grants the action
	 * @param back other location that grants the action
	 */
	private static void spawnIntraMapFogDoor(Location spawnLocation, Location front, Location back) {
		assert front.map() == back.map();
		FogDoor fogDoor = new FogDoor(front, back);
		spawnLocation.setGround(fogDoor);
	}

	/**
	 * Spawn Aldrich
	 * @param spawnLocation location that Aldrich will spawn on
	 */
	private static void spawnAldrich(Location spawnLocation) {
		spawnLocation.addActor(new Aldrich(spawnLocation));
	}

	/**
	 * Spawn random chests on the map.
	 * @param map map that chests will spawn on
	 */
	private static void spawnRandomChests(GameMap map) {
		// Spawn chests randomly in profane capital and anor londo
		Random r = new Random();
		float SPAWN_CHANCE = 0.005f;
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		ArrayList<Location> dirtLocations = new ArrayList<>();

		for (int x = xRange.min(); x < xRange.max(); x++){
			for (int y = yRange.min(); y < yRange.max(); y++){
				if (map.at(x,y).getGround() instanceof Dirt){
					dirtLocations.add(map.at(x,y));
				}
			}
		}
		for (Location dirtLocation : dirtLocations) {
			if (r.nextFloat() <= SPAWN_CHANCE) {
				Ground oldGround = dirtLocation.getGround();
				dirtLocation.setGround(new Chest(oldGround, dirtLocation));
			}
		}

	}

	/**
	 * Game starts from here.
	 * @param args any command arguments
	 */
	public static void main(String[] args) {
			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(
					new Dirt(), new Wall(), new Floor(), new Valley(), new Cemetery()
			);

			// Create the maps
			GameMap profaneCapital = new GameMap(groundFactory, Maps.PROFANE_CAPITAL);
			GameMap anorLondo = new GameMap(groundFactory, Maps.ANOR_LONDO);
			world.addGameMap(profaneCapital);
			world.addGameMap(anorLondo);

			// Spawn player
			spawnPlayer(profaneCapital.at(38, 12), world);

			// Spawn bonfires
			spawnBonfire(profaneCapital.at(38, 11), "Firelink Shrine Bonfire");
			spawnBonfire(anorLondo.at(39, 11), "Anor Londo Bonfire");

			// Place some skeletons around the map
			// Randomly place skeletons on the map
			spawnSkeleton(profaneCapital.at(38, 18), "Steve");
			spawnSkeleton(profaneCapital.at(38 + 15, 12 - 5), "Skeleton");
			spawnSkeleton(profaneCapital.at(38 + 35, 12), "Skeleton");
			spawnSkeleton(profaneCapital.at(38 - 20, 12 - 10), "Skeleton");

			// Place Yhorm the Giant/boss in the map + storm ruler
			spawnYhorm(profaneCapital.at(6, 25));
			spawnStormRuler(profaneCapital.at(6, 20));

			// Spawn fog door from profane capital to anor londo
			spawnInterMapFogDoor(
					profaneCapital.at(39, 25), anorLondo.at(39, 0),		// Location where fog door is placed
					profaneCapital.at(39, 24), anorLondo.at(39, 1)		// Entry/Exits (vice versa)
			);

			// Spawn fog door into aldrich's chamber
			spawnIntraMapFogDoor(anorLondo.at(39, 17), anorLondo.at(39, 16), anorLondo.at(39, 18));

			// Spawn Aldrich
			spawnAldrich(anorLondo.at(39, 27));

			//Spawn Skeletons in Anor Londo
			spawnSkeleton(anorLondo.at(7, 13), "Skeleton");
			spawnSkeleton(anorLondo.at(71, 11), "Skeleton");
			spawnSkeleton(anorLondo.at(71, 18), "Skeleton");
			spawnSkeleton(anorLondo.at(26, 11), "Skeleton");
			spawnSkeleton(anorLondo.at(54, 11), "Skeleton");
			spawnSkeleton(anorLondo.at(17, 22), "Skeleton");
			spawnSkeleton(anorLondo.at(66, 26), "Skeleton");
			spawnSkeleton(anorLondo.at(58, 26), "Skeleton");
			spawnSkeleton(anorLondo.at(26, 3), "Skeleton");
			spawnSkeleton(anorLondo.at(9, 6), "Skeleton");
			spawnSkeleton(anorLondo.at(55, 7), "Skeleton");
			spawnSkeleton(anorLondo.at(72, 6), "Skeleton");

			// Spawn chests randomly in profane capital and anor londo
			spawnRandomChests(profaneCapital);
			spawnRandomChests(anorLondo);

			// Run the game
			world.run();
	}
}
