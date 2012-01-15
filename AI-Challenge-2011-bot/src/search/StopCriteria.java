package search;

import search.path.impl.Path;

public interface StopCriteria<T extends Number & Comparable<T>, U> {

	void init();

	boolean isStop();
	
	boolean isStop(Path<T, U> path);

}