package cn.mchina.robot.common;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class SimiResponse {
    private String response;
    private int id;
    private int result;
    private String message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
