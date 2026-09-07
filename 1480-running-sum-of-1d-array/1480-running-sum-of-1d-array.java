class Solution {
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int Sum = 0 ;
        int [] runningSum = new int[n];
 
        for(int i =0; i<n; i++){
             Sum = Sum + nums[i];
             runningSum[i] = Sum; 
        }
        return runningSum;
    }
}