package com.aiticket.server.agent.tools;

import com.aiticket.server.agent.vo.AiRecentLogVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class ProjectLogReadTool {

    public AiRecentLogVO recentLogs(String type) {
        String normalizedType = normalizeType(type);
        if ("frontend".equals(normalizedType)) {
            return new AiRecentLogVO(normalizedType, List.of(
                    "2026-05-13 10:20:11 INFO  Vite dev server started",
                    "2026-05-13 10:21:03 WARN  Failed to resolve component: WorkbenchChart",
                    "2026-05-13 10:21:18 ERROR Cannot read properties of undefined (reading 'resize')"
            ));
        }
        return new AiRecentLogVO(normalizedType, List.of(
                "2026-05-13 10:21:01 INFO  Application started",
                "2026-05-13 10:22:14 ERROR relation sys_user does not exist",
                "2026-05-13 10:22:15 WARN  SQL execution failed"
        ));
    }

    private String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            return "backend";
        }
        String normalizedType = type.trim().toLowerCase(Locale.ROOT);
        if (!"frontend".equals(normalizedType) && !"backend".equals(normalizedType)) {
            return "backend";
        }
        return normalizedType;
    }
}
