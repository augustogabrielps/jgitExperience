package com.jgitexperience.JGit.Experience.monitor;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RemoteRepositoryMonitor {

    private final Git git;
    private final Repository repository;

    public RemoteRepositoryMonitor(String repoPath) throws IOException, GitAPIException {
        this.git = Git.open(new File(repoPath));
        this.repository = git.getRepository();
    }

    public void checkForRemoteUpdates() throws GitAPIException, IOException {
        // Fetch changes from the remote repository
        git.fetch().call();

        // Get the latest commit from the remote branch
        ObjectId remoteHead = repository.resolve("refs/remotes/origin/main");
        ObjectId localHead = repository.resolve("refs/heads/main");

        // Check if local and remote heads differ
        if (!localHead.equals(remoteHead)) {
            System.out.println("Remote updates detected, pulling changes...");
            git.pull().call();
        } else {
            System.out.println("Local repository is up to date with remote.");
        }
    }

    public static void main(String[] args) throws IOException, GitAPIException {
        RemoteRepositoryMonitor monitor = new RemoteRepositoryMonitor("/home/as/Dev/JGitExperience/jgitExperience");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    monitor.checkForRemoteUpdates();
                } catch (GitAPIException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        // Check every minute for updates
        timer.schedule(task, 0, 60000);
    }
}

