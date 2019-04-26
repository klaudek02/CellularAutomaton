package visualization;

import javafx.collections.ObservableList;

import java.util.Arrays;

public class GameOfLife {

    int[] neighbours;
    ObservableList<ObservableList<Integer>> array;
    public GameOfLife(ObservableList<ObservableList<Integer>> array) {
        this.neighbours = new int[8];
        this.array = array;
    }

    void periodicCondition(int x, int y, int n, int m)
    {
        if(x-1 < 0){
            if(y-1 < 0)
                neighbours[0] = array.get(n-1).get(m-1);
            else
                neighbours[0] = array.get(x).get(m-1);
        }
        else if(x == n)
        {

        }
        else if(y-1< 0)
        {

        }
        else if(y == m)
        {

        }
        else
        {
            neighbours[0] = array.get(x-1).get(y-1);
        }
    }
    void checkRules()
    {
        int i = 0;
        //periodicCondition();
        int numberOfNeighbours = Arrays.stream(neighbours).filter(x-> x == 1).sum();
        if(i == 1)
        {
            if(numberOfNeighbours > 3 || numberOfNeighbours < 2)
                i = 0;
            else
                i = 1;
        }
        else
        {
            if( numberOfNeighbours == 3)
                i = 1;
        }
    }
}
