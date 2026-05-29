package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AI Agent 项目状态")
public class AiProjectStatusVO {

    @Schema(description = "前端项目名称", example = "ai-ticket-web")
    private String frontendProject;

    @Schema(description = "后端项目名称", example = "ai-ticket-server")
    private String backendProject;

    @Schema(description = "当前分支，第一阶段为 mock 数据", example = "main")
    private String branch;

    @Schema(description = "运行环境", example = "dev")
    private String env;

    @Schema(description = "是否存在未提交变更，第一阶段为 mock 数据", example = "true")
    private Boolean hasUncommittedChanges;

    @Schema(description = "最近一次提交信息，第一阶段为 mock 数据", example = "feat: optimize dashboard screen")
    private String lastCommit;

    @Schema(description = "前端项目路径", example = "/Users/lei/Desktop/ai-ticket-web")
    private String frontendPath;

    @Schema(description = "后端项目路径", example = "/Users/lei/Desktop/ai-ticket-server")
    private String backendPath;
}
