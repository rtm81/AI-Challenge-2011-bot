package search;



public class DirectionEnd<T, U> extends GetNextOnPath<T, U> {

	public DirectionEnd(GetNextOnPathProblem<T, U> problem) {
		super(problem);
	}

	@Override
	public U get(Path<T> path, T currentPosition) {
		for (Path<T> tempPath = path; isNotLastPosition(tempPath); tempPath = tempPath
				.getParent()) {
			if (isParentEqualsCurrentPosition(currentPosition, tempPath)) {
				U result = problem.getDirection(currentPosition, tempPath);
				if (result != null)
					return result;
			}

		}
		return null;
	}

}