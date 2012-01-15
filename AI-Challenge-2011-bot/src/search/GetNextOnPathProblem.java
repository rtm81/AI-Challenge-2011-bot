package search;

import search.path.Path;

public interface GetNextOnPathProblem<T, U> {

	public U getDirection(T current, Path<T> path);
}