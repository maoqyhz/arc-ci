package cn.com.arcsoft.arcci.entity.base;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fururur
 * @create 2019-07-23-10:19
 */
@Data
@NoArgsConstructor
public class RestResult {
    private int code = ResultCode.SUCCESS.getCode();
    private String message = ResultCode.SUCCESS.getMsg();
    private Object data;

    public RestResult(Object data) {
        this.data = data;
    }

    public RestResult(int code, Object data) {
        this.code = code;
        this.message = ResultCode.toMsg(code);
        this.data = data;
    }
}
