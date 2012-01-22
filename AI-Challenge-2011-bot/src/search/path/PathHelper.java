package search.path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import search.PathBuilder;

public class PathHelper {

	public static <T extends Number & Comparable<T>, U> Path<T, U> invert(
			final Path<T, U> path, final PathBuilder<T, U> pathBuilder) {
		Deque<U> stack = new ArrayDeque<U>();
		for (Path<T, U> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<T, U> resultPath = null;
		for (Iterator<U> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = pathBuilder.create(resultPath, iter.next());
		}
		return resultPath;
	}
}
