package search;

import search.path.Path;


public interface BreadthFirstSearchListener<T> {

	public void addPathPerformed(Path<T> path);
}
