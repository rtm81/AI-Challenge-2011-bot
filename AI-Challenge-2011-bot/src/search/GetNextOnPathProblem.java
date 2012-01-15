package search;

import search.path.impl.Path;

public interface GetNextOnPathProblem<T extends Number & Comparable<T>, U, V> {

	public V getDirection(U current, Path<T, U> path);
}