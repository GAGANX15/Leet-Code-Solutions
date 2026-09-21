class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k]; // dp[r] = # of subarrays ending at current index with product % k == r

        for (int num : nums) {
            long[] newDp = new long[k];
            int numMod = num % k;
            newDp[numMod] = 1; // subarray consisting of just this element

            for (int r = 0; r < k; r++) {
                if (dp[r] == 0) continue;
                int newMod = (int) ((long) r * numMod % k);
                newDp[newMod] += dp[r];
            }

            for (int r = 0; r < k; r++) {
                ans[r] += newDp[r];
            }
            dp = newDp;
        }

        return ans;
    }
}