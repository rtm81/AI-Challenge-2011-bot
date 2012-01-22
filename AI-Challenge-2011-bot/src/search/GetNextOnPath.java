package search;

import search.path.impl.Path;



public abstract class GetNextOnPath<T extends Number & Comparable<T>, U, V> {

	protected GetNextOnPathProblem<T, U, V> problem;

	protected GetNextOnPath(GetNextOnPathProblem<T, U, V> problem) {
		this.problem = problem;
	}

	public abstract V get(final Path<T, U> path, final U currentPosition);

	protected boolean isNotLastPosition(final Path<T, U> path) {
		return !isLastPosition(path);
	}

	private boolean isLastPosition(final Path<T, U> path) {
		return path.getParent() == null;
	}

	protected boolean isParentEqualsCurrentPosition(final U currentPosition,
			final Path<T, U> path) {
		return path.getParent().getEnd().equals(currentPosition);
	}
}