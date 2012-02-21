package com.alibaba.antx.util;

import java.io.InputStream;

/**
 * ͨ��<code>ByteArray</code>�����ڴ濽��������<code>ByteArrayOutputStream</code>ʵ�֡�
 * 
 * @author Michael Zhou
 */
public class ByteArrayOutputStream extends java.io.ByteArrayOutputStream {

    public ByteArrayOutputStream() {
        super();
    }

    public ByteArrayOutputStream(int size) {
        super(size);
    }

    public ByteArray getByteArray() {
        return new ByteArray(buf, 0, count);
    }

    public InputStream toInputStream() {
        return getByteArray().toInputStream();
    }
}
