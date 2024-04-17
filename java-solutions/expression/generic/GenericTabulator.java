package expression.generic;

import expression.exceptions.ExpressionException;
import expression.generic.expression_generic.UnificationGeneric;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        switch (mode) {
            case "i" -> {
                ExpressionParserGeneric<Integer> parser = new ExpressionParserGeneric<>();
                return constructionTabulate(new IntegerGeneric(), parser.parse(new IntegerGeneric(), expression), x1, x2, y1, y2, z1, z2);
            }
            case "d" -> {
                ExpressionParserGeneric<Double> parser = new ExpressionParserGeneric<>();
                return constructionTabulate(new DoubleGeneric(), parser.parse(new DoubleGeneric(), expression), x1, x2, y1, y2, z1, z2);
            }
            case "u" -> {
                ExpressionParserGeneric<Integer> parser = new ExpressionParserGeneric<>();
                return constructionTabulate(new UIntegerGeneric(), parser.parse(new UIntegerGeneric(), expression), x1, x2, y1, y2, z1, z2);
            }
            case "b" -> {
                ExpressionParserGeneric<Byte> parser = new ExpressionParserGeneric<>();
                return constructionTabulate(new ByteGeneric(), parser.parse(new ByteGeneric(), expression), x1, x2, y1, y2, z1, z2);
            }
            default -> {
                ExpressionParserGeneric<BigInteger> parser = new ExpressionParserGeneric<>();
                return constructionTabulate(new BigIntegerGeneric(), parser.parse(new BigIntegerGeneric(), expression), x1, x2, y1, y2, z1, z2);
            }
        }
    }

    private <T> Object[][][] constructionTabulate(Generics<T> gen, UnificationGeneric<T> expr, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    try {
                        result[i][j][k] = eval(gen, expr, x1 + i, y1 + j, z1 + k);
                    } catch (ExpressionException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }

    private <T> Object eval(Generics<T> gen, UnificationGeneric<T> expr, int x1, int y1, int z1) throws ExpressionException {
        return expr.evaluate(gen.convert(x1), gen.convert(y1), gen.convert(z1));
    }
}