public class LinkedListTest{
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(12);
        System.out.println("First element is " + list.get(0) + ".");
        list.add(9);
        System.out.println("Second element is " + list.get(1) + ".");
        list.add(list.get(0)+list.get(1));
        System.out.println("Third element is " + list.get(2) + ".");
       System.out.println("The size of this list is " + list.size() + ".");
       list.remove(1);
       System.out.println("The size of this list after removing the second value is " + list.size() + "."); 
    }
}