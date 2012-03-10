package search.path.impl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import search.PathBuilder;
import search.path.Path;

public class IntPathBuilder<U> extends PathBuilder<Integer, U> {

	@Override
	public Path<Integer, U> create(U initial) {
		return new IntPath<U>(null, initial);
	}

	@Override
	public Path<Integer, U> create(Path<Integer, U> path, U initial) {
		return new IntPath<U>(path, initial);
	}

	@Override
	public Path<Integer, U> invert(final Path<Integer, U> path) {
		Deque<U> stack = new ArrayDeque<U>();
		for (Path<Integer, U> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<Integer, U> resultPath = null;
		for (Iterator<U> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = this.create(resultPath, iter.next());
		}
		return resultPath;
	}

	@Override
	public IntPath<U> create(final U... args) {
		IntPath<U> path = null;
		for (U arg : args) {
			path = new IntPath<U>(path, arg);
		}
		return path;
	}
}
