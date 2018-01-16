package eu.burbach.diplom.application;

public class Cell {
    private final String value;

    public Cell(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
