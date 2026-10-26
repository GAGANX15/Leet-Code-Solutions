class Solution {
    public List<Long> mergeAdjacent(int[] nums) {
        Stack<Long> st = new Stack<>();
          for (int i = 0; i < nums.length; i++) {
        long curr = nums[i];

        while (!st.isEmpty() && st.peek() == curr) {
            st.pop();
            curr *= 2;
        }

        st.push(curr);
    }

    return new ArrayList<>(st);
    }
}