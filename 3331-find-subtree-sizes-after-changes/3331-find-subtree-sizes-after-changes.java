class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }
        int[] last = new int[26];
        int[] prev = new int[n];
        int[] newParent = new int[n];
        int[] order = new int[n];
        int[] stack = new int[2 * n];
        Arrays.fill(last, -1);
        newParent[0] = -1;
        int top = 0, count = 0;
        stack[top++] = 0;
        while (top > 0) {
            int u = stack[--top];
            if (u < 0) {
                u = ~u;
                last[s.charAt(u) - 'a'] = prev[u];
                continue;
            }
            int c = s.charAt(u) - 'a';
            prev[u] = last[c];
            if (prev[u] != -1) {
                newParent[u] = prev[u];
            } else if (u != 0) {
                newParent[u] = parent[u];
            }
            last[c] = u;
            order[count++] = u;
            stack[top++] = ~u;
            for (int v : tree[u]) {
                stack[top++] = v;
            }
        }
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            ans[newParent[u]] += ans[u];
        }
        return ans;
    }
}