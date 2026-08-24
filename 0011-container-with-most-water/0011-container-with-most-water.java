class Solution {
    public int maxArea(int[] height) {
        int n = height.length;
        int l = 0,r=n-1;
        int maxArea = Integer.MIN_VALUE;
        while(l<r){
            int h = Math.min(height[l],height[r]);
            int w = r-l;
            int a = h*w;
            maxArea = Math.max(maxArea, a);
            if(height[l] < height[r]){
                l++;
            }else{
                r--;
            }
        }
        return maxArea;
    }
}