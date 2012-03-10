package search;

import search.path.Path;
import search.path.impl.IntPath;

public abstract class PathBuilder<T extends Number & Comparable<T>, U> {
	
	public abstract Path<T, U> create(U initial);

	public abstract Path<T, U> create(Path<T, U> path, U initial);

	public abstract Path<T, U> invert(final Path<T, U> path);

	public abstract IntPath<U> create(final U... args);
}
