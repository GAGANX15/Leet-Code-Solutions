class Solution {
    public long countCommas(long n) {
        long ans = 0;
        for (long threshold = 1000L; threshold <= n; threshold *= 1000) {
            ans += n - threshold + 1;
        }
        return ans;
    }
}