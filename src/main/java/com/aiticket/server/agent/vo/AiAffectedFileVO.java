package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 执行计划影响文件")
public class AiAffectedFileVO {

    @Schema(description = "文件路径或模块路径", example = "src/views/dashboard/workbench/index.vue")
    private String filePath;

    @Schema(description = "变更类型", allowableValues = {"CHECK", "CREATE", "UPDATE", "DELETE"}, example = "CHECK")
    private String changeType;

    @Schema(description = "影响说明", example = "检查大屏页面组件和图表初始化逻辑")
    private String description;
}
