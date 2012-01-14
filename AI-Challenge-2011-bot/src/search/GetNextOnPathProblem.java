package search;

public interface GetNextOnPathProblem<T, U> {

	public U getDirection(T current, Path<T> path);
}