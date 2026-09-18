class Solution {
    public int countNegatives(int[][] grid) {
     int count = 0;
        int n = grid[0].length;
        int m = grid.length;

        for (int i = 0; i < m; i++) {
            int low = 0;
            int high = n - 1;
            int f_ve = n;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (grid[i][mid] < 0) {
                    f_ve = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            count += n - f_ve;
        }
        return count;
    }
}