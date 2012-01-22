package search;

import search.path.Path;




public class DirectionEnd<T extends Number & Comparable<T>, U, V> extends
		GetNextOnPath<T, U, V> {

	public DirectionEnd(GetNextOnPathProblem<T, U, V> problem) {
		super(problem);
	}

	@Override
	public V get(Path<T, U> path, U currentPosition) {
		for (Path<T, U> tempPath = path; isNotLastPosition(tempPath); tempPath = tempPath
				.getParent()) {
			if (isParentEqualsCurrentPosition(currentPosition, tempPath)) {
				V result = problem.getDirection(currentPosition, tempPath);
				if (result != null)
					return result;
			}

		}
		return null;
	}

}