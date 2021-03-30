package com.hss.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 世界
 */
@Data
public class World {
    /** 编号 */
    private String keyNo;
    /** 处理状态 */
    private Integer status;
    /** 通知标识 */
    private Integer submitFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date actionTime;
}
