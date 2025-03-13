
import aima.search.framework.GoalTest;

public class goalTest implements GoalTest {
    public boolean isGoalState(Object aState){
        Estado estado =(Estado)aState;
        return false;
    }
}