/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

public class Node<E> {
    E element;
    Node<E>next;

    public Node(E element) {
        this.element = element;
    }
}
