package com.aiticket.server.agent.tools;

import com.aiticket.server.agent.config.AiAgentProperties;
import com.aiticket.server.agent.vo.AiProjectStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStatusTool {

    private final AiAgentProperties properties;
    private final GitStatusTool gitStatusTool;
    private final Environment environment;

    public AiProjectStatusVO getStatus() {
        AiProjectStatusVO status = new AiProjectStatusVO();
        status.setFrontendProject("ai-ticket-web");
        status.setBackendProject("ai-ticket-server");
        status.setBranch(gitStatusTool.currentBranch());
        status.setEnv(resolveEnv());
        status.setHasUncommittedChanges(gitStatusTool.hasUncommittedChanges());
        status.setLastCommit(gitStatusTool.lastCommit());
        status.setFrontendPath(properties.getFrontendPath());
        status.setBackendPath(properties.getBackendPath());
        return status;
    }

    private String resolveEnv() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            return "dev";
        }
        return String.join(",", activeProfiles);
    }
}
