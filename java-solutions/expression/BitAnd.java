package expression;

public class BitAnd extends Actions{
    public BitAnd(Unification left_expression, Unification right_expression){
        super(left_expression, "&", right_expression);
    }

    @Override
    public int binaryOperation(int left_result, int right_result) {
        return left_result & right_result;
    }
}
