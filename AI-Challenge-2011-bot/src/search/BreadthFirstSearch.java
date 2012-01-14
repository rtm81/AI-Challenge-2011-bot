package search;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;


public class BreadthFirstSearch<T> {
	
	private final Set<T> goal;
	private final Set<T> explored = new HashSet<T>();
	private final PriorityQueue<Path<T>> frontier = new PriorityQueue<Path<T>>();
	private final Problem<T> problem;
	private final Set<BreadthFirstSearchListener<T>> breadthFirstSearchListeners = new HashSet<BreadthFirstSearchListener<T>>();
	
	public BreadthFirstSearch(final Set<T> goal, final T initial,
			final Problem<T> problem) {
		this.goal = goal;
		this.frontier.offer(new Path<T>(null, initial));
		this.problem = problem;
	}
	
	public Path<T> search() {
		return search(new NoStopCriteria<T>());
	}

	public Path<T> search(StopCriteria<T> stopCriteria) {
		stopCriteria.init();
		
		while (true) {
			if (stopCriteria.isStop() || isFrontierEmpty()) {
				break;
			}
			Path<T> path = removeChoice();
			if (path == null || stopCriteria.isStop(path)) {
				break;
			}
			T end = path.getEnd();
			addToExplored(end);
			if (isGoal(end)) {
				return path;
			}
			
			for (T action : problem.getActions(end)) {
				addToFrontierUnlessNotAlreadyExplored(path, action);
			}
		}
		// TODO: avoid null
		return null;
	}

	public void addListener(
			BreadthFirstSearchListener<T> breadthFirstSearchListener) {
		breadthFirstSearchListeners.add(breadthFirstSearchListener);
	}

	public void removeListener(
			BreadthFirstSearchListener<T> breadthFirstSearchListener) {
		breadthFirstSearchListeners.remove(breadthFirstSearchListener);
	}

	private void addToFrontierUnlessNotAlreadyExplored(Path<T> path, T action) {
		if (!(isInExplored(action) || isInFrontier(action))) {
			Path<T> newPath = new Path<T>(path, action);
			frontier.offer(newPath);
			// addToExplored(action);
			for (BreadthFirstSearchListener<T> breadthFirstSearchListener : breadthFirstSearchListeners) {
				breadthFirstSearchListener.addPathPerformed(newPath);
			}
		}
	}

	private boolean isInExplored(T action) {
		return explored.contains(action);
	}

	private boolean isInFrontier(T action) {
		for (Path<T> path : frontier) {
			if (action.equals(path.getEnd()))
				return true;
		}
		return false;
	}

	private boolean isFrontierEmpty() {
		return frontier.isEmpty();
	}

	private void addToExplored(T end) {
		explored.add(end);
	}

	private boolean isGoal(final T head) {
		return goal.contains(head);
	}

	private Path<T> removeChoice() {
		return frontier.poll();
	}
}