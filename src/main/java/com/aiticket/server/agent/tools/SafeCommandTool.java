package com.aiticket.server.agent.tools;

import com.aiticket.server.agent.config.AiAgentProperties;
import com.aiticket.server.agent.dto.AiActionConfirmRequest;
import com.aiticket.server.agent.vo.AiActionConfirmResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SafeCommandTool {

    private final AiAgentProperties properties;

    public AiActionConfirmResponse confirmOnly(AiActionConfirmRequest request) {
        log.info("AI Agent 安全边界: enableCommandExec={}, enableFileWrite={}, enableRealDeploy={}, actionType={}, confirm={}",
                properties.getEnableCommandExec(),
                properties.getEnableFileWrite(),
                properties.getEnableRealDeploy(),
                request.getActionType(),
                request.getConfirm());
        String message = Boolean.TRUE.equals(request.getConfirm())
                ? "操作已确认，第一阶段仅记录确认结果，暂不执行真实命令。"
                : "操作已取消，第一阶段仅记录确认结果，暂不执行真实命令。";
        return new AiActionConfirmResponse(true, message);
    }
}
