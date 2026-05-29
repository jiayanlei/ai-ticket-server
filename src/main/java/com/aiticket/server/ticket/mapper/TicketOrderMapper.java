package com.aiticket.server.ticket.mapper;

import com.aiticket.server.ticket.dto.TicketQueryRequest;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TicketOrderMapper extends BaseMapper<TicketOrder> {

    @Select("""
            <script>
            select *
            from ticket_order
            where deleted = 1
            <if test="query.keyword != null and query.keyword != ''">
              and (title ilike concat('%', #{query.keyword}, '%') or ticket_no ilike concat('%', #{query.keyword}, '%'))
            </if>
            <if test="query.status != null and query.status != ''">
              and status = #{query.status}
            </if>
            <if test="query.priority != null and query.priority != ''">
              and priority = #{query.priority}
            </if>
            <if test="query.category != null and query.category != ''">
              and category = #{query.category}
            </if>
            <if test="query.handlerId != null">
              and handler_id = #{query.handlerId}
            </if>
            <if test="query.handlerId == null and query.assigneeId != null">
              and handler_id = #{query.assigneeId}
            </if>
            <if test="query.applicantId != null">
              and applicant_id = #{query.applicantId}
            </if>
            order by update_time desc
            </script>
            """)
    IPage<TicketOrder> selectRecyclePage(Page<TicketOrder> page, @Param("query") TicketQueryRequest query);

    @Update("update ticket_order set deleted = 0, update_time = now() where id = #{id} and deleted = 1")
    int restoreById(@Param("id") Long id);
}
