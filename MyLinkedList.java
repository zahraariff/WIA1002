/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

public class MyLinkedList<E> {
    private int size;
    Node<E> head;
    Node<E> tail;
    
    //Constructor for the node
    public MyLinkedList() {
        size=0;
        head=null;
        tail=null;
    }
    
    //getSize
    public int getSize(){
        return size;
    }
    
     //isEmpty
    public boolean isEmpty(){
        return size==0;
    }
    
    //addFirst
    public void addFirst(E e){
         Node<E> newNode = new Node<>(e);
         newNode.next=head;
         head=newNode;
         size++;
         if(tail==null)
             tail=head;
     }
   
    //addLast
    public void addLast(E e){
         Node<E>newNode=new Node<>(e);
         if(tail==null){
             head=tail= newNode;
         }else{
            tail.next= newNode;
            tail=newNode;
         }
         size++;
    }
    
    //add using addLast()
    public void add(E e){
        addLast(e);
    }

        public E removeFirst(){
        if(size==0){
             return null;
        }
        Node<E> temp = head; 
        head=head.next;
        if (head == null){
            tail = null; 
        }
        size--;
        return temp.element;
    }

    public E removeLast(){
        Node<E> current = head; 
        for (int i = 0; i < size - 2; i++){
            current = (Node<E>) current.next; 
        }
        Node<E>temp=tail;
        tail=current;
        size--;
        return temp.element;
    }

    public E remove(int index){
        if(index<0||index>=size)
            return null;
        else if(index==0)
            removeFirst();
        else if(index==size-1)
            removeLast();
        else{
             Node<E> previous = head;
        for (int i = 0; i <index-1; i++){
            previous = previous.next; 
        }
        Node<E>current=previous.next;
        previous.next=current.next;
        size--;
        return current.element;
        }
        return null;
    }

    public void remove(E e){
        remove(indexOf(e));
    }


    //Return element at the specified index
    public E get(int index){
        Node<E> current = head; 
        for (int i = 0; i <index; i++){
            current = current.next; 
        }
        return current.element;////
    }
    
    //Return the index of the head matching element in this 
    //list. Return -1 of no match
    public int indexOf(E e){
        int index=-1;
        Node<E> current = head; 
        for (int i = 0; i<size; i++){
            if(current.element.equals(e)){ 
                index=i;
                break;
            }
            current = (Node<E>) current.next;
        }
        return index;
    }
    
    //check if an element is in the list
    public boolean contains(E e){
        Node<E> current = head; 
        boolean found=false;
        for (int i = 0; i <size; i++){
                if(current.element.equals(e)){
                    found=true;
            }
        current = current.next; 
        }
        return found;
    }
    
    //Return the value of the first item
    public E getFirst(){
        return head.element;
    }
    
    //Return the value of the first item
    public E getLast(){
        return tail.element;
    }
    
    //Print all the elements in the list
    public void print(){
        Node<E> current = head; 
        String output="";
        while(current != null) {
            output = output + current.element;
            current = current.next;
        }
        System.out.println(output);
    }
    
    public MyLinkedList<E> clone(MyLinkedList<E>oldList){
        MyLinkedList<E>newList=new MyLinkedList<>();
        for(int i=0;i<oldList.size;i++){
            newList.add(oldList.get(i));
        }
        return newList;
    }

    public void clear(){
        Node<E> remove;
        Node<E> temp = head;
        while(temp!=null){
            remove=temp;
            remove=null;
            temp=temp.next;
        }
        remove=null;
        head=tail=null;
        size=0;
    } 
}
