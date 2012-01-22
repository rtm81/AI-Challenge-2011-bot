package ants;
import java.util.List;

import search.GetNextOnPathProblem;
import search.path.Path;

public class GetNextOnPathProblemAdapter implements
		GetNextOnPathProblem<Integer, Tile, Aim> {

	private final Ants ants;

	public GetNextOnPathProblemAdapter(Ants ants) {
		this.ants = ants;
	}

	@Override
	public Aim getDirection(Tile current, Path<Integer, Tile> path) {
		List<Aim> directions = ants.getDirections(current, path.getEnd());
		if (directions.size() > 0
				&& ants.getIlk(current, directions.get(0)).isUnoccupied()) {
			return directions.get(0);
		}
		return null;
	}

}
