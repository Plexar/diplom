package eu.burbach.diplom.application;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<Cell> list = new ArrayList<>();

    public List<Cell> getCells()
    {
        return list;
    }
}
