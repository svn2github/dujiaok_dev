/*
 * @(#)DOMNodeListImpl.java   1.11 2000/08/16
 *
 */

package org.w3c.tidy;

/**
 *
 * DOMNodeListImpl
 *
 * (c) 1998-2000 (W3C) MIT, INRIA, Keio University
 * See Tidy.java for the copyright notice.
 * Derived from <a href="http://www.w3.org/People/Raggett/tidy">
 * HTML Tidy Release 4 Aug 2000</a>
 *
 * @author  Dave Raggett <dsr@w3.org>
 * @author  Andy Quick <ac.quick@sympatico.ca> (translation to Java)
 * @version 1.4, 1999/09/04 DOM support
 * @version 1.5, 1999/10/23 Tidy Release 27 Sep 1999
 * @version 1.6, 1999/11/01 Tidy Release 22 Oct 1999
 * @version 1.7, 1999/12/06 Tidy Release 30 Nov 1999
 * @version 1.8, 2000/01/22 Tidy Release 13 Jan 2000
 * @version 1.9, 2000/06/03 Tidy Release 30 Apr 2000
 * @version 1.10, 2000/07/22 Tidy Release 8 Jul 2000
 * @version 1.11, 2000/08/16 Tidy Release 4 Aug 2000
 */

/**
 * <p>
 * The items in the <code>NodeList</code> are accessible via an integral index,
 * starting from 0.
 */
public class DOMNodeListImpl implements org.w3c.dom.NodeList {

    private Node parent = null;

    protected DOMNodeListImpl(Node parent) {
        this.parent = parent;
    }

    /**
     * @see org.w3c.dom.NodeList#item
     */
    public org.w3c.dom.Node item(int index) {
        int i = 0;
        Node node = parent.content;
        while (node != null) {
            if (i >= index)
                break;
            i++;
            node = node.next;
        }
        if (node != null)
            return node.getAdapter();
        else
            return null;
    }

    /**
     * @see org.w3c.dom.NodeList#getLength
     */
    public int getLength() {
        int len = 0;
        Node node = parent.content;
        while (node != null) {
            len++;
            node = node.next;
        }
        return len;
    }

}
