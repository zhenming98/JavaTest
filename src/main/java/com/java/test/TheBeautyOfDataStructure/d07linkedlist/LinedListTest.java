package com.java.test.TheBeautyOfDataStructure.d07linkedlist;

/**
 * @author yzm
 * @date 2021/4/22 - 17:03
 */
public class LinedListTest {

    static class Node {
        private int data;
        private Node next;

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return this.data;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.data).append("\t");
            if (this.next != null) {
                stringBuilder.append(this.next);
            }
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Node list = new Node(1, new Node(2, new Node(3, new Node(4,null))));
        System.out.println(list);
        System.out.println(reverse(list));
    }

    public static Node reverse(Node list) {
        Node curr = list;
        Node pre = null;
        while (curr != null) {
            Node next = curr.next;
            System.out.println("next   " + next);
            curr.next = pre;
            System.out.println("curr   " + curr);
            pre = curr;
            System.out.println("pre    " + pre);
            curr = next;
            System.out.println("curr   " + curr);
            System.out.println("----------------");
        }
        return pre;
    }

}
