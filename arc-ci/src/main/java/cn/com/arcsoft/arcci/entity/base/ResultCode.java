package cn.com.arcsoft.arcci.entity.base;

import lombok.Getter;

import java.util.Optional;

/**
 * 错误代码定义
 *
 * @author Fururur
 * @create 2019-07-23-10:24
 */
@Getter
public enum ResultCode {
    SUCCESS(20000,"Success"),
    CREATE_PIPLINE_FAILED(20001,"Create pipline failed!"),
    LOGIN_FAILED(20002,"login failed!");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Optional<ResultCode> getByCode(int code) {
        for (ResultCode ec : ResultCode.values()) {
            if (ec.getCode() == code) {
                return Optional.of(ec);
            }
        }
        return Optional.empty();
    }

    public static String toMsg(int code) {
        Optional<ResultCode> rc = getByCode(code);
        return rc.map(ResultCode::getMsg).orElse(null);
    }
}
