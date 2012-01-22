package search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import search.path.Path;
import search.path.impl.IntPath;

public class PathBuilder<T extends Number & Comparable<T>, U> {
	
	public Path<T, U> create(U initial) {
		return new IntPath<T, U>(null, initial);
	}

	public Path<T, U> create(Path<T, U> path, U initial) {
		return new IntPath<T, U>((IntPath<T, U>) path, initial);
	}

	public Path<T, U> invert(final Path<T, U> path) {
		Deque<U> stack = new ArrayDeque<U>();
		for (Path<T, U> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<T, U> resultPath = null;
		for (Iterator<U> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = this.create(resultPath, iter.next());
		}
		return resultPath;
	}

	public IntPath<T, U> create(
			final U... args) {
		IntPath<T, U> path = null;
		for (U arg : args) {
			path = new IntPath<T, U>(path, arg);
		}
		return path;
	}
}
