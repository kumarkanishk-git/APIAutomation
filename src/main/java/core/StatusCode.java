package core;

public enum StatusCode {

    SUCCESS(200, "The request was successful. The response contains the requested data."),

    CREATED(201, "The request was successful, and a new resource was created as a result."),

    BAD_REQUEST(400, "The server couldn't understand the request due to invalid syntax or malformed data."),

    UNAUTHORIZED(401, "Authentication is required and has failed or hasn't been provided."),

    NOT_FOUND(404, "The requested resource could not be found on the server."),

    NO_CONTENT(204, "The request was successful, but there's no content to return."),

    INTERNAL_SERVER_ERROR(500, "A generic error occurred on the server. The server encountered an unexpected condition that prevented it from fulfilling the request.");


    public final int code;
    public final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
