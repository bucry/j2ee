package com.framework.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.exception.RuntimeIOException;


public final class IOUtils {
	
    private static final Log LOGGER = LogFactory.getLog(IOUtils.class);
    
    private static final int BUFFER_SIZE = 4096;

    public static void write(File file, byte[] bytes) {
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            IOUtils.close(stream);
        }
    }

    public static void write(File file, String content) {
        write(file, content.getBytes(Charset.defaultCharset()));
    }

    public static String text(File file) {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
            return new String(bytes(stream), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            IOUtils.close(stream);
        }
    }

    public static String text(InputStream stream) {
        return new String(bytes(stream), Charset.defaultCharset());
    }

    public static byte[] bytes(File file) {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
            return bytes(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeIOException(e);
        } finally {
            IOUtils.close(stream);
        }
    }

    public static byte[] bytes(InputStream stream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        try {
            while (true) {
                len = stream.read(buf);
                if (len < 0) break;
                byteArrayOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static void close(InputStream stream) {
        try {
            if (stream != null) stream.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    public static void close(OutputStream stream) {
        try {
            if (stream != null) stream.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    public static void close(Reader reader) {
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    public static void close(Writer writer) {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    public static String getTempFolderPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static void copy(File from, File to) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(from));
            output = new BufferedOutputStream(new FileOutputStream(to));

            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while (true) {
                len = input.read(buf);
                if (len < 0) break;
                output.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            close(input);
            close(output);
        }
    }

    private IOUtils() {
    }
}
