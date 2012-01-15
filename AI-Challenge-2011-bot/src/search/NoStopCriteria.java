package search;

import search.path.Path;

public class NoStopCriteria<T> implements StopCriteria<T> {

	@Override
	public void init() {
	}

	@Override
	public boolean isStop() {
		return false;
	}

	@Override
	public boolean isStop(Path<T> path) {
		return false;
	}
}