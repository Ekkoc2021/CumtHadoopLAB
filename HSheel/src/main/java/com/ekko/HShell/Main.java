package com.ekko.HShell;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
/**
 * @Author YLL
 * @Date 2023/6/8 20:20
 * @PackageName:com.ekko.HShell
 * @ClassName: Main
 * @Description:
 * 1.使用HShell -cp 本地路径 HDFS路径，将文件从Linux本地文件系统拷贝到HDFS指定路径上。
 *
 * 2.使用HShell -rm 路径删除文件
 *
 * 3.使用HShell -rm -r 路径删除目录
 *
 * 4.使用HShell -cp -r 本地目录路径 HDFS路径，将目录从Linux本地拷贝到HDFS指定路径上。
 *
 * 5.使用HShell -list 路径显示某个文件的信息或者某个目录的信息
 *
 * 6.使用HShell -mv 路径 路径移动文件或者重命名文件
 *
 * 7.使用HShell -find 文件名 目录实现在目录下递归查找某个文件名的文件。
 *
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: HShell.jar <command> <arguments>");
            System.exit(1);
        }

        String command = args[0];
        String[] arguments = new String[args.length - 1];
        System.arraycopy(args, 1, arguments, 0, arguments.length);

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        try {
            switch (command) {
                case "-cp":
                    copyToLocalFileSystem(arguments[0], arguments[1], fs);
                    break;
                case "-rm":
                    if (arguments.length > 1 && arguments[0].equals("-r")) {
                        deleteDirectory(arguments[1], fs);
                    } else {
                        deleteFile(arguments[0], fs);
                    }
                    break;
                case "-list":
                    listStatus(arguments[0], fs);
                    break;
                case "-mv":
                    moveFile(arguments[0], arguments[1], fs);
                    break;
                case "-find":
                    findFile(arguments[0], arguments[1], fs);
                    break;
                default:
                    System.out.println("Invalid command: " + command);
                    System.exit(1);
            }
        } finally {
            fs.close();
        }
    }
//1.使用HShell -cp 本地路径 HDFS路径，将文件从Linux本地文件系统拷贝到HDFS指定路径上。
    private static void copyToLocalFileSystem(String source, String destination, FileSystem fs) throws IOException {
        /**
         * 传入参数,调用copyFromLocalFile api
         */
        Path sourcePath = new Path(source);
        Path destinationPath = new Path(destination);

        fs.copyFromLocalFile(sourcePath, destinationPath);

        System.out.println("File copied successfully.");
    }
// 2.使用HShell -rm 路径删除文件
    private static void deleteFile(String path, FileSystem fs) throws IOException {
        Path filePath = new Path(path);

        boolean deleted = fs.delete(filePath, false);

        if (deleted) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete file or file does not exist.");
        }
    }
//3.使用HShell -rm -r 路径删除目录
    private static void deleteDirectory(String path, FileSystem fs) throws IOException {
        Path directoryPath = new Path(path);

        boolean deleted = fs.delete(directoryPath, true);

        if (deleted) {
            System.out.println("Directory deleted successfully.");
        } else {
            System.out.println("Failed to delete directory or directory does not exist.");
        }
    }
//4.使用HShell -cp -r 本地目录路径 HDFS路径，将目录从Linux本地拷贝到HDFS指定路径上。
    private static void listStatus(String path, FileSystem fs) throws IOException {
        Path directoryPath = new Path(path);

        if (fs.exists(directoryPath)) {
            System.out.println("Listing for: " + directoryPath);
            System.out.println("--------------------------------------");
            for (org.apache.hadoop.fs.FileStatus fileStatus : fs.listStatus(directoryPath)) {
                System.out.println(fileStatus.getPath());
            }
            System.out.println("--------------------------------------");
        } else {
            System.out.println("Directory does not exist: " + directoryPath);
        }
    }
//5.使用HShell -list 路径显示某个文件的信息或者某个目录的信息
    private static void moveFile(String source, String destination, FileSystem fs) throws IOException {
        Path sourcePath = new Path(source);
        Path destinationPath = new Path(destination);

        boolean renamed = fs.rename(sourcePath, destinationPath);

        if (renamed) {
            System.out.println("File moved successfully.");
        } else {
            System.out.println("Failed to move file or file does not exist.");
        }
    }
//    6.使用HShell -mv 路径 路径移动文件或者重命名文件

    private static void findFile(String fileName, String directory, FileSystem fs) throws IOException {
        Path directoryPath = new Path(directory);

        if (fs.exists(directoryPath)) {
            searchFiles(fileName, directoryPath, fs);
        } else {
            System.out.println("Directory does not exist: " + directoryPath);
        }
    }
//7.使用HShell -find 文件名 目录实现在目录下递归查找某个文件名的文件。
    private static void searchFiles(String fileName, Path path, FileSystem fs) throws IOException {
        for (org.apache.hadoop.fs.FileStatus fileStatus : fs.listStatus(path)) {
            if (fileStatus.isDirectory()) {
                searchFiles(fileName, fileStatus.getPath(), fs);
            } else if (fileStatus.isFile() && fileStatus.getPath().getName().equals(fileName)) {
                System.out.println("Found file: " + fileStatus.getPath());
            }
        }
    }
}
