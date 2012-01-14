package search;

public interface StopCriteria<T> {

	void init();

	boolean isStop();
	
	boolean isStop(Path<T> path);

}