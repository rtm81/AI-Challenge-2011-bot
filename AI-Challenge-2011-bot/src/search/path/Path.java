package search.path;


public interface Path<T extends Number & Comparable<T>, U> extends
		Comparable<Path<T, U>> {

	// public static final Path EMPTY_PATH = new EmptyPath();
	//
	// private static final class EmptyPath<T extends Number & Comparable<T>, U>
	// extends Path<T, U> {
	// @Override
	// public int compareTo(Path<T, U> o) {
	// throw new UnsupportedOperationException();
	// }
	//
	// @Override
	// protected Path<T, U> create(U end) {
	// throw new UnsupportedOperationException();
	// }
	//
	// @Override
	// public T getLength() {
	// throw new UnsupportedOperationException();
	// }
	//
	// @Override
	// public U getEnd() {
	// throw new UnsupportedOperationException();
	// }
	//
	// @Override
	// public Path<T, U> getParent() {
	// throw new UnsupportedOperationException();
	// }
	// }
	//
	// public static <T extends Number & Comparable<T>, U> Path<T, U>
	// getEmptyPath() {
	// return EMPTY_PATH;
	// }
	//
	// public static <T extends Number & Comparable<T>, U> Path<T, U> create(U
	// end) {
	// return null;
	// }
	//
	// public static <T extends Number & Comparable<T>, U> Path<T, U> create(
	// Path<T, U> path, U end) {
	// if (path == null) {
	// return null;
	// } else {
	// return path.create(end);
	// }
	// }

	public abstract Path<T, U> create(U end);

	public abstract T getLength();

	public abstract U getEnd();

	public abstract Path<T, U> getParent();

}