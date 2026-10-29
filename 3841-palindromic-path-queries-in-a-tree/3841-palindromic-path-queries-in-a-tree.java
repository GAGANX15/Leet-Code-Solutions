import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[] depth = new int[n], tin = new int[n], tout = new int[n];
        int[] parent = new int[n], order = new int[n], size = new int[n];
        int[] prefix = new int[n];
        Arrays.fill(parent, -1);

        int[] stack = new int[n];
        int top = 0, timer = 0;
        stack[top++] = 0;
        parent[0] = 0;
        prefix[0] = 1 << (s.charAt(0) - 'a');

        while (top > 0) {
            int u = stack[--top];
            tin[u] = timer;
            order[timer++] = u;
            size[u] = 1;

            for (int v : adj[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                prefix[v] = prefix[u] ^ (1 << (s.charAt(v) - 'a'));
                up[0][v] = u;

                for (int j = 1; j < LOG; j++)
                    up[j][v] = up[j - 1][up[j - 1][v]];

                stack[top++] = v;
            }
        }

        for (int i = n - 1; i > 0; i--)
            size[parent[order[i]]] += size[order[i]];

        for (int u = 0; u < n; u++)
            tout[u] = tin[u] + size[u] - 1;

        up[0][0] = 0;
        for (int j = 1; j < LOG; j++)
            up[j][0] = 0;

        Fenwick bit = new Fenwick(n);
        char[] chars = s.toCharArray();
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = (1 << (chars[u] - 'a')) ^ (1 << (c - 'a'));
                bit.update(tin[u] + 1, delta);
                bit.update(tout[u] + 2, delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int w = lca(u, v, depth, up, LOG);

                int mask = prefix[u] ^ bit.query(tin[u] + 1)
                         ^ prefix[v] ^ bit.query(tin[v] + 1)
                         ^ (1 << (chars[w] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private int lca(int u, int v, int[] depth, int[][] up, int LOG) {
        if (depth[u] < depth[v]) {
            int t = u;
            u = v;
            v = t;
        }

        int diff = depth[u] - depth[v];
        for (int j = 0; j < LOG; j++)
            if ((diff & (1 << j)) != 0) u = up[j][u];

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void update(int i, int val) {
            for (; i <= n + 1; i += i & -i)
                tree[i] ^= val;
        }

        int query(int i) {
            int res = 0;
            for (; i > 0; i -= i & -i)
                res ^= tree[i];
            return res;
        }
    }
}