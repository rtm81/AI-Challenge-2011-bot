package search;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import search.path.Path;


/**
 * 
 * Not threadsafe!
 * 
 * @author christopher.roscoe
 * 
 * @param <T>
 *            the type of the length of {@link Path}
 * @param <U>
 *            the type of the node of {@link Path}
 */
public class BreadthFirstSearch<T extends Number & Comparable<T>, U> {
	
	private final Set<U> goal;
	private final Set<U> explored = new HashSet<U>();
	private final PriorityQueue<Path<T, U>> frontier = new PriorityQueue<Path<T, U>>();
	private final Problem<U> problem;
	private final Set<BreadthFirstSearchListener<T, U>> breadthFirstSearchListeners = new HashSet<BreadthFirstSearchListener<T, U>>();
	
	public BreadthFirstSearch(final Set<U> goal, final U initial,
			final Problem<U> problem, final PathBuilder<T, U> pathBuilder) {
		this.goal = goal;
		this.frontier.offer(pathBuilder.create(initial));
		this.problem = problem;
	}
	
	/**
	 * search with breadth first search algorithm according to the given
	 * problem.
	 * 
	 * @return the first found path, that fulfills the problem requirements or
	 *         <code>null</code>, if no path is found.
	 */
	public Path<T, U> search() {
		return search(new NoStopCriteria<T, U>());
	}

	/**
	 * a special case of {@link BreadthFirstSearch#search()}
	 * 
	 * @param stopCriteria
	 *            {@link StopCriteria}
	 * @return the first found path, that fulfills the problem requirements or
	 *         <code>null</code>, if no path is found or the given stopCriteria
	 *         matches.
	 * @see BreadthFirstSearch#search()
	 */
	public Path<T, U> search(StopCriteria<T, U> stopCriteria) {
		stopCriteria.init();
		
		while (true) {
			if (isFrontierEmpty() || stopCriteria.isStop()) {
				break;
			}
			Path<T, U> path = removeChoice();
			if (path == null || stopCriteria.isStop(path)) {
				break;
			}
			U end = path.getEnd();
			addToExplored(end);
			if (isGoal(end)) {
				return path;
			}
			
			for (U action : problem.getActions(end)) {
				addToFrontierUnlessNotAlreadyExplored(path, action);
			}
		}
		// TODO: avoid null
		return null;
	}

	public void addListener(
			BreadthFirstSearchListener<T, U> breadthFirstSearchListener) {
		breadthFirstSearchListeners.add(breadthFirstSearchListener);
	}

	public void removeListener(
			BreadthFirstSearchListener<T, U> breadthFirstSearchListener) {
		breadthFirstSearchListeners.remove(breadthFirstSearchListener);
	}

	private void addToFrontierUnlessNotAlreadyExplored(Path<T, U> path, U action) {
		if (!(isInExplored(action) || isInFrontier(action))) {
			Path<T, U> newPath = path.create(action);
			frontier.offer(newPath);
			// addToExplored(action);
			for (BreadthFirstSearchListener<T, U> breadthFirstSearchListener : breadthFirstSearchListeners) {
				breadthFirstSearchListener.addPathPerformed(newPath);
			}
		}
	}

	private boolean isInExplored(U action) {
		return explored.contains(action);
	}

	private boolean isInFrontier(U action) {
		for (Path<T, U> path : frontier) {
			if (action.equals(path.getEnd()))
				return true;
		}
		return false;
	}

	private boolean isFrontierEmpty() {
		return frontier.isEmpty();
	}

	private void addToExplored(U end) {
		explored.add(end);
	}

	private boolean isGoal(final U head) {
		return goal.contains(head);
	}

	private Path<T, U> removeChoice() {
		return frontier.poll();
	}
}