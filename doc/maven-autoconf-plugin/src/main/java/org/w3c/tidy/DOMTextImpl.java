/*
 * @(#)DOMTextImpl.java   1.11 2000/08/16
 *
 */

package org.w3c.tidy;

import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

/**
 * DOMTextImpl (c) 1998-2000 (W3C) MIT, INRIA, Keio University See Tidy.java for
 * the copyright notice. Derived from <a
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

public class DOMTextImpl extends DOMCharacterDataImpl implements org.w3c.dom.Text {

    protected DOMTextImpl(Node adaptee) {
        super(adaptee);
    }

    /* --------------------- DOM ---------------------------- */

    /**
     * @see org.w3c.dom.Node#getNodeName
     */
    public String getNodeName() {
        return "#text";
    }

    /**
     * @see org.w3c.dom.Node#getNodeType
     */
    public short getNodeType() {
        return org.w3c.dom.Node.TEXT_NODE;
    }

    /**
     * @see org.w3c.dom.Text#splitText
     */
    public org.w3c.dom.Text splitText(int offset) throws DOMException {
        // NOT SUPPORTED
        throw new DOMExceptionImpl(DOMException.NO_MODIFICATION_ALLOWED_ERR, "Not supported");
    }

    public String getWholeText() {
        return null;
    }

    public boolean isElementContentWhitespace() {
        return false;
    }

    public Text replaceWholeText(String content) throws DOMException {
        return null;
    }

}
