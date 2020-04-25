package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 生成有参构造方法
@NoArgsConstructor // 生成无参构造方法
public class GuliException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

    @Override
    public String toString() {
        return "GuliException{" +
        "message=" + this.getMsg() +
        ", code=" + this.getCode() +
        '}';
    }

}