package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Main {
    static int MAX_DISCS = 12;

    static int[] c = new int[1 << (MAX_DISCS * 2)];

    private static int get(int state, int index) {
        return (state >> (index * 2)) & 3;
    }

    private static int set(int state, int index, int value) {
        return (state & ~(3 << (index * 2))) | (value << (index * 2));
    }

    private static int sign(int x) {
        if (x == 0) return 0;
        return x > 0 ? 1 : -1;
    }

    private static int incr(int x) {
        if (x < 0) return x - 1;
        return x + 1;
    }

    private static int bidir(int numOfCircle, int begin, int end) {
        if (begin == end) return 0;
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(c, 0);
        queue.offer(begin);
        c[begin] = 1;
        queue.offer(end);
        c[end] = -1;

        while (!queue.isEmpty()) {
            int here = queue.poll();

            int[] top = {-1, -1, -1, -1};
            for (int i = numOfCircle - 1; i >= 0; i--) {
                top[get(here, i)] = i;
            }

            for (int i = 0; i < 4; i++) {
                if (top[i] != -1) {
                    for (int j = 0; j < 4; j++) {
                        if (i != j && (top[j] == -1 || top[j] > top[i])) {
                            int there = set(here, top[i], j);
                            if (c[there] == 0) {
                                c[there] = incr(c[here]);
                                queue.offer(there);
                            } else if (sign(c[there]) != sign(c[here]))
                                return Math.abs(c[there]) + Math.abs(c[here]) - 1;
                        }
                    }
                }
            }
        }

        return -1;
    }
}
