package com.aiticket.server.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "分页查询参数")
public class PageQuery {

    @Min(value = 1, message = "页码最小为1")
    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 500, message = "每页条数最大为500")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
