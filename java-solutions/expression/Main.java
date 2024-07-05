package expression;

import expression.exceptions.ExpressionParser;

import java.util.*;

public class Main {

    public static int strStr(String haystack, String needle) {
        int ind = 0;
        char[] inp1 = haystack.toCharArray();
        char[] inp2 = needle.toCharArray();
        while(ind < inp1.length - inp2.length + 1) {
            while(ind < inp1.length - inp2.length && inp1[ind] != inp2[0]) {
                ind++;
            }
            System.out.println(ind);
            for (int i = 0; i < inp2.length; i++) {
                if (inp2[i] != inp1[ind + i]) {
                    break;
                }
                if (i == inp2.length - 1) {
                    return ind;
                }
            }
            ind++;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(strStr("a", "a"));
    }


    /*private static int result = -1001;

    private static int[] dfs(TreeNode node){
        int[] buffer = new int[2];
        buffer[0] = node.val;
        buffer[1] = node.val;
        if (node.left != null){
            int[] a = dfs(node.left);
            buffer[0] += Math.max(a[0], a[1]);
        }
        if (node.right != null){
            int[] a = dfs(node.right);
            buffer[1] += Math.max(a[0], a[1]);
        }
        result = Math.max(Math.max(result, buffer[0] + buffer[1] - node.val), Math.max(buffer[0], buffer[1]));
        result = Math.max(result, node.val);
        return buffer;
    }

    public static int maxPathSum(TreeNode root) {
        int[] a = dfs(root);
        return result;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(9, new TreeNode(6, null, null),
                new TreeNode(-3, new TreeNode(-6, null, null),
                        new TreeNode(2, new TreeNode(2, null, new TreeNode(-6, null, null)), null)));
        System.out.println(maxPathSum(root));
    }*/
}
