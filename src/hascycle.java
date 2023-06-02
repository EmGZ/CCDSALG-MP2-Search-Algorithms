    // Complete the hasCycle function below.

    /*
     * For your reference:
     *
     * SinglyLinkedListNode {
     *     int data;
     *     SinglyLinkedListNode next;
     * }
     *
     */
    static boolean hasCycle(SinglyLinkedListNode head) {
        ArrayList<SinglyLinkedListNode> nList = new ArrayList<>();
        
        if(head == null)
            return false;

        do {
            if(nList.size() > 1) {
                if(nList.contains(head))
                    return true;
                else
                    nList.add(head);
            }
            else 
                nList.add(head);
            
            head = head.next;

        } while(head != null);
        
        return false;


    }