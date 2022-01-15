import java.util.Arrays;
public class IntList {
    private int[] arr;
    private int count;
    public IntList() {
        arr = new int[1];
        int i = 0;
    }
    public void add(int n) {
        if (count == arr.length) {
            arr = Arrays.copyOf(arr, arr.length*2);
        }
        arr[count] = n;
        count++;
    }
    public int size() {
        return count;
    }

    public int get(int i)throws IndexOutOfBoundsException {
        if (i < 0 || i >= count) {
            throw new IndexOutOfBoundsException("Your index out of List!");
        }
        return arr[i];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void remove(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= count) {
            throw new IndexOutOfBoundsException("Your index out of List!");
        }
        for (int j = i + 1 ; j < count; j++) {
            arr[j-1] = arr[j];
        }
        count--;
    }
    public void clear() {
        arr = new int[1];
        count = 0;
    }

}
