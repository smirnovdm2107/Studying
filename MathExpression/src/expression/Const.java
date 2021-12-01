package expression;

public class Const implements Expression, ToMiniString{
    final int num;
    public Const(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }


    public int evaluate(int x, int y, int z) {
        return num;
    }

    public int evaluate(int var) {
        return num;
    }

    @Override
    public String toMiniString() {
        return Integer.toString(num);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            return obj.toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
