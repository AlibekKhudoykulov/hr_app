package ecma.ai.hrapp.payload;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {

    private String message;
    private boolean success;
    private Object object;
    private List<Object> objects;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public ApiResponse(String message, boolean success, List<Object> objects) {
        this.message = message;
        this.success = success;
        this.objects = objects;
    }
}
