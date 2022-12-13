package com.jeasy.common.file;

import com.google.common.collect.Lists;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.clazz.ClassKit;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.log.LogKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 文件工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class FileKit {

    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';
    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * Class文件扩展名
     */
    public static final String CLASS_EXT = ".class";
    /**
     * Jar文件扩展名
     */
    public static final String JAR_FILE_EXT = ".jar";
    /**
     * 在Jar中的路径jar的扩展名形式
     */
    public static final String JAR_PATH_EXT = ".jar!";
    /**
     * 当Path为文件形式时, path会加入一个表示文件的前缀
     */
    public static final String PATH_FILE_PRE = "file:";
    public static final String PATH_REGEX = "^[a-zA-Z]:/.*";
    public static final int NUM_1024 = 1024;

    /**
     * 列出目录文件<br>
     * 给定的绝对路径不能是压缩包中的路径
     *
     * @param path 目录绝对路径或者相对路径
     * @return 文件列表（包含目录）
     */
    public static File[] ls(String path) {
        if (path == null) {
            return null;
        }
        path = getAbsolutePath(path);

        File file = file(path);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        throw new KitException(StrKit.format("Path [{}] is not directory!", path));
    }

    /**
     * 文件是否为空<br>
     * 目录：里面没有文件时为空
     * 文件：文件大小为0时为空
     *
     * @param file 文件
     * @return 是否为空，当提供非目录时，返回false
     */
    public static boolean isEmpty(File file) {
        if (null == file) {
            return true;
        }

        if (file.isDirectory()) {
            String[] subFiles = file.list();
            if (CollectionKit.isEmpty(subFiles)) {
                return true;
            }
        } else if (file.isFile()) {
            return file.length() <= 0;
        }

        return false;
    }

    /**
     * 目录是否为空
     *
     * @param file 目录
     * @return 是否为空，当提供非目录时，返回false
     */
    public static boolean isNotEmpty(File file) {
        return false == isEmpty(file);
    }

    /**
     * 目录是否为空
     *
     * @param dirPath 目录
     * @return 是否为空
     * @throws KitException IOException
     */
    public static boolean isDirEmpty(Path dirPath) {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirPath)) {
            return false == dirStream.iterator().hasNext();
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 目录是否为空
     *
     * @param dir 目录
     * @return 是否为空
     */
    public static boolean isDirEmpty(File dir) {
        return isDirEmpty(dir.toPath());
    }

    /**
     * 递归遍历目录以及子目录中的所有文件
     *
     * @param file       当前遍历文件
     * @param fileFilter 文件过滤规则对象，选择要保留的文件
     */
    public static List<File> loopFiles(File file, FileFilter fileFilter) {
        List<File> fileList = new ArrayList<File>();
        if (file == null) {
            return fileList;
        } else if (!file.exists()) {
            return fileList;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (Func.isNotEmpty(files)) {
                for (File tmp : files) {
                    fileList.addAll(loopFiles(tmp, fileFilter));
                }
            }
        } else {
            if (null == fileFilter || fileFilter.accept(file)) {
                fileList.add(file);
            }
        }

        return fileList;
    }

    /**
     * 递归遍历目录以及子目录中的所有文件
     *
     * @param file 当前遍历文件
     */
    public static List<File> loopFiles(File file) {
        return loopFiles(file, null);
    }

    /**
     * 获得指定目录下所有文件<br>
     * 不会扫描子目录
     *
     * @param path 相对ClassPath的目录或者绝对路径目录
     * @return 文件路径列表（如果是jar中的文件，则给定类似.jar!/xxx/xxx的路径）
     * @throws IOException
     */
    public static List<String> listFileNames(String path) {
        if (path == null) {
            return null;
        }
        path = getAbsolutePath(path);
        if (!path.endsWith(String.valueOf(UNIX_SEPARATOR))) {
            path = path + UNIX_SEPARATOR;
        }

        List<String> paths = new ArrayList<String>();
        JarFile jarFile = null;
        int index = path.lastIndexOf(FileKit.JAR_PATH_EXT);
        try {
            if (index == -1) {
                // 普通目录路径
                File[] files = ls(path);
                for (File file : files) {
                    if (file.isFile()) {
                        paths.add(file.getName());
                    }
                }
            } else {
                // jar文件中的路径
                index = index + FileKit.JAR_FILE_EXT.length();
                final String jarPath = path.substring(0, index);
                final String subPath = path.substring(index + 2);
                jarFile = new JarFile(jarPath);
                List<JarEntry> jarEntries = Collections.list(jarFile.entries());
                for (JarEntry entry : jarEntries) {
                    final String name = entry.getName();
                    if (name.startsWith(subPath)) {
                        String nameSuffix = StrKit.removePrefix(name, subPath);
                        if (!nameSuffix.contains(String.valueOf(UNIX_SEPARATOR))) {
                            paths.add(nameSuffix);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new KitException(StrKit.format("Can not read file path of [{}]", path), e);
        } finally {
            IoKit.close(jarFile);
        }
        return paths;
    }

    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (StrKit.isBlank(path)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(getAbsolutePath(path));
    }

    /**
     * 创建File对象
     *
     * @param parent 父目录
     * @param path   文件路径
     * @return File
     */
    public static File file(String parent, String path) {
        if (StrKit.isBlank(path)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(parent, path);
    }

    /**
     * 创建File对象
     *
     * @param parent 父文件对象
     * @param path   文件路径
     * @return File
     */
    public static File file(File parent, String path) {
        if (StrKit.isBlank(path)) {
            throw new NullPointerException("File path is blank!");
        }
        return new File(parent, path);
    }

    /**
     * 创建File对象
     *
     * @param uri 文件URI
     * @return File
     */
    public static File file(URI uri) {
        if (uri == null) {
            throw new NullPointerException("File uri is null!");
        }
        return new File(uri);
    }

    /**
     * 判断文件是否存在，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果存在返回true
     */
    public static boolean exist(String path) {
        return (path == null) ? false : file(path).exists();
    }

    /**
     * 判断文件是否存在，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果存在返回true
     */
    public static boolean exist(File file) {
        return (file == null) ? false : file.exists();
    }

    /**
     * 是否存在匹配文件
     *
     * @param directory 文件夹路径
     * @param regexp    文件夹中所包含文件名的正则表达式
     * @return 如果存在匹配文件返回true
     */
    public static boolean exist(String directory, String regexp) {
        File file = new File(directory);
        if (!file.exists()) {
            return false;
        }

        String[] fileList = file.list();
        if (fileList == null) {
            return false;
        }

        for (String fileName : fileList) {
            if (fileName.matches(regexp)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 指定文件最后修改时间
     *
     * @param file 文件
     * @return 最后修改时间
     */
    public static Date lastModifiedTime(File file) {
        if (!exist(file)) {
            return null;
        }

        return new Date(file.lastModified());
    }

    /**
     * 指定路径文件最后修改时间
     *
     * @param path 路径
     * @return 最后修改时间
     */
    public static Date lastModifiedTime(String path) {
        File file = new File(path);
        if (!exist(file)) {
            return null;
        }

        return new Date(file.lastModified());
    }

    /**
     * 创建文件，如果这个文件存在，直接返回这个文件
     *
     * @param fullFilePath 文件的全路径，使用POSIX风格
     * @return 文件，若路径为null，返回null
     * @throws IOException
     */
    public static File touch(String fullFilePath) throws IOException {
        if (fullFilePath == null) {
            return null;
        }
        return touch(file(fullFilePath));
    }

    /**
     * 创建文件，如果这个文件存在，直接返回这个文件
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IOException
     */
    public static File touch(File file) throws IOException {
        if (Func.isEmpty(file)) {
            return null;
        }

        if (!file.exists() && Func.isNotEmpty(mkParentDirs(file)) && file.createNewFile()) {
            log.debug(file.getName() + " createNewFile success ");
        }
        return file;
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (Func.isNotEmpty(parentFile) && !parentFile.exists() && parentFile.mkdirs()) {
            log.debug(parentFile.getName() + " mkdirs success ");
        }
        return parentFile;
    }

    /**
     * 创建父文件夹，如果存在直接返回此文件夹
     *
     * @param path 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkParentDirs(String path) {
        if (Func.isEmpty(path)) {
            return null;
        }
        return mkParentDirs(file(path));
    }

    /**
     * 删除文件或者文件夹
     *
     * @param fullFileOrDirPath 文件或者目录的路径
     * @return 成功与否
     * @throws IOException
     */
    public static boolean del(String fullFileOrDirPath) throws IOException {
        return del(file(fullFileOrDirPath));
    }

    /**
     * 删除文件或者文件夹
     *
     * @param file 文件对象
     * @return 成功与否
     * @throws IOException
     */
    public static boolean del(File file) throws IOException {
        if (file == null || !file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (Func.isNotEmpty(files)) {
                for (File childFile : files) {
                    boolean isOk = del(childFile);
                    if (!isOk) {
                        // 删除一个出错则本次删除任务失败
                        return false;
                    }
                }
            }

        }
        return file.delete();
    }

    /**
     * 创建文件夹，如果存在直接返回此文件夹
     *
     * @param dirPath 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkdir(String dirPath) {
        if (Func.isEmpty(dirPath)) {
            return null;
        }
        File dir = file(dirPath);
        if (!dir.exists() && dir.mkdirs()) {
            log.debug(dir.getName() + " mkdirs success ");
        }
        return dir;
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir 临时文件创建的所在目录
     * @return 临时文件
     * @throws IOException
     */
    public static File createTempFile(File dir) throws IOException {
        return createTempFile("hutool", null, dir, true);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].tmp
     *
     * @param dir       临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IOException
     */
    public static File createTempFile(File dir, boolean isReCreat) throws IOException {
        return createTempFile("hutool", null, dir, isReCreat);
    }

    /**
     * 创建临时文件<br>
     * 创建后的文件名为 prefix[Randon].suffix From com.jodd.io.FileUtil
     *
     * @param prefix    前缀，至少3个字符
     * @param suffix    后缀，如果null则使用默认.tmp
     * @param dir       临时文件创建的所在目录
     * @param isReCreat 是否重新创建文件（删掉原来的，创建新的）
     * @return 临时文件
     * @throws IOException
     */
    public static File createTempFile(String prefix, String suffix, File dir, boolean isReCreat) throws IOException {
        int exceptionsCount = 0;
        while (true) {
            try {
                File file = File.createTempFile(prefix, suffix, dir).getCanonicalFile();
                if (isReCreat && file.delete() && file.createNewFile()) {
                    log.debug(file.getName() + " delete & createNewFile success ");
                }
                return file;
            } catch (IOException ioex) {
                // fixes java.io.WinNTFileSystem.createFileExclusively access denied
                if (++exceptionsCount >= 50) {
                    throw ioex;
                }
            }
        }
    }

    /**
     * 复制文件或目录<br>
     * 如果目标文件为目录，则将源文件以相同文件名拷贝到目标目录
     *
     * @param srcPath    源文件或目录
     * @param destPath   目标文件或目录
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IOException
     */
    public static File copy(String srcPath, String destPath, boolean isOverride) throws IOException {
        return copy(file(srcPath), file(destPath), isOverride);
    }

    /**
     * 复制文件或目录<br>
     * 情况如下：<br>
     * 1、src和dest都为目录，则讲src下所有文件目录拷贝到dest下<br>
     * 2、src和dest都为文件，直接复制，名字为dest<br>
     * 3、src为文件，dest为目录，将src拷贝到dest目录下<br>
     *
     * @param src        源文件
     * @param dest       目标文件或目录
     * @param isOverride 是否覆盖目标文件
     * @return 目标目录或文件
     * @throws IOException
     */
    public static File copy(File src, File dest, boolean isOverride) throws IOException {
        // check
        if (!src.exists()) {
            throw new FileNotFoundException("File not exist: " + src);
        }
        if (equals(src, dest)) {
            throw new IOException("Files '" + src + "' and '" + dest + "' are equal");
        }

        // 复制目录
        if (src.isDirectory()) {
            if (dest.isFile()) {
                throw new IOException(StrKit.format("Src [{}] is a directory but Dest [{}] is a file!", src.getPath(), dest.getPath()));
            }

            if (!dest.exists() && dest.mkdirs()) {
                log.debug(dest.getName() + " mkdirs success ");
            }
            String[] files = src.list();
            if (Func.isNotEmpty(files)) {
                for (String file : files) {
                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    // 递归复制
                    copy(srcFile, destFile, isOverride);
                }
            }
            return dest;
        }

        // 检查目标
        if (dest.exists()) {
            if (dest.isDirectory()) {
                dest = new File(dest, src.getName());
            }
            if (!isOverride) {
                // 不覆盖，直接跳过
                LogKit.debug("File already exist");
                return dest;
            }
        } else {
            touch(dest);
        }

        // do copy file
        try (FileInputStream input = new FileInputStream(src);
             FileOutputStream output = new FileOutputStream(dest)) {
            IoKit.copy(input, output);
        } catch (Exception e) {
            log.error("error: ", e);
        }

        if (src.length() != dest.length()) {
            throw new KitException("Copy file failed of '" + src + "' to '" + dest + "' due to different sizes");
        }

        return dest;
    }

    /**
     * 移动文件或者目录
     *
     * @param src        源文件或者目录
     * @param dest       目标文件或者目录
     * @param isOverride 是否覆盖目标
     * @throws IOException
     */
    public static void move(File src, File dest, boolean isOverride) throws IOException {
        // check
        if (!src.exists()) {
            throw new FileNotFoundException("File already exist: " + src);
        }
        if (dest.exists()) {
            if (isOverride && dest.delete()) {
                LogKit.debug("File delete success");
            } else {
                LogKit.debug("File already exist");
            }
        }

        // 来源为文件夹，目标为文件
        if (src.isDirectory() && dest.isFile()) {
            throw new IOException(StrKit.format("Can not move directory [{}] to file [{}]", src, dest));
        }

        // 来源为文件，目标为文件夹
        if (src.isFile() && dest.isDirectory()) {
            dest = new File(dest, src.getName());
        }

        if (!src.renameTo(dest)) {
            // 在文件系统不同的情况下，renameTo会失败，此时使用copy，然后删除原文件
            try {
                copy(src, dest, isOverride);
                if (src.delete()) {
                    log.debug(src.getName() + " delete success ");
                }
            } catch (Exception e) {
                throw new IOException(StrKit.format("Move [{}] to [{}] failed!", src, dest), e);
            }
        }
    }

    /**
     * 获取绝对路径<br/>
     * 此方法不会判定给定路径是否有效（文件或目录存在）
     *
     * @param path      相对路径
     * @param baseClass 相对路径所相对的类
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path, Class<?> baseClass) {
        if (path == null) {
            path = StrKit.S_EMPTY;
        }
        if (baseClass == null) {
            return getAbsolutePath(path);
        }
        return StrKit.removePrefix(PATH_FILE_PRE, baseClass.getResource(path).getPath());
    }

    /**
     * 获取绝对路径，相对于classes的根目录<br>
     * 如果给定就是绝对路径，则返回原路径，原路径把所有\替换为/
     *
     * @param path 相对路径
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path) {
        if (path == null) {
            path = StrKit.S_EMPTY;
        } else {
            path = normalize(path);

            if (path.startsWith(StrKit.S_SLASH) || path.matches(PATH_REGEX)) {
                // 给定的路径已经是绝对路径了
                return path;
            }
        }

        // 相对路径
        ClassLoader classLoader = ClassKit.getClassLoader();
        URL url = classLoader.getResource(path);
        return url != null ? url.getPath() : ClassKit.getClassPath() + path;
    }

    /**
     * 获取标准的绝对路径
     *
     * @param file 文件
     * @return 绝对路径
     */
    public static String getAbsolutePath(File file) {
        if (file == null) {
            return null;
        }

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * 判断是否为目录，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果为目录true
     */
    public static boolean isDirectory(String path) {
        return (path == null) ? false : file(path).isDirectory();
    }

    /**
     * 判断是否为目录，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果为目录true
     */
    public static boolean isDirectory(File file) {
        return (file == null) ? false : file.isDirectory();
    }

    /**
     * 判断是否为文件，如果path为null，则返回false
     *
     * @param path 文件路径
     * @return 如果为文件true
     */
    public static boolean isFile(String path) {
        return (path == null) ? false : file(path).isDirectory();
    }

    /**
     * 判断是否为文件，如果file为null，则返回false
     *
     * @param file 文件
     * @return 如果为文件true
     */
    public static boolean isFile(File file) {
        return (file == null) ? false : file.isDirectory();
    }

    /**
     * 检查两个文件是否是同一个文件
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return 是否相同
     */
    public static boolean equals(File file1, File file2) {
        try {
            file1 = file1.getCanonicalFile();
            file2 = file2.getCanonicalFile();
        } catch (IOException ignore) {
            return false;
        }
        return file1.equals(file2);
    }

    /**
     * 获得最后一个文件路径分隔符的位置
     *
     * @param filePath 文件路径
     * @return 最后一个文件路径分隔符的位置
     */
    public static int indexOfLastSeparator(String filePath) {
        if (filePath == null) {
            return -1;
        }
        int lastUnixPos = filePath.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = filePath.lastIndexOf(WINDOWS_SEPARATOR);
        return (lastUnixPos >= lastWindowsPos) ? lastUnixPos : lastWindowsPos;
    }

    /**
     * 判断文件是否被改动<br>
     * 如果文件对象为 null 或者文件不存在，被视为改动
     *
     * @param file           文件对象
     * @param lastModifyTime 上次的改动时间
     * @return 是否被改动
     */
    public static boolean isModifed(File file, long lastModifyTime) {
        if (null == file || false == file.exists()) {
            return true;
        }
        return file.lastModified() != lastModifyTime;
    }

    /**
     * 修复路径<br>
     * 1. 统一用 / <br>
     * 2. 多个 / 转换为一个
     *
     * @param path 原路径
     * @return 修复后的路径
     */
    public static String normalize(String path) {
        return path.replaceAll("[/\\\\]{1,}", "/");
    }

    /**
     * 获得相对子路径
     *
     * @param rootDir  绝对父路径
     * @param filePath 文件路径
     * @return 相对子路径
     */
    public static String subPath(String rootDir, String filePath) {
        return subPath(rootDir, file(filePath));
    }

    /**
     * 获得相对子路径
     *
     * @param rootDir 绝对父路径
     * @param file    文件
     * @return 相对子路径
     */
    public static String subPath(String rootDir, File file) {
        if (StrKit.isEmpty(rootDir)) {
        }

        String subPath;
        try {
            subPath = file.getCanonicalPath();
        } catch (IOException e) {
            throw new KitException(e);
        }

        if (StrKit.isNotEmpty(rootDir) && StrKit.isNotEmpty(subPath)) {
            rootDir = normalize(rootDir);
            subPath = normalize(subPath);

            if (subPath != null && subPath.toLowerCase().startsWith(subPath.toLowerCase())) {
                subPath = subPath.substring(rootDir.length() + 1);
            }
        }
        return subPath;
    }

    // -------------------------------------------------------------------------------------------- name start

    /**
     * 返回主文件名
     *
     * @param file 文件
     * @return 主文件名
     */
    public static String mainName(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        return mainName(file.getName());
    }

    /**
     * 返回主文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     */
    public static String mainName(String fileName) {
        if (StrKit.isBlank(fileName) || false == fileName.contains(StrKit.S_DOT)) {
            return fileName;
        }
        return StrKit.subPre(fileName, fileName.lastIndexOf(StrKit.S_DOT));
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String extName(File file) {
        if (null == file) {
            return null;
        }
        if (file.isDirectory()) {
            return null;
        }
        return extName(file.getName());
    }

    /**
     * 获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String extName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(StrKit.S_DOT);
        if (index == -1) {
            return StrKit.S_EMPTY;
        } else {
            String ext = fileName.substring(index + 1);
            // 扩展名中不能包含路径相关的符号
            return (ext.contains(String.valueOf(UNIX_SEPARATOR)) || ext.contains(String.valueOf(WINDOWS_SEPARATOR))) ? StrKit.S_EMPTY : ext;
        }
    }
    // -------------------------------------------------------------------------------------------- name end

    // -------------------------------------------------------------------------------------------- in start

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     * @throws FileNotFoundException
     */
    public static BufferedInputStream getInputStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     * @throws FileNotFoundException
     */
    public static BufferedInputStream getInputStream(String path) throws FileNotFoundException {
        return getInputStream(file(path));
    }

    /**
     * 获得一个文件读取器
     *
     * @param file 文件
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getUtf8Reader(File file) throws IOException {
        return getReader(file, CharsetKit.UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path 文件路径
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getUtf8Reader(String path) throws IOException {
        return getReader(path, CharsetKit.UTF_8);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getReader(File file, String charsetName) throws IOException {
        return IoKit.getReader(getInputStream(file), charsetName);
    }

    /**
     * 获得一个文件读取器
     *
     * @param file    文件
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getReader(File file, Charset charset) throws IOException {
        return IoKit.getReader(getInputStream(file), charset);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path        绝对路径
     * @param charsetName 字符集
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getReader(String path, String charsetName) throws IOException {
        return getReader(file(path), charsetName);
    }

    /**
     * 获得一个文件读取器
     *
     * @param path    绝对路径
     * @param charset 字符集
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedReader getReader(String path, Charset charset) throws IOException {
        return getReader(file(path), charset);
    }

    // -------------------------------------------------------------------------------------------- in end

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param file 文件
     * @return 字节码
     * @throws IOException
     */
    public static byte[] readBytes(File file) throws IOException {
        // check
        if (!file.exists()) {
            throw new FileNotFoundException("File not exist: " + file);
        }
        if (!file.isFile()) {
            throw new IOException("Not a file:" + file);
        }

        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        byte[] bytes = new byte[(int) len];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
        } finally {
            IoKit.close(in);
        }

        return bytes;
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return 内容
     * @throws IOException
     */
    public static String readUtf8String(File file) throws IOException {
        return readString(file, CharsetKit.UTF_8);
    }

    /**
     * 读取文件内容
     *
     * @param path 文件路径
     * @return 内容
     * @throws IOException
     */
    public static String readUtf8String(String path) throws IOException {
        return readString(path, CharsetKit.UTF_8);
    }

    /**
     * 读取文件内容
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return 内容
     * @throws IOException
     */
    public static String readString(File file, String charsetName) throws IOException {
        return new String(readBytes(file), charsetName);
    }

    /**
     * 读取文件内容
     *
     * @param file    文件
     * @param charset 字符集
     * @return 内容
     * @throws IOException
     */
    public static String readString(File file, Charset charset) throws IOException {
        return new String(readBytes(file), charset);
    }

    /**
     * 读取文件内容
     *
     * @param path        文件路径
     * @param charsetName 字符集
     * @return 内容
     * @throws IOException
     */
    public static String readString(String path, String charsetName) throws IOException {
        return readString(file(path), charsetName);
    }

    /**
     * 读取文件内容
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 内容
     * @throws IOException
     */
    public static String readString(String path, Charset charset) throws IOException {
        return readString(file(path), charset);
    }

    /**
     * 读取文件内容
     *
     * @param url     文件URL
     * @param charset 字符集
     * @return 内容
     * @throws IOException
     */
    public static String readString(URL url, String charset) throws IOException {
        if (url == null) {
            throw new RuntimeException("Empty url provided!");
        }

        InputStream in = null;
        try {
            in = url.openStream();
            return IoKit.read(in, charset);
        } finally {
            IoKit.close(in);
        }
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param path       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IOException
     */
    public static <T extends Collection<String>> T readLines(String path, String charset, T collection) throws IOException {
        return readLines(file(path), charset, collection);
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file       文件路径
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IOException
     */
    public static <T extends Collection<String>> T readLines(File file, String charset, T collection) throws IOException {
        BufferedReader reader = null;
        try {
            reader = getReader(file, charset);
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                collection.add(line);
            }
            return collection;
        } finally {
            IoKit.close(reader);
        }
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url        文件的URL
     * @param charset    字符集
     * @param collection 集合
     * @return 文件中的每行内容的集合
     * @throws IOException
     */
    public static <T extends Collection<String>> T readLines(URL url, String charset, T collection) throws IOException {
        InputStream in = null;
        try {
            in = url.openStream();
            return IoKit.readLines(in, charset, collection);
        } finally {
            IoKit.close(in);
        }
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param url     文件的URL
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IOException
     */
    public static List<String> readLines(URL url, String charset) throws IOException {
        return readLines(url, charset, new ArrayList<String>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IOException
     */
    public static List<String> readLines(String path, String charset) throws IOException {
        return readLines(path, charset, new ArrayList<String>());
    }

    /**
     * 从文件中读取每一行数据
     *
     * @param file    文件
     * @param charset 字符集
     * @return 文件中的每行内容的集合List
     * @throws IOException
     */
    public static List<String> readLines(File file, String charset) throws IOException {
        return readLines(file, charset, new ArrayList<String>());
    }

    /**
     * 按照给定的readerHandler读取文件中的数据
     *
     * @param readerHandler Reader处理类
     * @param path          文件的绝对路径
     * @param charset       字符集
     * @return 从文件中load出的数据
     * @throws IOException
     */
    public static <T> T load(ReaderHandler<T> readerHandler, String path, String charset) throws IOException {
        BufferedReader reader = null;
        T result = null;
        try {
            reader = getReader(path, charset);
            result = readerHandler.handle(reader);
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IoKit.close(reader);
        }
        return result;
    }

    // -------------------------------------------------------------------------------------------- out start

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     * @throws IOException
     */
    public static BufferedOutputStream getOutputStream(File file) throws IOException {
        return new BufferedOutputStream(new FileOutputStream(touch(file)));
    }

    /**
     * 获得一个输出流对象
     *
     * @param path 输出到的文件路径，绝对路径
     * @return 输出流对象
     * @throws IOException
     */
    public static BufferedOutputStream getOutputStream(String path) throws IOException {
        return getOutputStream(touch(path));
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param path        输出路径，绝对路径
     * @param charsetName 字符集
     * @param isAppend    是否追加
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedWriter getWriter(String path, String charsetName, boolean isAppend) throws IOException {
        return getWriter(touch(path), Charset.forName(charsetName), isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param path     输出路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedWriter getWriter(String path, Charset charset, boolean isAppend) throws IOException {
        return getWriter(touch(path), charset, isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param file        输出文件
     * @param charsetName 字符集
     * @param isAppend    是否追加
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedWriter getWriter(File file, String charsetName, boolean isAppend) throws IOException {
        return getWriter(file, Charset.forName(charsetName), isAppend);
    }

    /**
     * 获得一个带缓存的写入对象
     *
     * @param file     输出文件
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return BufferedReader对象
     * @throws IOException
     */
    public static BufferedWriter getWriter(File file, Charset charset, boolean isAppend) throws IOException {
        if (!file.exists() && file.createNewFile()) {
            log.debug(file.getName() + " createNewFile success ");
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, isAppend), charset));
    }

    /**
     * 获得一个打印写入对象，可以有print
     *
     * @param path     输出路径，绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IOException
     */
    public static PrintWriter getPrintWriter(String path, String charset, boolean isAppend) throws IOException {
        return new PrintWriter(getWriter(path, charset, isAppend));
    }

    /**
     * 获得一个打印写入对象，可以有print
     *
     * @param file     文件
     * @param charset  字符集
     * @param isAppend 是否追加
     * @return 打印对象
     * @throws IOException
     */
    public static PrintWriter getPrintWriter(File file, String charset, boolean isAppend) throws IOException {
        return new PrintWriter(getWriter(file, charset, isAppend));
    }

    // -------------------------------------------------------------------------------------------- out end

    /**
     * 将String写入文件，覆盖模式，字符集为UTF-8
     *
     * @param content 写入的内容
     * @param path    文件路径
     * @return 写入的文件
     * @throws IOException
     */
    public static File writeUtf8String(String content, String path) throws IOException {
        return writeString(content, path, CharsetKit.UTF_8);
    }

    /**
     * 将String写入文件，覆盖模式，字符集为UTF-8
     *
     * @param content 写入的内容
     * @param file    文件
     * @return 写入的文件
     * @throws IOException
     */
    public static File writeUtf8String(String content, File file) throws IOException {
        return writeString(content, file, CharsetKit.UTF_8);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     * @param content 写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IOException
     */
    public static File writeString(String content, String path, String charset) throws IOException {
        return writeString(content, touch(path), charset);
    }

    /**
     * 将String写入文件，覆盖模式
     *
     * @param content 写入的内容
     * @param file    文件
     * @param charset 字符集
     * @throws IOException
     */
    public static File writeString(String content, File file, String charset) throws IOException {
        PrintWriter writer = null;
        try {
            writer = getPrintWriter(file, charset, false);
            writer.print(content);
            writer.flush();
        } finally {
            IoKit.close(writer);
        }
        return file;
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param content 写入的内容
     * @param path    文件路径
     * @param charset 字符集
     * @return 写入的文件
     * @throws IOException
     */
    public static File appendString(String content, String path, String charset) throws IOException {
        return appendString(content, touch(path), charset);
    }

    /**
     * 将String写入文件，追加模式
     *
     * @param content 写入的内容
     * @param file    文件
     * @param charset 字符集
     * @return 写入的文件
     * @throws IOException
     */
    public static File appendString(String content, File file, String charset) throws IOException {
        PrintWriter writer = null;
        try {
            writer = getPrintWriter(file, charset, true);
            writer.print(content);
            writer.flush();
        } finally {
            IoKit.close(writer);
        }
        return file;
    }

    /**
     * 将列表写入文件，覆盖模式
     *
     * @param list    列表
     * @param path    绝对路径
     * @param charset 字符集
     * @throws IOException
     */
    public static <T> void writeLines(Collection<T> list, String path, String charset) throws IOException {
        writeLines(list, path, charset, false);
    }

    /**
     * 将列表写入文件，追加模式
     *
     * @param list    列表
     * @param path    绝对路径
     * @param charset 字符集
     * @throws IOException
     */
    public static <T> void appendLines(Collection<T> list, String path, String charset) throws IOException {
        writeLines(list, path, charset, true);
    }

    /**
     * 将列表写入文件
     *
     * @param list     列表
     * @param path     绝对路径
     * @param charset  字符集
     * @param isAppend 是否追加
     * @throws IOException
     */
    public static <T> void writeLines(Collection<T> list, String path, String charset, boolean isAppend) throws IOException {
        PrintWriter writer = null;
        try {
            writer = getPrintWriter(path, charset, isAppend);
            for (T t : list) {
                if (t != null) {
                    writer.println(t.toString());
                    writer.flush();
                }
            }
        } finally {
            IoKit.close(writer);
        }
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @param path 目标文件
     * @return File
     * @throws IOException
     */
    public static File writeBytes(byte[] data, String path) throws IOException {
        return writeBytes(data, touch(path));
    }

    /**
     * 写数据到文件中
     *
     * @param dest 目标文件
     * @param data 数据
     * @return dest
     * @throws IOException
     */
    public static File writeBytes(byte[] data, File dest) throws IOException {
        return writeBytes(data, dest, 0, data.length, false);
    }

    /**
     * 写入数据到文件
     *
     * @param data   数据
     * @param dest   目标文件
     * @param off
     * @param len
     * @param append
     * @return dest
     * @throws IOException
     */
    public static File writeBytes(byte[] data, File dest, int off, int len, boolean append) throws IOException {
        if (dest.exists()) {
            if (!dest.isFile()) {
                throw new IOException("Not a file: " + dest);
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest, append);
            out.write(data, off, len);
            out.flush();
        } finally {
            IoKit.close(out);
        }
        return dest;
    }

    /**
     * 将流的内容写入文件<br>
     *
     * @param dest 目标文件
     * @param in   输入流
     * @return dest
     * @throws IOException
     */
    public static File writeFromStream(InputStream in, File dest) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            IoKit.copy(in, out);
        } finally {
            IoKit.close(out);
        }
        return dest;
    }

    /**
     * 将流的内容写入文件<br>
     *
     * @param in           输入流
     * @param fullFilePath 文件绝对路径
     * @return dest
     * @throws IOException
     */
    public static File writeFromStream(InputStream in, String fullFilePath) throws IOException {
        return writeFromStream(in, touch(fullFilePath));
    }

    /**
     * 将文件写入流中
     *
     * @param file 文件
     * @param out  流
     * @throws IOException
     */
    public static void writeToStream(File file, OutputStream out) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            IoKit.copy(in, out);
        } finally {
            IoKit.close(in);
        }
    }

    /**
     * 将流的内容写入文件<br>
     *
     * @param fullFilePath 文件绝对路径
     * @param out          输出流
     * @throws IOException
     */
    public static void writeToStream(String fullFilePath, OutputStream out) throws IOException {
        writeToStream(touch(fullFilePath), out);
    }

    /**
     * 可读的文件大小
     *
     * @param file 文件
     * @return 大小
     */
    public static String readableFileSize(File file) {
        return readableFileSize(file.length());
    }

    /**
     * 可读的文件大小<br>
     * 参考 http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
     *
     * @param size Long类型大小
     * @return 大小
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(NUM_1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(NUM_1024, digitGroups)) + " " + units[digitGroups];
    }

    // -------------------------------------------------------------------------- Interface start

    /**
     * Reader处理接口
     *
     * @param <T>
     */
    public interface ReaderHandler<T> {
        /**
         * 处理
         *
         * @param reader
         * @return
         * @throws IOException
         */
        T handle(BufferedReader reader) throws IOException;
    }
    // -------------------------------------------------------------------------- Interface end

    /**
     * 将网络上的图片保存在本地
     *
     * @param httpUrl  图片地址
     * @param fileName 保存路径和名称
     * @throws Exception
     */
    public static void saveAs(String httpUrl, String fileName) {
        try {
            // new一个URL对象
            URL url = new URL(httpUrl);
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(fileName);

            try (InputStream inStream = conn.getInputStream();
                 // 创建输出流
                 FileOutputStream outStream = new FileOutputStream(imageFile)) {
                // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                // 写入数据
                outStream.write(data);
                outStream.flush();
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    public static byte[] readInputStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            // 创建一个Buffer字符串
            byte[] buffer = new byte[NUM_1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(outStream);
        }
    }

    /**
     * 文件路径是否存在，不存在就创建，然会是否成功
     *
     * @param path
     * @return
     */
    public static Boolean validatePath(String path) {
        Boolean ret = false;
        File file = new File(path);

        if (!file.exists()) {
            ret = file.mkdirs();
        }

        return ret;
    }

    public static void writeFile(String path, boolean append, String content) {
        File myFilePath = new File(path);
        FileOutputStream outputStream = null;
        OutputStreamWriter resultFile = null;
        PrintWriter myFile = null;
        try {
            if (!myFilePath.exists() && myFilePath.createNewFile()) {
                log.debug(myFilePath.getName() + " createNewFile success ");
            }

            outputStream = new FileOutputStream(myFilePath, append);
            resultFile = new OutputStreamWriter(outputStream, CharsetKit.DEFAULT_ENCODE);
            myFile = new PrintWriter(resultFile);
            myFile.println(content);
            myFile.flush();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(myFile, resultFile, outputStream);
        }
    }

    public static List<String> readFileAsList(String path) {
        List<String> ls = new ArrayList<>();
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, CharsetKit.DEFAULT_ENCODE));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                ls.add(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return ls;
    }

    public static Set<String> readFileAsSet(String path) {
        Set<String> set = new HashSet<>();
        InputStream in = null;
        BufferedReader reader = null;

        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, CharsetKit.DEFAULT_ENCODE));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                if (!set.contains(line)) {
                    set.add(line);
                }
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return set;
    }

    public static String readFileAsString(String path) {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, CharsetKit.DEFAULT_ENCODE));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return sb.toString();
    }


    public static String readFileAsString(File file) {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in, CharsetKit.DEFAULT_ENCODE));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return sb.toString();
    }

    public static String readInputStreamAsString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, CharsetKit.DEFAULT_ENCODE));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader);
        }
        return sb.toString();
    }

    public static List<String> readFileAsArray(String path) {
        List<String> result = Lists.newArrayList();
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, CharsetKit.DEFAULT_ENCODE));

            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                result.add(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }

        return result;
    }

    public static List<String> readFileAsList(String path, String encode) {
        List<String> ls = new ArrayList<>();
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, encode));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (StrKit.S_EMPTY.equals(line)) {
                    continue;
                }
                ls.add(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return ls;
    }

    public static String readFileByCharAsString(String path, String encode) {
        String content = null;
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in, encode));

            StringBuffer contentbuffer = new StringBuffer();
            char[] temp = new char[NUM_1024];
            int size;
            while ((size = reader.read(temp, 0, NUM_1024)) != -1) {
                String tempstr = new String(temp, 0, size);
                contentbuffer.append(tempstr);
            }

            content = contentbuffer.toString();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(reader, in);
        }
        return content;
    }

    public static boolean inputStreamToFile(InputStream in, String pathname) {
        File f = new File(pathname);
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);
            byte[] buf = new byte[NUM_1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            return true;
        } catch (IOException e) {
            throw new KitException(e);
        } finally {
            IoKit.close(out);
        }
    }

    public static boolean byteArrayToFile(byte[] in, String pathname) {
        if (null == in || in.length <= 0) {
            return false;
        }

        InputStream stream = new ByteArrayInputStream(in);
        return inputStreamToFile(stream, pathname);
    }

    public static void writeFile(String path, String name, boolean append, String content) {
        File myFilePath = new File(path + "\\" + name);
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter resultFile = null;
        PrintWriter myFile = null;

        try {
            boolean success = (new File(path)).mkdirs();
            if (success) {
                System.out.println("Directories: " + path + " created");
            }

            if (!myFilePath.exists() && myFilePath.createNewFile()) {
                log.debug(myFilePath.getName() + " createNewFile success ");
            }

            fileOutputStream = new FileOutputStream(myFilePath, append);
            resultFile = new OutputStreamWriter(fileOutputStream, CharsetKit.DEFAULT_ENCODE);
            myFile = new PrintWriter(resultFile);
            myFile.println(content);
            myFile.flush();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(myFile, resultFile);
        }
    }

    /**
     * 将Url保存到本地
     *
     * @param imageUrl
     * @param destinationFile
     * @throws IOException
     */
    public static void saveImage(String imageUrl, String destinationFile) {
        try (InputStream is = new URL(imageUrl).openStream();
             OutputStream os = new FileOutputStream(destinationFile)) {
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取文件后缀
     *
     * @param filename
     * @return
     */
    public static String getSuffix(String filename) {
        String suffix = StrKit.S_EMPTY;
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    /**
     * 创建文件夹
     *
     * @param s
     * @param overMode 是否覆盖
     * @return
     */
    public static File createDir(String s, boolean overMode) {
        File f = new File(s);
        if (overMode) {
            delDirs(f);
        }
        if (!f.exists() && f.mkdirs()) {
            log.debug(f.getName() + " mkdirs success ");
        }
        return f;
    }

    /**
     * 覆盖性创建文件夹
     *
     * @param root
     * @param filename
     * @return
     * @throws IOException
     */
    public static File createDir(File root, String filename) {
        return createDir(root, filename, true);
    }

    /**
     * 创建文件夹
     *
     * @param root
     * @param filename
     * @return
     * @throws IOException
     */
    public static File createDir(File root, String filename, boolean overMode) {
        return createDir(new File(root, filename).getAbsolutePath(), overMode);
    }

    /**
     * 覆盖性创建文件
     *
     * @param
     * @return
     * @throws IOException
     */
    public static File createFile(File dir, String fname) {
        return createFile(dir, fname, true);
    }

    /**
     * 创建文件
     *
     * @param
     * @return
     * @throws IOException
     */
    public static File createFile(File dir, String fname, boolean overMode) {
        return createFile(new File(dir, fname), overMode);
    }

    /**
     * 覆盖性创建文件
     *
     * @param f
     * @param
     * @return
     * @throws IOException
     */
    public static File createFile(File f) {
        return createFile(f, true);
    }

    /**
     * 创建文件
     *
     * @param f
     * @param overMode 是否覆盖
     * @return
     * @throws IOException
     */
    public static File createFile(File f, boolean overMode) {
        if (overMode && f.delete()) {
            log.debug(f.getName() + " delete success ");
        }

        try {
            if (!f.exists() && f.createNewFile()) {
                log.debug(f.getName() + " createNewFile success ");
            }
        } catch (IOException e) {
            throw new KitException(e);
        }
        return f;
    }

    /**
     * 写入内容
     *
     * @param s
     * @param f
     * @param encode
     * @throws IOException
     */
    public static void writeTo(String s, File f, String encode) {
        try {
            IoKit.writeTo(s, new FileOutputStream(f), encode);
        } catch (IOException e) {
            throw new KitException(e);
        }
    }

    /**
     * 读取文件的内容，并将文件内容以字符串的形式返回。
     *
     * @param fileName
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFile(String fileName, String encode) {
        File file = new File(fileName);
        return readFile(file, encode);
    }

    public static String readFile(File file, String encode) {
        if (!file.exists()) {
            return StrKit.S_EMPTY;
        }
        if (!file.isFile()) {
            return StrKit.S_EMPTY;
        }
        FileInputStream input = null;
        StringBuffer context = null;
        try {
            input = new FileInputStream(file);
            context = IoKit.getContext(input, encode);
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(input);
        }
        return context.toString();
    }

    /**
     * 删除文件夹 包括内容
     *
     * @param f
     */
    public static void delDirs(File f) {
        if (f.exists()) {
            if (f.isFile() && f.delete()) {
                log.debug(f.getName() + " delete success ");
            } else {
                File[] sz = f.listFiles();
                if (Func.isNotEmpty(sz)) {
                    if (sz.length < 1 && f.delete()) {
                        log.debug(f.getName() + " delete success ");
                    } else {
                        for (File file : sz) {
                            delDirs(file);
                        }
                        if (f.delete()) {
                            log.debug(f.getName() + " delete success ");
                        }
                    }
                }
            }
        }
    }

    /**
     * 重写内容
     *
     * @param s
     * @param f
     * @param encode
     * @throws IOException
     */
    public static void reWriteTo(String s, File f, String encode) {
        writeTo(s, createFile(f, true), encode);
    }


    /**
     * 拷贝文件夹
     *
     * @param src
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(File src, String targetDir) {
        // 新建目标目录
        createDir(targetDir, false);
        // 获取源文件夹当前下的文件或目录
        File[] files = src.listFiles();
        if (Func.isNotEmpty(files)) {
            for (File file : files) {
                if (file.isFile()) {
                    // 目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() +
                        File.separator + file.getName());
                    copyFile(file, targetFile);
                }
                if (file.isDirectory()) {
                    String dir1 = src.getAbsolutePath() + StrKit.S_SLASH + file.getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + StrKit.S_SLASH + file.getName();
                    copyDirectiory(new File(dir1), dir2);
                }
            }
        }
    }


    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(sourceFile);
            output = new FileOutputStream(targetFile);
            IoKit.streamCopy(input, output, null);
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(output, input);
        }
    }

    /**
     * 读取Properties文件
     *
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String filename) {
        InputStream in = null;
        Properties props = new Properties();
        try {
            File initfile = seachFile(filename);
            if (initfile.exists()) {
                in = new FileInputStream(initfile);
            } else {
                in = FileKit.class.getClassLoader().getResourceAsStream(filename);
                if (in == null) {
                    in = new FileInputStream(initfile);
                }
            }
            props.load(in);
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(in);
        }
        return props;
    }

    /**
     * 获取项目中的某个文件目录
     *
     * @param filename
     * @return
     */
    public static File seachFile(String filename) {
        File initfile = new File(filename);
        if (!initfile.exists()) {
            String path = FileKit.class.getResource("/").getFile().substring(1) + filename;
            initfile = new File(path);
        }
        return initfile;
    }

    /**
     * 获取文件名后缀
     *
     * @param filename
     * @return
     */
    public static String getExt(String filename) {
        String[] s = filename.split("\\.");
        String ext = StrKit.S_EMPTY;
        if (s.length > 1) {
            ext = s[s.length - 1];
        }
        return ext;
    }

    /**
     * 检查文件后缀名是否符合
     *
     * @param name
     * @param suffix
     * @return
     */
    public static boolean checkSuffix(String name, String suffix) {
        return null != name && name.endsWith(suffix);
    }

    /**
     * 根据后缀结尾过滤出所有的子文件列表
     *
     * @param dir
     * @param suffix
     * @return
     */
    public static List<File> listAllFiles(File dir, final String suffix) {
        final ArrayList<File> li = new ArrayList<File>();
        if (!dir.exists()) {
            return li;
        } else {
            if (dir.isDirectory()) {
                File[] fl = dir.listFiles();
                if (Func.isNotEmpty(fl)) {
                    for (File f : fl) {
                        if (f.isFile()) {
                            if (checkSuffix(f.getName(), suffix)) {
                                li.add(f);
                            }
                        } else {
                            li.addAll(listAllFiles(f, suffix));
                        }
                    }
                }
            } else {
                if (checkSuffix(dir.getName(), suffix)) {
                    li.add(dir);
                }
            }
            return li;
        }

    }

    public static final String DEFAULT_UPLOAD_DIR_PREFIX = "/data/temp/";

    /**
     * 上传文件至目标路径
     *
     * @param srcFile
     * @param destDir
     * @return
     */
    public boolean upload(File srcFile, String destDir) {
        if (!srcFile.exists()) {
            return false;
        }

        if (StrKit.isBlank(destDir)) {
            return false;
        }

        File destFile = new File(destDir);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            throw new KitException(e);
        }
        return true;
    }

    /**
     * 上传文件至目标路径
     *
     * @param srcUrl
     * @param destDir
     * @return
     */
    public boolean upload(URL srcUrl, String destDir) {
        if (srcUrl == null) {
            return false;
        }

        if (StrKit.isBlank(destDir)) {
            return false;
        }

        File destFile = new File(destDir);
        try {
            FileUtils.copyURLToFile(srcUrl, destFile);
        } catch (Exception e) {
            throw new KitException(e);
        }
        return true;
    }

    /**
     * 上传文件
     *
     * @param srcFile 源文件
     * @return 上传成功后，返回文件的保存路径
     */
    public String upload(File srcFile) {
        boolean result = upload(srcFile, getFileName(srcFile));
        if (!result) {
            return StrKit.S_EMPTY;
        }
        return getFileName(srcFile);
    }

    /**
     * 上传文件
     *
     * @param srcUrl 源文件的url
     * @return 上传成功，返回文件的保存路径，上传失败，返回空串
     */
    public String upload(URL srcUrl) {
        boolean result = upload(srcUrl, getFileName(srcUrl));
        if (!result) {
            return StrKit.S_EMPTY;
        }
        return getFileName(srcUrl);
    }

    private String getFileName(File file) {
        return getFileName(file.getAbsolutePath());
    }

    private String getFileName(URL url) {
        return getFileName(url.getPath());
    }

    private String getFileName(String filePath) {
        int index = filePath.lastIndexOf(StrKit.S_SLASH);
        if (index > -1) {
            return DEFAULT_UPLOAD_DIR_PREFIX + StrKit.subSuf(filePath, index + 1);
        }
        return DEFAULT_UPLOAD_DIR_PREFIX + filePath;
    }
}
