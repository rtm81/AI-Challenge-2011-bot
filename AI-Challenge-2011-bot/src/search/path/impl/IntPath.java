package search.path.impl;

import search.path.Path;

public class IntPath<T extends Number & Comparable<T>, U> implements Path<T, U> {
	private final IntPath<T, U> parent;
	private final int length;
	private final U end;

	public IntPath(final IntPath<T, U> parent, final U end, int number) {
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

	public IntPath(final IntPath<T, U> parent, final U end) {
		this(parent, end, 1);
	}

	@Override
	public Path<T, U> create(U end) {
		return new IntPath<T, U>(this, end);
	}

	@Override
	public T getLength() {
		return (T) Integer.valueOf(length);
	}

	@Override
	public U getEnd() {
		return end;
	}

	@Override
	public IntPath<T, U> getParent() {
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
	public int compareTo(search.path.Path<T, U> o) {
		return (length < (Integer) o.getLength() ? -1 : (length == (Integer) o
				.getLength() ? 0 : 1));
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
		IntPath<T, U> other = (IntPath<T, U>) obj;
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