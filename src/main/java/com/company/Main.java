package com.company;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

    public static void runProcess(Process process,StringBuilder builder) throws IOException {
        BufferedReader pReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while((line=pReader.readLine())!=null){
            builder.append(line);
        }
        String result = builder.toString();
        System.out.print(result);
    }

    public static void getCommits(String command) throws IOException {
        StringBuilder builder = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command);
        runProcess(process,builder);
    }

    public static void getCommitByHash(String command,String hashCode) throws IOException {
        StringBuilder builder = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command+hashCode);

        runProcess(process, builder);
    }



    public static void main(String[] args) throws IOException, GitAPIException, InterruptedException {


        getCommits("git log");
        System.out.println();
        getCommitByHash("git show ","9e26fd451b3a8800a91d8147f9384a7a7c9f6ac6");


    }
}


