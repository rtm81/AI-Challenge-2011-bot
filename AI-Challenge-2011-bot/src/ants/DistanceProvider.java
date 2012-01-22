package ants;

import java.util.Set;

public abstract class DistanceProvider<T> {

	/**
	 * Calculates distance between two locations on the game map.
	 * 
	 * @param t1
	 *            one location on the game map
	 * @param t2
	 *            another location on the game map
	 * 
	 * @return distance between <code>t1</code> and <code>t2</code>
	 */
	public abstract int getDistance(T t1, T t2);

	public T getNearest(T start, Set<T> ends) {
		T nearest = null;
		int nearestDistance = Integer.MAX_VALUE;
		for (T end : ends) {
			int distance = this.getDistance(start, end);
			if (distance < nearestDistance) {
				nearestDistance = distance;
				nearest = end;
			}
		}
		return nearest;
	}

}