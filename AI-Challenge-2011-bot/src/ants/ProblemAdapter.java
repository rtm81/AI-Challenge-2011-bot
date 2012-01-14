package ants;
import java.util.HashSet;
import java.util.Set;


import search.Problem;

public class ProblemAdapter implements Problem<Tile> {
	private final Ants ants;
	private final Set<Aim> actions;

	public ProblemAdapter(Ants ants) {
		this.ants = ants;
		actions = new HashSet<Aim>();
		for (Aim aim : Aim.values()) {
			actions.add(aim);
		}
	}

	@Override
	public Set<Tile> getActions(Tile end) {
		Set<Tile> result = new HashSet<Tile>();
		for (Aim direction : Aim.values()) {
			Ilk possibleStep = ants.getIlk(end, direction);
			if (possibleStep == null)
				continue;
			Tile newTile = ants.getTile(end, direction);
			if (newTile == null)
				continue;
			if (possibleStep.isPassable()) {
				result.add(newTile);
			}
		}
		return result;
	}
}
