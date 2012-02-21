/*
 * @(#)DOMCharacterDataImpl.java   1.11 2000/08/16
 *
 */

package org.w3c.tidy;

import org.w3c.dom.DOMException;

/**
 * DOMCharacterDataImpl (c) 1998-2000 (W3C) MIT, INRIA, Keio University See
 * Tidy.java for the copyright notice. Derived from <a
 * href="http://www.w3.org/People/Raggett/tidy"> HTML Tidy Release 4 Aug
 * 2000</a>
 * 
 * @author Dave Raggett <dsr@w3.org>
 * @author Andy Quick <ac.quick@sympatico.ca> (translation to Java)
 * @version 1.7, 1999/12/06 Tidy Release 30 Nov 1999
 * @version 1.8, 2000/01/22 Tidy Release 13 Jan 2000
 * @version 1.9, 2000/06/03 Tidy Release 30 Apr 2000
 * @version 1.10, 2000/07/22 Tidy Release 8 Jul 2000
 * @version 1.11, 2000/08/16 Tidy Release 4 Aug 2000
 */

public class DOMCharacterDataImpl extends DOMNodeImpl implements org.w3c.dom.CharacterData {

    protected DOMCharacterDataImpl(Node adaptee) {
        super(adaptee);
    }

    /* --------------------- DOM ---------------------------- */

    /**
     * @see org.w3c.dom.CharacterData#getData
     */
    public String getData() throws DOMException {
        return getNodeValue();
    }

    /**
     * @see org.w3c.dom.CharacterData#setData
     */
    public void setData(String data) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * @see org.w3c.dom.CharacterData#getLength
     */
    public int getLength() {
        int len = 0;
        if (adaptee.textarray != null && adaptee.start < adaptee.end)
            len = adaptee.end - adaptee.start;
        return len;
    }

    /**
     * @see org.w3c.dom.CharacterData#substringData
     */
    public String substringData(int offset, int count) throws DOMException {
        int len;
        String value = null;
        if (count < 0) {
            throw new DOMExceptionImpl(DOMException.INDEX_SIZE_ERR, "Invalid length");
        }
        if (adaptee.textarray != null && adaptee.start < adaptee.end) {
            if (adaptee.start + offset >= adaptee.end) {
                throw new DOMExceptionImpl(DOMException.INDEX_SIZE_ERR, "Invalid offset");
            }
            len = count;
            if (adaptee.start + offset + len - 1 >= adaptee.end)
                len = adaptee.end - adaptee.start - offset;

            value = Lexer.getString(adaptee.textarray, adaptee.start + offset, len);
        }
        return value;
    }

    /**
     * @see org.w3c.dom.CharacterData#appendData
     */
    public void appendData(String arg) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * @see org.w3c.dom.CharacterData#insertData
     */
    public void insertData(int offset, String arg) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * @see org.w3c.dom.CharacterData#deleteData
     */
    public void deleteData(int offset, int count) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    /**
     * @see org.w3c.dom.CharacterData#replaceData
     */
    public void replaceData(int offset, int count, String arg) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

}
