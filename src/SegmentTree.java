

import java.util.Scanner;

import static java.lang.Math.*;
import static java.util.Arrays.*;


// Простейшее дерево отрезков:
public class SegmentTree {
    // реализация для нахождения суммы отрезков
    int n;
    long[] a;
    long[] segTree;

    void build(long[] a, int n) {
        this.a = copyOf(a, n);
        this.n = n;
        segTree = new long[n * 4];
        build(1, 0, n - 1);
    }

    void build(int id, int l, int r) {
        if (l == r)
            segTree[id] = a[l];
        else {
            int mid = (l + r) / 2;
            build(id * 2, l, mid);
            build(id * 2 + 1, mid + 1, r);
            segTree[id] = segTree[id * 2] + segTree[id * 2 + 1];
        }
    }

    long sum(int l, int r) {
        return sum(1, 0, n - 1, l, r);
    }

    long sum(int id, int tl, int tr, int l, int r) {
        if (l > r)
            return 0;
        if (l == tl && r == tr)
            return segTree[id];
        int mid = (tl + tr) / 2;
        return sum(id * 2, tl, mid, l, min(r, mid)) + sum(id * 2 + 1, mid + 1, tr, max(l, mid + 1), r);
    }

    void upd(int pos, long nv) {
        upd(1, 0, n - 1, pos, nv);
    }

    void upd(int id, int tl, int tr, int pos, long nv) {
        if (tl == tr)
            segTree[id] = nv;
        else {
            int tm = (tl + tr) / 2;
            if (pos <= tm)
                upd(id * 2, tl, tm, pos, nv);
            else
                upd(id * 2 + 1, tm + 1, tr, pos, nv);
            segTree[id] = segTree[id * 2] + segTree[id * 2 + 1];
        }
    }

    public void solve() {
        n = 6;
        a = new long[]{2,7,6,4,1,3};
        int m = 5;
        build(a,n);
        for (int i = 0; i < m; i++) {
            System.out.println(sum(1,4));
            upd(i,3);
        }
    }

    public static void main(String[] args) {
        new SegmentTree().solve();
    }
}
