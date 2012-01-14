package search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class PathHelper {

	public static <T> Path<T> invert(Path<T> path) {
		Deque<T> stack = new ArrayDeque<T>(path.getLength());
		for (Path<T> tempPath = path; tempPath != null; tempPath = tempPath
				.getParent()) {
			stack.push(tempPath.getEnd());
		}
		Path<T> resultPath = null;
		for (Iterator<T> iter = stack.descendingIterator(); iter.hasNext();) {
			resultPath = new Path<T>(resultPath, iter.next());
		}
		return resultPath;
	}
}
