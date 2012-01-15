package search;

import search.path.Path;



public class DirectionStart<T, U> extends GetNextOnPath<T, U> {

	public DirectionStart(GetNextOnPathProblem<T, U> problem) {
		super(problem);
	}

	@Override
	public U get(Path<T> path, T currentPosition) {
		for (Path<T> tempPath = path; isNotLastPosition(tempPath); tempPath = tempPath
				.getParent()) {
			if (tempPath.getEnd().equals(currentPosition)) {
				U result = problem.getDirection(currentPosition,
						tempPath.getParent());
				if (result != null)
					return result;
			}

		}
		return null;
	}

}