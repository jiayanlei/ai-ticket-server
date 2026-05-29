package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 结构化执行计划")
public class AiActionPlanVO {

    @Schema(description = "任务类型中文名称", example = "前端报错分析")
    private String taskType;

    @Schema(description = "风险等级", allowableValues = {"LOW", "MEDIUM", "HIGH"}, example = "LOW")
    private String riskLevel;

    @Schema(description = "计划摘要", example = "分析前端报错并给出修复建议")
    private String summary;

    @Schema(description = "影响范围", example = "[\"ai-ticket-web\"]")
    private List<String> scope;

    @Schema(description = "可能涉及的文件")
    private List<AiAffectedFileVO> affectedFiles;

    @Schema(description = "执行步骤")
    private List<String> steps;

    @Schema(description = "是否需要人工确认", example = "false")
    private Boolean needConfirm;
}
