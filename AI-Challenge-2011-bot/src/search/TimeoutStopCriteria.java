package search;

import search.path.Path;


public class TimeoutStopCriteria<T extends Number & Comparable<T>, U>
		implements StopCriteria<T, U> {

	private final long allowedSearchtime;
	private long startTime;

	public TimeoutStopCriteria(long allowedSearchtime) {
		this.allowedSearchtime = allowedSearchtime;
	}

	@Override
	public void init() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public boolean isStop() {
		return isTimeoutReached(allowedSearchtime, startTime);
	}

	private boolean isTimeoutReached(final long allowedSearchtime,
			final long startTime) {
		return (System.currentTimeMillis() - startTime) > allowedSearchtime;
	}

	@Override
	public boolean isStop(Path<T, U> path) {
		return false;
	}
}