package ants;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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

		smell.createSmell(new ProblemAdapter(ants), foodTiles,
				searchTimePerFood);

		getMyAntsCount(ants);
		List<Ant> antsList = new LinkedList<Ant>();
		for (Tile myAnt : ants.getMyAnts()) {

			Ant ant = new Ant(myAnt);
			antsList.add(ant);

			Path<Integer, Tile> shortestPath = smell.getShortestPath(myAnt);

			if (shortestPath != null) {
				Aim aim = getNextOnPath.get(shortestPath, myAnt);
				if (aim != null) {
					ant.addOrder(Ant.OrderType.FOOD, aim);
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
					ant.addOrder(Ant.OrderType.OWN_HILL, randomAim);
					plannedOrders.put(myAnt, randomAim);
					continue;
				}
			}

			/**
			 * move away from nearest own hill
			 */
			Tile nearestHillTile = getNearest(ants, myAnt);

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

	private Tile getNearest(Ants ants, Tile myAnt) {
		return ants.getNearest(myAnt, ants.getMyHills());
	}

	private int getMyAntsCount(Ants ants) {
		return ants.getMyAnts().size();
	}

}
