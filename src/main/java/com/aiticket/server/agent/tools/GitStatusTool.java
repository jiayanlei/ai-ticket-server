package com.aiticket.server.agent.tools;

import org.springframework.stereotype.Component;

@Component
public class GitStatusTool {

    public String currentBranch() {
        return "main";
    }

    public Boolean hasUncommittedChanges() {
        return true;
    }

    public String lastCommit() {
        return "feat: optimize dashboard screen";
    }
}
