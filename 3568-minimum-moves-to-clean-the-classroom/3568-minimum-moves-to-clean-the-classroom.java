class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length, n = classroom[0].length();
        int[][] lampBit = new int[m][n];
        int sr = 0, sc = 0, lampCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    sr = i;
                    sc = j;
                } else if (c == 'L') {
                    lampBit[i][j] = lampCount++;
                }
            }
        }

        if (lampCount == 0) return 0;

        int fullMask = (1 << lampCount) - 1;
        boolean[][][][] visited = new boolean[m][n][energy + 1][1 << lampCount];
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sr, sc, energy, fullMask});
        visited[sr][sc][energy][fullMask] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int[] cur = queue.poll();
                int r = cur[0], c = cur[1], e = cur[2], mask = cur[3];

                if (mask == 0) return moves;
                if (e <= 0) continue;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d], nc = c + dc[d];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                    char cell = classroom[nr].charAt(nc);
                    if (cell == 'X') continue;

                    int ne = (cell == 'R') ? energy : e - 1;
                    int nmask = mask;
                    if (cell == 'L') nmask &= ~(1 << lampBit[nr][nc]);

                    if (!visited[nr][nc][ne][nmask]) {
                        visited[nr][nc][ne][nmask] = true;
                        queue.add(new int[]{nr, nc, ne, nmask});
                    }
                }
            }
            moves++;
        }

        return -1;
    }
}