package search;

import search.path.Path;

public class DirectionStart<T extends Number & Comparable<T>, U, V> extends
		GetNextOnPath<T, U, V> {

	public DirectionStart(GetNextOnPathProblem<T, U, V> problem) {
		super(problem);
	}

	@Override
	public V get(Path<T, U> path, U currentPosition) {
		for (Path<T, U> tempPath = path; isNotLastPosition(tempPath); tempPath = tempPath
				.getParent()) {
			if (tempPath.getEnd().equals(currentPosition)) {
				V result = problem.getDirection(currentPosition,
						tempPath.getParent());
				if (result != null)
					return result;
			}

		}
		return null;
	}

}