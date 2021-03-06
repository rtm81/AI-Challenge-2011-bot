package search;

import search.path.impl.Path;


public interface BreadthFirstSearchListener<T extends Number & Comparable<T>, U> {

	public void addPathPerformed(Path<T, U> path);
}
