package ants;

import java.util.Set;

public abstract class DistanceProvider<T> {

	/**
	 * Calculates distance between two locations on the game map.
	 * 
	 * @param t1 one location on the game map
	 * @param t2 another location on the game map
	 * 
	 * @return distance between <code>t1</code> and <code>t2</code>
	 */
	public abstract int getDistance(T t1, T t2);

	/**
	 * @return null-possible
	 */
	public T getNearest(final T start, final Set<T> ends) {
		T nearest = null;
		int nearestDistance = Integer.MAX_VALUE;
		for (T endTile : ends) {
			final int distance = this.getDistance(start,
					endTile);
			if (distance < nearestDistance) {
				nearestDistance = distance;
				nearest = endTile;
			}
		}
		return nearest;
	}

}