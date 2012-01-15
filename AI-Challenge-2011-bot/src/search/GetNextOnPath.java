package search;

import search.path.Path;



public abstract class GetNextOnPath<T, U> {

	protected GetNextOnPathProblem<T, U> problem;

	protected GetNextOnPath(GetNextOnPathProblem<T, U> problem) {
		this.problem = problem;
	}

	public abstract U get(final Path<T> path, final T currentPosition);

	protected boolean isNotLastPosition(final Path<T> path) {
		return !isLastPosition(path);
	}

	private boolean isLastPosition(final Path<T> path) {
		return path.getLength() == 0;
	}

	protected boolean isParentEqualsCurrentPosition(final T currentPosition,
			final Path<T> path) {
		return path.getParent().getEnd().equals(currentPosition);
	}
}