package ants;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import search.DirectionEnd;
import search.GetNextOnPath;
import search.PathBuilder;
import search.path.Path;
import strategie.Fight;
import strategie.Smell;


public class MyBot extends Bot {


	/**
	 * Main method executed by the game engine for starting the bot.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		new MyBot().readSystemInput();
	}


	Smell<Integer, Tile> smell = new Smell<Integer, Tile>(
			new PathBuilder<Integer, Tile>());
	private int round = 0;

	@Override
	public void doTurn() {

		Ants ants = getAnts();
		GetNextOnPath<Integer, Tile, Aim> getNextOnPath = new DirectionEnd<Integer, Tile, Aim>(
					new GetNextOnPathProblemAdapter(ants));
			round++;

			Map<Tile, Aim> plannedOrders = new HashMap<Tile, Aim>();
			Set<Tile> foodTiles = ants.getFoodTiles();
			int foodCount = foodTiles.size();
			int searchTimePerFood = (int) ((ants.getTurnTime() * (0.5)) / foodCount) - 1;

			for (Tile food : foodTiles) {
				smell.createSmell(new ProblemAdapter(ants), searchTimePerFood,
						food);
			}
			smell.removeCachedEntriesIfNotExisting(foodTiles);

			getMyAntsCount(ants);
			for (Tile myAnt : ants.getMyAnts()) {

			Path<Integer, Tile> shortestPath = smell.getShortestPath(myAnt);

				if (shortestPath != null) {
					Aim aim = getNextOnPath.get(shortestPath, myAnt);
					if (aim != null) {
						plannedOrders.put(myAnt, aim);
						continue;
					}
				}

				/**
				 * give random order if on own hill
				 */
				if (ants.getMyHills().contains(myAnt)) {
					Aim randomAim = Aim.randomAim();
					if (ants.getIlk(myAnt, randomAim).isUnoccupied()) {
						plannedOrders.put(myAnt, randomAim);
						continue;
					}
				}

				/**
				 * move away from nearest own hill
				 */
				int nearestHillDistance = Integer.MAX_VALUE;
				Tile nearestHillTile = null;
				for (Tile hill : ants.getMyHills()) {
					int distance = ants.getDistance(myAnt, hill);
					if (distance < nearestHillDistance) {
						nearestHillDistance = distance;
						nearestHillTile = hill;
					}
				}

				if (nearestHillTile != null) {
					List<Aim> directions = ants.getDirections(myAnt,
							nearestHillTile);
					if (directions.size() > 0) {
						int largestDistance = Integer.MIN_VALUE;
						Aim largestAim = null;
						for (Aim aim : directions) {
							Aim direction = aim.getOpposite();
							if (ants.getIlk(myAnt, direction).isUnoccupied()) {
								int distance = ants.getDistance(
										nearestHillTile,
										ants.getTile(myAnt, direction));
								if (distance > largestDistance) {
									largestDistance = distance;
									largestAim = direction;
								}
							}
						}
						if (largestAim != null) {
							plannedOrders.put(myAnt, largestAim);
							continue;
						}
					}
				}

				// default: any unoccupied direction
				for (Aim direction : Aim.values()) {
					if (ants.getIlk(myAnt, direction).isUnoccupied()) {
						plannedOrders.put(myAnt, direction);
						continue;
					}
				}
			}
			new Fight().fight(ants, plannedOrders, round);

			Set<Tile> occupied = new HashSet<Tile>();
			for (Map.Entry<Tile, Aim> entry : plannedOrders.entrySet()) {
				Tile tile = entry.getKey();
				Aim direction = entry.getValue();
				Tile nextTile = ants.getTile(tile, direction);
				if (occupied.contains(nextTile)) {
					// no action
				} else {
					ants.issueOrder(tile, direction);
				}
				occupied.add(nextTile);
			}

	}

	private int getMyAntsCount(Ants ants) {
		return ants.getMyAnts().size();
	}

}
