package search.path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import search.path.impl.Path;


public class PathHelper {

	public static <T extends Number & Comparable<T>, U> Path<T, U> invert(
			Path<T, U> path) {
		Deque<U> stack = new ArrayDeque<U>();
		for (Path<T, U> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<T, U> resultPath = null;
		for (Iterator<U> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = new Path<T, U>(resultPath, iter.next());
		}
		return resultPath;
	}
}
