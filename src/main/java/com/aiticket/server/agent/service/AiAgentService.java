package com.aiticket.server.agent.service;

import com.aiticket.server.agent.dto.AiActionConfirmRequest;
import com.aiticket.server.agent.dto.AiChatRequest;
import com.aiticket.server.agent.dto.AiLogQueryRequest;
import com.aiticket.server.agent.vo.AiActionConfirmResponse;
import com.aiticket.server.agent.vo.AiChatResponse;
import com.aiticket.server.agent.vo.AiProjectStatusVO;
import com.aiticket.server.agent.vo.AiRecentLogVO;

public interface AiAgentService {

    AiChatResponse chat(AiChatRequest request);

    AiProjectStatusVO getProjectStatus();

    AiRecentLogVO getRecentLogs(AiLogQueryRequest request);

    AiActionConfirmResponse confirmAction(AiActionConfirmRequest request);
}
