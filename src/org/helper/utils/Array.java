package org.helper.utils;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Массив со свойствами стека
 */
public class Array<E> extends Stack<E>  {
    /**
     * Creates an empty Stack.
     */
    public Array() {
    }
    public synchronized E shift(){
        E obj;

        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        obj = elementAt(0);

        removeElementAt(0);

        return obj;
    }
}
