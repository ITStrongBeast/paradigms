package expression;

import java.util.List;
import java.util.Objects;

public class Const extends Expre{
    int con;
    public Const(int con){
        super(con);
        this.con = con;
    }
    @Override
    public int evaluate(int x) {
        return con;
    }

    @Override
    public int evaluate(int x, int y, int z){
        return  con;
    }

    @Override
    public String toString() {
        return String.valueOf(con);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return con;
    }
}
