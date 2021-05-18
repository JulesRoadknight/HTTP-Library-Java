package HTTPServer;

public class RequestReader {
    public static String getRequestParams(String request) {
        return request.split("\r\n")[0];
    }

    public static String getRequestMethod(String parameters) {
        return parameters.split(" ")[0];
    }

    public static String getRequestAddress(String parameters) {
        return parameters.split(" ")[1];
    }

    public static String getBody(String request) {
        StringBuilder body = new StringBuilder();
        String[] inputLines;
        if (request.contains("Content-Length")) {
            inputLines = request.split("\r\n\r\n");
            body.append(inputLines[inputLines.length - 1].trim());
        }
        return body.toString();
    }
}