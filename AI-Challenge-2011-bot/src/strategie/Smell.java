package strategie;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import search.BreadthFirstSearch;
import search.BreadthFirstSearchListener;
import search.Path;
import search.PathHelper;
import search.Problem;
import search.TimeoutStopCriteria;

public class Smell<T> {

	private static class SearchResults<T> {
		private final BreadthFirstSearch<T> search;
		private final Map<T, Path<T>> paths = new HashMap<T, Path<T>>();

		public SearchResults(BreadthFirstSearch<T> search) {
			this.search = search;

			// search for adding entries to searchResult.paths
			BreadthFirstSearchListener<T> breadthFirstSearchListener = new BreadthFirstSearchListener<T>() {
				@Override
				public void addPathPerformed(Path<T> path) {
					paths.put(path.getEnd(), path);
				}
			};
			search.addListener(breadthFirstSearchListener);
		}

	}

	private final Map<T, SearchResults<T>> searches = new HashMap<T, SearchResults<T>>();

	public void removeCachedEntriesIfNotExisting(Set<T> existing) {
		Set<T> differenceSet = new HashSet<T>(searches.keySet());
		differenceSet.removeAll(existing);
		for (T difference : differenceSet) {
			searches.remove(difference);
		}
	}

	public void createSmell(final Problem<T> problem, int searchTime,
			T initial) {
		final SearchResults<T> searchResults;
		if (searches.containsKey(initial)) {
			searchResults = searches.get(initial);
		} else {
			searchResults = new SearchResults<T>(new BreadthFirstSearch<T>(
					Collections.<T> emptySet(), initial, problem));
			searches.put(initial, searchResults);
		}

		searchResults.search.search(new TimeoutStopCriteria<T>(searchTime));
	}

	/**
	 * Returns cached shortest path from start to nearest target. If more that
	 * one path has the same length, then one path is picked non deterministic.
	 * 
	 * @param start
	 * @return <code>null</code> means no path found
	 */
	public Path<T> getShortestPath(T start) {
		Path<T> shortestPath = null;
		for (SearchResults<T> entry : searches.values()) {
			Path<T> path = entry.paths.get(start);
			if (path == null) {
				continue;
			}
			shortestPath = pathFound(shortestPath, path);
		}
		if (shortestPath != null) {
			shortestPath = PathHelper.invert(shortestPath);
		}
		return shortestPath;
	}



	private Path<T> pathFound(Path<T> shortestPath, Path<T> path) {
		if (shortestPath == null) {
			return path;
		}
		if (path.getLength() < shortestPath.getLength()) {
			return path;
		}
		return shortestPath;
	}
}