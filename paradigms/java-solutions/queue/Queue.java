package queue;
/*
Model: a[1] ... a[n]
Invariant: for i=1..n: a[i] != null


Let immutable(n): for i=1..n a'[i] == a[i]

    Pred: element != null;
    Post: n' == n + 1 && immutable(n) && a[n'] == element
        enqueue(element)

    Pred: n > 0
    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
        dequeue()

    Pred: n > 0
    Post: n' == n && immutable(n) && R == a[n]
        element()

    Pred: element != null
    Post: n' = n + 1 && immutable(n) && a[n'] = element
        push(element)

    Pred: size > 0
    Post: n' == n && immutable(n') && R == a[1]
        peek()

    Pred: size > 0
    Post: R == a[n] && n' == n - 1 && immutable(n')
        remove()

    Pred: true
    Post: R == n && n' == n && immutable(n)
        size()

    Pred: true
    Post: R == (n == 0) && n' == n && immutable(n)
        isEmpty()

    Pred: true
    Post: n' == 0
        clear()

 */

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {

    //    Pred: element != null;
    //    Post: n' == n + 1 && immutable(n) && a[n'] == element
    void enqueue(Object obj);

    //    Pred: n > 0
    //    Post: n' == n && immutable(n) && R == a[n]
    Object element();

    //    Pred: n > 0
    //    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
    Object dequeue();

    //    Pred: true
    //    Post: R == n && n' == n && immutable(n)
    int size();

    //    Pred: true
    //    Post: R == (n == 0) && n' == n && immutable(n)
    boolean isEmpty();

    //    Pred: true
    //    Post: n' == 0
    void clear();

    //  Pred: predicate != null
    //  Post: R == b[m] && multiset A = { a[i] belong to A <=> predicate.test(a[i]) } == multiset B = { for all b[i] }
    // && (for all i1, i2: i1 <= n && i2 <= n && i1 < i2
    // && predicate.test(a[i1]) && predicate.test(a[j1]) && a[i1] == b[i2] && a[j1] == b[j2] && i1 < j1 => i2 < j2)
    // && immutable(n)
    Queue filter(Predicate predicate);

    //  Pred: function != null
    //  Post: R == b[n] && multiset A = { for all function.apply(a[i]) } == multiset B = { for all b[i] }
    // && (for all i1, i2: i1 <= n && i2 <= n && i1 < j1
    // :NOTE: Неправда
    // && function.apply(a[i1]) == b[i2] && function.apply(a[j1]) == b[j2] => i2 < j2)
    // && immutable(n)
    Queue map(Function function);



}
