package com.company;

import java.io.*;



public class Main {

    public static void runProcess(Process process, StringBuilder builder) throws IOException {
        BufferedReader pReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = pReader.readLine()) != null) {
            builder.append(line);
        }
        System.out.println(builder);
    }

    public static StringBuilder getAllCommits(String command) throws IOException {
        StringBuilder commits = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command);
        runProcess(process, commits);
        return commits;
    }

    public static StringBuilder getCommit(String command, String hashCode) throws IOException {
        StringBuilder commit = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command + " " + hashCode);
        runProcess(process, commit);
        return commit;
    }

    public static String[] getCommitsHash(StringBuilder commits) {
        commits.replace(0,1," ");
        commits.replace(commits.length()-1,commits.length()," ");
        for (int i = 0; i < commits.length(); i++) {
            if (commits.charAt(i) == '"' && commits.charAt(i + 1) == '"') {
                commits.replace(i, i + 2, " ");
            }
        }

        String newArray=new String(commits);
        String[] splitedCommits=newArray.split(" ");

        for (int i = 0; i < splitedCommits.length; i++) {
            System.out.println(splitedCommits[i]+" ");
        }
           return splitedCommits;
    }

    //        git rev-list 9b18c99 ^9e26fd4^ ^9b18c99^
    public static void chooseSomeCommits(String from,String to) throws IOException{
        StringBuilder commit = new StringBuilder();
        Process process = Runtime.getRuntime().exec("git rev-list "+ from+"^ ^"+to+ "^ ");
        runProcess(process,commit);
    }


    public static void creatPatch(String[] commits,String fileName) throws IOException {
        StringBuilder commit = new StringBuilder();
        Process process = Runtime.getRuntime().exec("git diff "+ commits[0]+" "+ commits[1]+
                "> "+ fileName+".patch");
        runProcess(process, commit);
    }

    public static void applyPatch(String filename) throws IOException {
        StringBuilder commit = new StringBuilder();
        Process process = Runtime.getRuntime().exec("git apply "+filename+ ".patch");
        runProcess(process, commit);
    }



    public static void main(String[] args) throws IOException {

        System.out.println("Git log: ");
        getAllCommits("git log");
        System.out.println("Get commit by hash");
        System.out.println(getCommit("git show ","9e26fd451b3a8800a91d8147f9384a7a7c9f6ac6"));

        System.out.println("Format: ");
        getAllCommits("git log --pretty=format:\"%h\"");


        System.out.println("Get commits hash: ");
        System.out.println( getCommitsHash(getAllCommits("git log --pretty=format:\"%h\"")));

        System.out.println("Choose some commits: ");
        chooseSomeCommits("9b18c99","9e26fd4");

        System.out.println("Create and apply: ");
        creatPatch(getCommitsHash(getAllCommits("git log --pretty=format:\"%h\"")), "myfile");
        applyPatch("myFile");


    }
}


