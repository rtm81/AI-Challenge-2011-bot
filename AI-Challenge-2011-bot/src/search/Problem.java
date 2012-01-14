package search;
import java.util.Set;

public interface Problem<T> {

	public Set<T> getActions(T end);

}
