package com.tang.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

    private String ffmpegEXE;

    public MergeVideoMp3(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    //ffmpeg -i veido -i mp3 -t seconds -y newname
    public  void convertor(String videoInputPath, String mp3InputPath,
                          double seconds, String outputPath) {

        List<String> command = new ArrayList<>();

        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(outputPath);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getErrorStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader reader1 = new BufferedReader(reader);
            //读取每行
            String line = "";
            while ((reader1.readLine()) != null) {

            }
            if (reader1 != null) {
                reader1.close();
            }if (reader != null) {
                reader.close();
            }if (inputStream != null) {
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public  void converVideoCover(String videoInputPath,String outputPath) {

        List<String> command = new ArrayList<>();

        command.add(ffmpegEXE);

        command.add("-ss");
        command.add("00:00:01");

        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);

        command.add("-vframes");
        command.add("1");
        command.add(outputPath);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getErrorStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader reader1 = new BufferedReader(reader);
            //读取每行
            String line = "";
            while ((reader1.readLine()) != null) {

            }
            if (reader1 != null) {
                reader1.close();
            }if (reader != null) {
                reader.close();
            }if (inputStream != null) {
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        MergeVideoMp3 m = new MergeVideoMp3("D:\\ffmpeg\\bin\\ffmpeg.exe");
        m.converVideoCover("D:\\app\\weixiApp\\fileupload\\180601BH8T1AZA14\\video\\a.mp4",
               "D:\\app\\weixiApp\\fileupload\\bgm\\test.jpg");
    }
}
