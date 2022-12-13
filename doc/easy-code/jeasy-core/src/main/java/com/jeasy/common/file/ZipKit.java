package com.jeasy.common.file;

import com.jeasy.common.Func;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.support.FastByteArrayOutputStream;
import com.jeasy.exception.KitException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 压缩工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class ZipKit {
    /**
     * 打包到当前目录
     *
     * @param srcPath 源文件路径
     * @return 打包好的压缩文件
     * @throws IOException
     */
    public static File zip(String srcPath) throws IOException {
        return zip(FileKit.file(srcPath));
    }

    /**
     * 打包到当前目录
     *
     * @param srcFile 源文件或目录
     * @return 打包好的压缩文件
     * @throws IOException
     */
    public static File zip(File srcFile) throws IOException {
        File zipFile = FileKit.file(srcFile.getParentFile(), FileKit.mainName(srcFile) + ".zip");
        zip(zipFile, false, srcFile);
        return zipFile;
    }

    /**
     * 对文件或文件目录进行压缩<br>
     * 不包含被打包目录
     *
     * @param srcPath 要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath 压缩文件保存的路径，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @return 压缩好的Zip文件
     * @throws IOException
     */
    public static File zip(String srcPath, String zipPath) throws IOException {
        return zip(srcPath, zipPath, false);
    }

    /**
     * 对文件或文件目录进行压缩<br>
     *
     * @param srcPath    要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath    压缩文件保存的路径，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @param withSrcDir 是否包含被打包目录
     * @throws IOException
     * @throws Exception
     */
    public static File zip(String srcPath, String zipPath, boolean withSrcDir) throws IOException {
        File srcFile = FileKit.file(srcPath);
        File zipFile = FileKit.file(zipPath);
        zip(zipFile, withSrcDir, srcFile);
        return zipFile;
    }

    /**
     * 对文件或文件目录进行压缩<br>
     *
     * @param zipFile    生成的Zip文件，包括文件名。注意：zipPath不能是srcPath路径下的子文件夹
     * @param withSrcDir 是否包含被打包目录
     * @param srcFiles   要压缩的源文件或目录。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @throws IOException
     */
    public static void zip(File zipFile, boolean withSrcDir, File... srcFiles) throws IOException {
        validateFiles(zipFile, srcFiles);

        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new CheckedOutputStream(FileKit.getOutputStream(zipFile), new CRC32()));
            for (File srcFile : srcFiles) {
                // 如果只是压缩一个文件，则需要截取该文件的父目录
                String srcRootDir = srcFile.getCanonicalPath();
                if (srcFile.isFile() || withSrcDir) {
                    srcRootDir = srcFile.getParent();
                }
                // 调用递归压缩方法进行目录或文件压缩
                zip(out, srcRootDir, srcFile);
                out.flush();
            }
        } catch (IOException e) {
            throw new KitException(e);
        } finally {
            IoKit.close(out);
        }
    }

    /**
     * 解压到文件名相同的目录中
     *
     * @param zipFile 压缩文件
     * @return 解压的目录
     * @throws IOException
     */
    public static File unzip(File zipFile) throws IOException {
        return unzip(zipFile, FileKit.file(zipFile.getParentFile(), FileKit.mainName(zipFile)));
    }

    /**
     * 解压到文件名相同的目录中
     *
     * @param zipFilePath 压缩文件路径
     * @return 解压的目录
     * @throws IOException
     */
    public static File unzip(String zipFilePath) throws IOException {
        return unzip(FileKit.file(zipFilePath));
    }

    /**
     * 解压
     *
     * @param zipFilePath 压缩文件的路径
     * @param outFileDir  解压到的目录
     * @return 解压的目录
     * @throws IOException
     */
    public static File unzip(String zipFilePath, String outFileDir) throws IOException {
        return unzip(FileKit.file(zipFilePath), FileKit.mkdir(outFileDir));
    }

    /**
     * 解压
     *
     * @param zipFile zip文件
     * @param outFile 解压到的目录
     * @return 解压的目录
     * @throws IOException
     */
    public static File unzip(File zipFile, File outFile) throws IOException {
        final ZipFile zipFileObj = new ZipFile(zipFile);
        final Enumeration<ZipEntry> em = (Enumeration<ZipEntry>) zipFileObj.entries();
        ZipEntry zipEntry;
        File outItemFile;
        while (em.hasMoreElements()) {
            zipEntry = em.nextElement();
            outItemFile = new File(outFile, zipEntry.getName());
            if (zipEntry.isDirectory() && outItemFile.mkdirs()) {
                log.debug(outItemFile + " mkdirs success ");
            } else {
                FileKit.touch(outItemFile);
                copy(zipFileObj, zipEntry, outItemFile);
            }
        }
        IoKit.close(zipFileObj);
        return outFile;
    }

    //----------------------------------------------------------------------------- Gzip

    /**
     * Gzip压缩处理
     *
     * @param content 被压缩的字符串
     * @param charset 编码
     * @return 压缩后的字节流
     * @throws IOException
     */
    public static byte[] gzip(String content, String charset) throws IOException {
        return gzip(StrKit.bytes(content, charset));
    }

    /**
     * Gzip压缩处理
     *
     * @param val 被压缩的字节流
     * @return 压缩后的字节流
     * @throws IOException
     */
    public static byte[] gzip(byte[] val) throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream(val.length);
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(bos);
            gos.write(val, 0, val.length);
            gos.finish();
            gos.flush();
            val = bos.toByteArray();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(gos, bos);
        }
        return val;
    }

    /**
     * Gzip压缩文件
     *
     * @param file 被压缩的文件
     * @return 压缩后的字节流
     * @throws IOException
     */
    public static byte[] gzip(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        GZIPOutputStream gos = null;
        BufferedInputStream in;
        try {
            gos = new GZIPOutputStream(bos);
            in = FileKit.getInputStream(file);
            IoKit.copy(in, gos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(gos, bos);
        }
    }

    /**
     * Gzip解压缩处理
     *
     * @param buf     压缩过的字节流
     * @param charset 编码
     * @return 解压后的字符串
     * @throws IOException
     */
    public static String unGzip(byte[] buf, String charset) throws IOException {
        return StrKit.str(unGzip(buf), charset);
    }

    /**
     * Gzip解压处理
     *
     * @param buf buf
     * @return bytes
     * @throws IOException
     */
    public static byte[] unGzip(byte[] buf) throws IOException {
        GZIPInputStream gzi = null;
        ByteArrayOutputStream bos = null;
        try {
            gzi = new GZIPInputStream(new ByteArrayInputStream(buf));
            bos = new ByteArrayOutputStream(buf.length);
            IoKit.copy(gzi, bos);
            buf = bos.toByteArray();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(bos, gzi);
        }
        return buf;
    }

    // ---------------------------------------------------------------------------------------------- Private method start

    /**
     * 递归压缩文件夹
     *
     * @param out        压缩文件存储对象
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @throws Exception
     */
    private static void zip(ZipOutputStream out, String srcRootDir, File file) {
        if (file == null) {
            return;
        }

        // 如果是文件，则直接压缩该文件
        if (file.isFile()) {
            // 获取文件相对于压缩文件夹根目录的子路径
            final String subPath = FileKit.subPath(srcRootDir, file);
            BufferedInputStream in = null;
            try {
                out.putNextEntry(new ZipEntry(subPath));
                in = FileKit.getInputStream(file);
                IoKit.copy(in, out);
            } catch (IOException e) {
                throw new KitException(e);
            } finally {
                IoKit.close(in);
                closeEntry(out);
            }
        } else {// 如果是目录，则压缩压缩目录中的文件或子目录
            File[] files = file.listFiles();
            if (Func.isNotEmpty(files)) {
                for (File childFile : files) {
                    zip(out, srcRootDir, childFile);
                }
            }
        }
    }

    /**
     * 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
     *
     * @param srcFiles 被压缩的文件或目录
     * @param zipFile  压缩后的产生的文件路径
     */
    private static void validateFiles(File zipFile, File... srcFiles) {
        for (File srcFile : srcFiles) {
            if (false == srcFile.exists()) {
                throw new KitException(StrKit.format("File [{}] not exist!", srcFile.getAbsolutePath()));
            }

            try {
                // 压缩文件不能位于被压缩的目录内
                if (srcFile.isDirectory() && zipFile.getParent().contains(srcFile.getCanonicalPath())) {
                    throw new KitException("[zipPath] must not be the child directory of [srcPath]!");
                }

                if (false == zipFile.exists()) {
                    FileKit.touch(zipFile);
                }
            } catch (IOException e) {
                throw new KitException(e);
            }
        }
    }

    /**
     * 关闭当前Entry，继续下一个Entry
     *
     * @param out ZipOutputStream
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 从Zip文件流中拷贝文件出来
     *
     * @param zipFile     Zip文件
     * @param zipEntry    zip文件中的子文件
     * @param outItemFile 输出到的文件
     * @throws IOException
     */
    private static void copy(ZipFile zipFile, ZipEntry zipEntry, File outItemFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = zipFile.getInputStream(zipEntry);
            out = FileKit.getOutputStream(outItemFile);
            IoKit.copy(in, out);
        } finally {
            IoKit.close(out, in);
        }
    }

}
