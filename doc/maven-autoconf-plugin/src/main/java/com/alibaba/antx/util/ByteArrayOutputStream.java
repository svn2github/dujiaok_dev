package com.alibaba.antx.util;

import java.io.InputStream;

/**
 * 通过<code>ByteArray</code>减少内存拷贝次数的<code>ByteArrayOutputStream</code>实现。
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
