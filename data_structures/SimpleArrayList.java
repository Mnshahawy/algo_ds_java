package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SimpleArrayList<E> implements Iterable<E>{
    //Initial capacity for the array list
    private static final int DEFAULT_CAPACITY = 16;

    private int size;
    private E[] items;

    /**
    * Create an empty <code>SimpleArrayList</code> with the default initial
    * capacity.
    */
    SimpleArrayList(){
        this(DEFAULT_CAPACITY);
    }

    /**
    * Create an empty <code>SimpleArrayList</code> with the specified initial
    * capacity.
    * @param capacity the initial capacity
    * @throws NegativeArraySizeException if capacity is negative
    */
    @SuppressWarnings("unchecked")
    SimpleArrayList(int capacity){
        if(capacity < 0)
            throw new NegativeArraySizeException();
        /*
        This is unchecked array creation, we have to accept a second argument "Class<E>"
        if we wish to check for the generic type.
        */
        items = (E[])new Object[capacity];
    }

    /**
    * Increase the capacity of this <code>SimpleArrayList</code>. This will
    * ensure that an expensive growing operation will not occur until
    * <code>minimumCapacity</code> is reached. The buffer is grown to the
    * larger of <code>minimumCapacity</code> and
    * <code>capacity() * 2 + 2</code>, if it is not already large enough.
    *
    * @param minimumCapacity the new capacity
    * @see #capacity()
    * Credit to GNU class path implementation 
    */
    @SuppressWarnings("unchecked")
    public void ensureCapacity(int minimumCapacity){
        if (minimumCapacity > items.length){
            int max = items.length * 2 + 2;
            minimumCapacity = (minimumCapacity < max ? max : minimumCapacity);
            E[] newArr = (E[])new Object[minimumCapacity];
            System.arraycopy(items, 0, newArr, 0, size);
            items = newArr;
        }
    }

    /**
    * Get the capacity of <code>SimpleArrayList</code>.
    * @return capacity of the array list
    */
    public int capacity(){
        return items.length;
    }
    /**
    * Get the elements' count in a <code>SimpleArrayList</code>.
    * @return count of elements in the ArrayList
    */
    public int size(){
        return size;
    }
    /**
    * Add an element to a <code>SimpleArrayList</code>
    * @param element item to be added
    */
    public void add(E element){
        add(size, element);
    }
    /**
    * Add an element to a <code>SimpleArrayList</code> at the specified index
    * if the specified index is beyond the last item in the ArrayList, the item
    * is appended to the end of the ArrayList.
    * if an item exists in the specified index, it will be overwritten.
    * @param index the index where to add the element
    * @param element the item to be added
    * @throws ArrayIndexOutOfBoundsException if index is negative
    */
    public void add(int index, E element){
        //Reject negative index
        if(index < 0)
            throw new ArrayIndexOutOfBoundsException();
        //If the index is greater than size, append the element to the end
        if(index > size)
            index = size;
        //Ensure we have space for one more element
        ensureCapacity(size + 1);
        //Shift elements to the right of the given index
        for(int i=size; i > index; i--)
            items[i] = items[i-1];
        items[index] = element;
        size++;
    }
    /**
    * Get an element from a <code>SimpleArrayList</code> using its index
    * @param index the index where the item is located
    * @return the requested element
    * @throws ArrayIndexOutOfBoundsException if index is negative or greater than
    * the size of the ArrayList
    */
    public E get(int index){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        return items[index];
    }

    /**
    * Replace an element in a <code>SimpleArrayList</code> at the specified index
    * @param index the index where to add the element
    * @param newElement the new value for the item
    * @return the replaced element
    * @throws ArrayIndexOutOfBoundsException if index is negative or greater than
    * the size of the ArrayList
    */
    public E set(int index, E newElement){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        E old =  items[index];
        items[index] = newElement; 
        return old;
    }

    /**
    * Remove an element from a <code>SimpleArrayList</code> at the specified index
    * @param index the index where the item is located
    * @return the removed element
    * @throws ArrayIndexOutOfBoundsException if index is negative or greater than
    * the size of the ArrayList
    */
    public E remove(int index){
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        E deletedItem =  items[index];
        //Shift elements to the left one space starting at the given index
        for(int i=index; i < size-1; i++){
            items[i] = items[i+1];
        }
        //Reduce the size accordingly
        size--;
        return deletedItem;
    }
    /**
    * Remove an element from a <code>SimpleArrayList</code> using its value
    * For this to work accurately, the E class should override the equals method
    * @param element the element to be removed
    * @return the removed element or null if not found
    */
    public E remove(E element){
        for(int i=0; i < size; i++){
            if(items[i].equals(element))
                return remove(i);
        }
        return null;
    }

    /**
    * Get an Iterator for the <code>SimpleArrayList</code>
    * Required for use with forEach
    * @return an Iterator of the <code>SimpleArrayList</code>
    */
    @Override
    public Iterator<E> iterator() {
        //return an iterator. We're using anonymous inner class here.
        return new Iterator<E>(){
            int current = 0;
            @Override
            public boolean hasNext() {
                return current < size();
            }

            @Override
            public E next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                return get(current++);
            }

            @Override
            public void remove() {
                SimpleArrayList.this.remove(--current);
            }
            
        };
    }

    /**
    * Get a String presentation of the <code>SimpleArrayList</code>
    * @return a String of the format [item1, item2, ...] Null objects are
    * represented as "null"
    */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        E element;
        for(int i=0; i < size; i++){
            element = items[i];
            joiner.add(element != null ? element.toString() : "null");
        }
        
        return String.format("[%s]", joiner.toString());
    }

    /**
     * Testing for the <code>SimpleArrayList</code> class
     * @param args
     */
    public static void main(String[] args){
        SimpleArrayList<String> cars = new SimpleArrayList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");
        for(String car : cars) {
            System.out.println(car);
        }
        SimpleArrayList<String> alist=new SimpleArrayList<String>();  
        alist.add("Steve");
        alist.add("Tim");
        alist.add("Lucy");
        alist.add("Pat");
        alist.add("Angela");
        alist.add("Tom");
    
        //displaying elements
        System.out.println(alist);
    
        //Adding "Steve" at the fourth position
        alist.add(3, "Steve");
    
        //displaying elements
        System.out.println(alist);

        SimpleArrayList<Double> doubleList=new SimpleArrayList<Double>();
        doubleList.add(999.3347);
        doubleList.add(3,3989.334);
        doubleList.add(34384.666);
        System.out.println(doubleList);
    }
}