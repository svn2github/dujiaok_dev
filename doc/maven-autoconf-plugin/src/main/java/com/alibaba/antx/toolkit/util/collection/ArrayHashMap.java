package com.alibaba.antx.toolkit.util.collection;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p>
 * һ��Hash���ʵ��, ʵ����<code>ListMap</code>��<code>Map</code>�ӿ�.
 * </p>
 * 
 * <p>
 * ���hash���ʵ�־�����������:
 * </p>
 * 
 * <ul>
 * <li>
 * ���ڲ�������ķ�ʽ��������entry, ����˳�����
 * </li>
 * <li>
 * ��<code>DefaultHashMap</code>һ��, û�н����κ�<code>synchronized</code>����
 * </li>
 * </ul>
 * 
 *
 * @author Michael Zhou
 * @version $Id: ArrayHashMap.java 1291 2005-03-04 03:23:30Z baobao $
 *
 * @see DefaultHashMap
 * @see ListMap
 */
public class ArrayHashMap extends DefaultHashMap
        implements ListMap {
    private static final long serialVersionUID = 3258411729271927857L;

    /* ============================================================================ */
    /* ��Ա����                                                                     */
    /* ============================================================================ */

    /** ��¼entry��˳�������. */
    protected transient Entry[] order;

    /** Key���б���ͼ. */
    private transient List keyList;

    /** Value���б���ͼ. */
    private transient List valueList;

    /** Entry���б���ͼ. */
    private transient List entryList;

    /* ============================================================================ */
    /* ���캯��                                                                     */
    /* ============================================================================ */

    /**
     * ����һ���յ�hash��. ʹ��ָ����Ĭ�ϵĳ�ʼ����(16)��Ĭ�ϵĸ���ϵ��(0.75).
     */
    public ArrayHashMap() {
        super();
    }

    /**
     * ����һ���յ�hash��. ʹ��ָ���ĳ�ʼ��ֵ��Ĭ�ϵĸ���ϵ��(0.75).
     *
     * @param initialCapacity ��ʼ����.
     */
    public ArrayHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * ����һ���յ�hash��. ʹ��ָ���ĳ�ʼ�����͸���ϵ��.
     *
     * @param initialCapacity ��ʼ����
     * @param loadFactor ����ϵ��.
     */
    public ArrayHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * ����ָ��<code>Map</code>������ͬ��<code>HashMap</code>. ʹ��Ĭ�ϵĸ���ϵ��(0.75).
     *
     * @param map Ҫ���Ƶ�<code>Map</code>
     */
    public ArrayHashMap(Map map) {
        super(map);
    }

    /* ============================================================================ */
    /* ʵ��Map��ListMap�ӿڵķ���                                                   */
    /* ============================================================================ */

    /**
     * ���hash���а���һ������key��Ӧָ����value, �򷵻�true.
     *
     * @param value ָ��value, ������Ĵ������.
     *
     * @return ���hash���а���һ������key��Ӧָ����value, �򷵻�<code>true</code>.
     */
    public boolean containsValue(Object value) {
        // ���Ǵ˷����ǳ������ܵĿ���.  ����������Ҹ���Ч.
        for (int i = 0; i < size; i++) {
            Entry entry = order[i];

            if (eq(value, entry.getValue())) {
                return true;
            }
        }

        return false;
    }

    /**
     * ���hash���е�����entry.
     */
    public void clear() {
        super.clear();
        Arrays.fill(order, null);
    }

    /**
     * ����ָ��index����value. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫ���ص�value������ֵ
     *
     * @return ָ��index����value����
     */
    public Object get(int index) {
        checkRange(index);
        return order[index].getValue();
    }

    /**
     * ����ָ��index����key. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫ���ص�key������ֵ
     *
     * @return ָ��index����key����
     */
    public Object getKey(int index) {
        checkRange(index);
        return order[index].getKey();
    }

    /**
     * ɾ��ָ��index������. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
     *
     * @param index Ҫɾ�����������ֵ
     *
     * @return ��ɾ����<code>Map.Entry</code>��
     */
    public Map.Entry remove(int index) {
        checkRange(index);
        return removeEntryForKey(order[index].getKey());
    }

    /**
     * ��������key��<code>List</code>.
     *
     * @return ����key��<code>List</code>.
     */
    public List keyList() {
        return ((keyList != null) ? keyList
                                  : (keyList = new KeyList()));
    }

    /**
     * ��������value��<code>List</code>.
     *
     * @return ����value��<code>List</code>.
     */
    public List valueList() {
        return ((valueList != null) ? valueList
                                    : (valueList = new ValueList()));
    }

    /**
     * ��������entry��<code>List</code>.
     *
     * @return ����entry��<code>List</code>.
     */
    public List entryList() {
        return ((entryList != null) ? entryList
                                    : (entryList = new EntryList()));
    }

    /* ============================================================================ */
    /* �ڲ���                                                                       */
    /* ============================================================================ */

    /**
     * <code>Map.Entry</code>��ʵ��.
     */
    protected class Entry extends DefaultHashMap.Entry {
        /** Entry���б��е�����ֵ. */
        protected int index;

        /**
         * ����һ���µ�entry.
         *
         * @param h key��hashֵ
         * @param k entry��key
         * @param v entry��value
         * @param n �����е���һ��entry
         */
        protected Entry(int h, Object k, Object v, DefaultHashMap.Entry n) {
            super(h, k, v, n);
        }

        /**
         * ��entry����ɾ��ʱ, ���º�����entry������ֵ.
         */
        protected void onRemove() {
            int numMoved = size - index;

            if (numMoved > 0) {
                System.arraycopy(order, index + 1, order, index, numMoved);
            }

            order[size] = null;

            for (int i = index; i < size; i++) {
                order[i].index--;
            }
        }
    }

    /**
     * ������.
     */
    private abstract class ArrayHashIterator
            implements ListIterator {
        /** ������ص�entry. */
        private Entry lastReturned;

        /** ��ǰλ��. */
        private int cursor;

        /** ����iteratorʱ���޸ļ���. */
        private int expectedModCount;

        /**
         * ����һ��list iterator.
         *
         * @param index ��ʼ��
         */
        protected ArrayHashIterator(int index) {
            if ((index < 0) || (index > size())) {
                throw new IndexOutOfBoundsException("Index: " + index);
            }

            cursor           = index;
            expectedModCount = modCount;
        }

        /**
         * ��ָ��������뵽�б���. (��֧�ִ˲���)
         *
         * @param o Ҫ����Ķ���
         */
        public void add(Object o) {
            throw new UnsupportedOperationException();
        }

        /**
         * ��ָ�������滻���б���. (����<code>valueList</code>����, ��֧�ִ˲���)
         *
         * @param o Ҫ�滻�Ķ���
         */
        public void set(Object o) {
            throw new UnsupportedOperationException();
        }

        /**
         * ���ر��������Ƿ�����һ��entry.
         *
         * @return ����������л�����һ��entry, ����<code>true</code>
         */
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * ���ر��������Ƿ���ǰһ��entry.
         *
         * @return ����������л���ǰһ��entry, ����<code>true</code>
         */
        public boolean hasPrevious() {
            return cursor > 0;
        }

        /**
         * ȡ����һ��index. ��������һ��, �򷵻�<code>size</code>.
         *
         * @return ��һ���index
         */
        public int nextIndex() {
            return cursor;
        }

        /**
         * ȡ��ǰһ��index. ����ǵ�һ��, �򷵻�<code>-1</code>.
         *
         * @return ǰһ���index
         */
        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * ɾ��һ����ǰentry. ִ��ǰ������ִ��<code>next()</code>��<code>previous()</code>����.
         */
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }

            checkForComodification();

            removeEntryForKey(lastReturned.getKey());

            if (lastReturned.index < cursor) {
                cursor--;
            }

            lastReturned     = null;
            expectedModCount = modCount;
        }

        /**
         * ȡ����һ��entry.
         *
         * @return ��һ��entry
         */
        protected Entry nextEntry() {
            checkForComodification();

            if (cursor >= size) {
                throw new NoSuchElementException();
            }

            lastReturned = order[cursor++];

            return lastReturned;
        }

        /**
         * ȡ��ǰһ��entry.
         *
         * @return ǰһ��entry
         */
        protected Entry previousEntry() {
            checkForComodification();

            if (cursor <= 0) {
                throw new NoSuchElementException();
            }

            lastReturned = order[--cursor];

            return lastReturned;
        }

        /**
         * ���õ�ǰentry��ֵ.
         *
         * @param o Ҫ���õ�ֵ
         */
        protected void setValue(Object o) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }

            checkForComodification();

            lastReturned.setValue(o);
        }

        /**
         * ����Ƿ�ͬʱ���޸�.
         */
        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * ȡ��hash���key�ı�����.
     */
    private class KeyIterator extends ArrayHashIterator {
        /**
         * ����һ��list iterator.
         *
         * @param index ��ʼ��
         */
        protected KeyIterator(int index) {
            super(index);
        }

        /**
         * ȡ����һ��key.
         *
         * @return ��һ��key
         */
        public Object next() {
            return nextEntry().getKey();
        }

        /**
         * ȡ��ǰһ��key.
         *
         * @return ǰһ��key
         */
        public Object previous() {
            return previousEntry().getKey();
        }
    }

    /**
     * ȡ��hash���value�ı�����.
     */
    private class ValueIterator extends ArrayHashIterator {
        /**
         * ����һ��list iterator.
         *
         * @param index ��ʼ��
         */
        protected ValueIterator(int index) {
            super(index);
        }

        /**
         * ��ָ�������滻���б���.
         *
         * @param o Ҫ�滻�Ķ���(value)
         */
        public void set(Object o) {
            setValue(o);
        }

        /**
         * ȡ����һ��value.
         *
         * @return ��һ��value
         */
        public Object next() {
            return nextEntry().getValue();
        }

        /**
         * ȡ��ǰһ��value.
         *
         * @return ǰһ��value
         */
        public Object previous() {
            return previousEntry().getValue();
        }
    }

    /**
     * ȡ��hash���entry�ı�����.
     */
    private class EntryIterator extends ArrayHashIterator {
        /**
         * ����һ��list iterator.
         *
         * @param index ��ʼ��
         */
        protected EntryIterator(int index) {
            super(index);
        }

        /**
         * ȡ����һ��entry.
         *
         * @return ��һ��entry
         */
        public Object next() {
            return nextEntry();
        }

        /**
         * ȡ��ǰһ��entry.
         *
         * @return ǰһ��entry
         */
        public Object previous() {
            return previousEntry();
        }
    }

    /**
     * �б���ͼ.
     */
    private abstract class ArrayHashList extends AbstractList {
        /**
         * ����hash����entry�ĸ���.
         *
         * @return hash���е�entry��.
         */
        public int size() {
            return size;
        }

        /**
         * �ж��Ƿ�Ϊ�յ�hash��.
         *
         * @return ���Ϊ��(<code>size() == 0</code>), �򷵻�<code>true</code>.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * �������entry.
         */
        public void clear() {
            ArrayHashMap.this.clear();
        }

        /**
         * ɾ��ָ��index������. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
         *
         * @param index Ҫɾ�����������ֵ
         *
         * @return ��ɾ����<code>Map.Entry</code>��
         */
        public Object remove(int index) {
            checkRange(index);
            return removeEntryForKey(order[index].getKey());
        }

        /**
         * ȡ��ָ��entry������. ͬ<code>indexOf</code>����.
         *
         * @param o Ҫ���ҵ�entry
         *
         * @return ָ��entry������
         */
        public int lastIndexOf(Object o) {
            return indexOf(o);
        }
    }

    /**
     * entry���б���ͼ.
     */
    private class EntryList extends ArrayHashList {
        /**
         * �ж�entry�б����Ƿ����ָ������.
         *
         * @param o Ҫ���ҵĶ���
         *
         * @return ���entry�б����Ƿ����ָ������, �򷵻�<code>true</code>
         */
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry entry     = (Map.Entry) o;
            Entry     candidate = (ArrayHashMap.Entry) getEntry(entry.getKey());

            return eq(candidate, entry);
        }

        /**
         * ȡ��entry�ı�����.
         *
         * @return entry�ı�����
         */
        public Iterator iterator() {
            return newEntryIterator();
        }

        /**
         * ɾ��ָ����entry.
         *
         * @param o Ҫɾ����entry
         *
         * @return ���ɾ���ɹ�, ����<code>true</code>
         */
        public boolean remove(Object o) {
            return removeEntry(o) != null;
        }

        /**
         * ����ָ��index����entry. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
         *
         * @param index Ҫ���ص�entry������ֵ
         *
         * @return ָ��index����entry����
         */
        public Object get(int index) {
            checkRange(index);
            return order[index];
        }

        /**
         * ȡ��ָ��entry������.
         *
         * @param o Ҫ���ҵ�entry
         *
         * @return ָ��entry������
         */
        public int indexOf(Object o) {
            if ((o != null) && o instanceof Map.Entry) {
                Entry entry = (Entry) getEntry(((Map.Entry) o).getKey());

                if ((entry != null) && entry.equals(o)) {
                    return entry.index;
                }
            }

            return -1;
        }

        /**
         * ȡ��list iterator, �����õ�ǰλ��.
         *
         * @param index ��ǰλ��
         *
         * @return list iterator
         */
        public ListIterator listIterator(int index) {
            return new EntryIterator(index);
        }
    }

    /**
     * key���б���ͼ.
     */
    private class KeyList extends ArrayHashList {
        /**
         * �ж�key�б����Ƿ����ָ������.
         *
         * @param o Ҫ���ҵĶ���
         *
         * @return ���key�б����Ƿ����ָ������, �򷵻�<code>true</code>
         */
        public boolean contains(Object o) {
            return ArrayHashMap.this.containsKey(o);
        }

        /**
         * ȡ��key�ı�����.
         *
         * @return key�ı�����
         */
        public Iterator iterator() {
            return newKeyIterator();
        }

        /**
         * ɾ��ָ����key.
         *
         * @param o Ҫɾ����key
         *
         * @return ���ɾ���ɹ�, ����<code>true</code>
         */
        public boolean remove(Object o) {
            Entry entry = (Entry) getEntry(o);

            if (entry == null) {
                return false;
            } else {
                removeEntry(entry);
                return true;
            }
        }

        /**
         * ����ָ��index����key. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
         *
         * @param index Ҫ���ص�key������ֵ
         *
         * @return ָ��index����key����
         */
        public Object get(int index) {
            checkRange(index);
            return order[index].getKey();
        }

        /**
         * ȡ��ָ��key������.
         *
         * @param o Ҫ���ҵ�key
         *
         * @return ָ��key������
         */
        public int indexOf(Object o) {
            Entry entry = (Entry) getEntry(o);

            if (entry != null) {
                return entry.index;
            }

            return -1;
        }

        /**
         * ȡ��list iterator, �����õ�ǰλ��.
         *
         * @param index ��ǰλ��
         *
         * @return list iterator
         */
        public ListIterator listIterator(int index) {
            return new KeyIterator(index);
        }
    }

    /**
     * value���б���ͼ.
     */
    private class ValueList extends ArrayHashList {
        /**
         * �ж�value�б����Ƿ����ָ������.
         *
         * @param o Ҫ���ҵĶ���
         *
         * @return ���value�б����Ƿ����ָ������, �򷵻�<code>true</code>
         */
        public boolean contains(Object o) {
            return ArrayHashMap.this.containsValue(o);
        }

        /**
         * ȡ��value�ı�����.
         *
         * @return value�ı�����
         */
        public Iterator iterator() {
            return newValueIterator();
        }

        /**
         * ɾ��ָ����value.
         *
         * @param o Ҫɾ����value
         *
         * @return ���ɾ���ɹ�, ����<code>true</code>
         */
        public boolean remove(Object o) {
            int index = indexOf(o);

            if (index != -1) {
                ArrayHashMap.this.remove(index);
                return true;
            }

            return false;
        }

        /**
         * ����ָ��index����value. ���index������Χ, ������<code>IndexOutOfBoundsException</code>.
         *
         * @param index Ҫ���ص�value������ֵ
         *
         * @return ָ��index����value����
         */
        public Object get(int index) {
            checkRange(index);
            return order[index].getValue();
        }

        /**
         * ȡ��ָ��value������.
         *
         * @param o Ҫ���ҵ�value
         *
         * @return ָ��value������
         */
        public int indexOf(Object o) {
            for (int i = 0; i < size; i++) {
                if (eq(o, order[i].getValue())) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * ȡ��list iterator, �����õ�ǰλ��.
         *
         * @param index ��ǰλ��
         *
         * @return list iterator
         */
        public ListIterator listIterator(int index) {
            return new ValueIterator(index);
        }
    }

    /* ============================================================================ */
    /* �ڲ�����                                                                     */
    /* ============================================================================ */

    /**
     * ��ʼ��ʱhash��.
     */
    protected void onInit() {
        order = new Entry[threshold];
    }

    /**
     * �˷��������˸���ķ���.  ���������һ��entry, ͬʱ��entry��¼��order�б���.
     *
     * @param key hash���key
     * @param value hash���value
     */
    protected void addEntry(Object key, Object value) {
        int   hash  = hash(key);
        int   i     = indexFor(hash, table.length);
        Entry entry = new Entry(hash, key, value, table[i]);

        table[i]      = entry;
        entry.index   = size;
        order[size++] = entry;
    }

    /**
     * ���Ǹ���ķ���, ��������key�ı�����.
     *
     * @return hash���key�ı�����
     */
    protected Iterator newKeyIterator() {
        return new KeyIterator(0);
    }

    /**
     * ���Ǹ���ķ���, ��������value�ı�����.
     *
     * @return hash���key�ı�����
     */
    protected Iterator newValueIterator() {
        return new ValueIterator(0);
    }

    /**
     * ���Ǹ���ķ���, ��������entry�ı�����.
     *
     * @return hash���key�ı�����
     */
    protected Iterator newEntryIterator() {
        return new EntryIterator(0);
    }

    /**
     * ��map��������.  �˷�����entry��������ֵʱ������.
     *
     * @param newCapacity �µ�����
     */
    protected void resize(int newCapacity) {
        super.resize(newCapacity);

        if (threshold > order.length) {
            Entry[] newOrder = new Entry[threshold];

            System.arraycopy(order, 0, newOrder, 0, order.length);

            order = newOrder;
        }
    }

    /**
     * ������<code>resize</code>ʱ����ô˷��������е���Ƶ��µ�������. ���Ǵ˷����ǳ������ܵĿ���, ��Ϊ�����������hash���ԭ����ʵ�ַ�������Ч.
     *
     * @param newTable �±�
     */
    protected void transfer(DefaultHashMap.Entry[] newTable) {
        int newCapacity = newTable.length;

        for (int i = 0; i < size; i++) {
            Entry entry = order[i];
            int   index = indexFor(entry.hash, newCapacity);

            entry.next      = newTable[index];
            newTable[index] = entry;
        }
    }

    /**
     * ���ָ��������ֵ�Ƿ�Խ��.  �����, ����������ʱ�쳣.
     *
     * @param index Ҫ�����쳣
     */
    private void checkRange(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
