package search;

import search.path.Path;

public class NoStopCriteria<T extends Number & Comparable<T>, U> implements
		StopCriteria<T, U> {

	@Override
	public void init() {
	}

	@Override
	public boolean isStop() {
		return false;
	}

	@Override
	public boolean isStop(Path<T, U> path) {
		return false;
	}
}