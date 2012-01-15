package search.path;

public class Path<T> implements Comparable<Path<T>> {
	private final Path<T> parent;
	private final int length;
	private final T end;

	public Path(final Path<T> parent, final T end, Integer number) {
		if (end == null)
			throw new NullPointerException("end may not be null");
		this.parent = parent;
		if (parent == null) {
			length = 0;
		} else {
			length = parent.length + number;
		}
		this.end = end;
	}

	public Path(final Path<T> parent, final T end) {
		this(parent, end, Integer.valueOf(1));
	}

	public static <T> Path<T> create(final T... args) {
		Path<T> path = null;
		for (T arg : args) {
			path = new Path<T>(path, arg);
		}
		return path;
	}

	public int getLength() {
		return length;
	}

	public T getEnd() {
		return end;
	}

	public Path<T> getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "[" + stepsToString() + ", length=" + length + "]";
	}

	private String stepsToString() {
		return "" + end + (parent == null ? "" : "<-" + parent.stepsToString());
	}

	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 */
	@Override
	public int compareTo(Path<T> o) {
		return (length < o.length ? -1 : (length == o.length ? 0 : 1));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + length;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Path<T> other = (Path<T>) obj;
		if (end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!end.equals(other.end)) {
			return false;
		}
		if (length != other.length) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}

}