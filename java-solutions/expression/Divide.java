package expression;

public class Divide extends Actions{
    public Divide(Unification left_expression, Unification right_expression){super(left_expression, "/", right_expression);}

    public int binaryOperation(int left_result, int right_result){
        return  left_result / right_result;
    }
}
