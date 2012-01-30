package search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import search.path.Path;
import search.path.impl.IntPath;

public class PathBuilder<T extends Number & Comparable<T>, U> {
	
	public Path<Integer, U> create(U initial) {
		return new IntPath<U>(null, initial);
	}

	public Path<Integer, U> create(Path<Integer, U> path, U initial) {
		return new IntPath<U>(path, initial);
	}

	public Path<Integer, U> invert(final Path<T, U> path) {
		Deque<U> stack = new ArrayDeque<U>();
		for (Path<T, U> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<Integer, U> resultPath = null;
		for (Iterator<U> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = this.create(resultPath, iter.next());
		}
		return resultPath;
	}

	public IntPath<U> create(
			final U... args) {
		IntPath<U> path = null;
		for (U arg : args) {
			path = new IntPath<U>(path, arg);
		}
		return path;
	}
}
