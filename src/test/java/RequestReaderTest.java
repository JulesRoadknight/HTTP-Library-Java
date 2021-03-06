import HTTPServer.RequestReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestReaderTest {
    private String headRequest = "GET /head_request HTTP/1.1";
    private String optionsRequest = "OPTIONS /method_options2 HTTP/1.1\n";
    private String postmanInput = "POST /pokemon/id/25 HTTP/1.1\r\n" +
            "Content-Type: application/json\r\n" +
            "User-Agent: PostmanRuntime/7.28.0\r\n" +
            "Accept: */*\r\n" +
            "Postman-Token: numb3rs-w0rdsn-da5he5\r\n" +
            "Host: localhost:5000\r\n" +
            "Accept-Encoding: gzip, deflate, br\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 39\r\n" +
            "\r\n\r\n" +
            "{\r\n" +
            "    \"id\": 25,\r\n" +
            "    \"name\": \"Pikachu\"\r\n" +
            "}";
    private String exampleInput = "POST /test HTTP/1.1\r\n" +
            "Host: thing.example\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Content-Length: 27\r\n" +
            "\r\n\r\n" +
            "field1=value1&field2=value2";

    @Test
    public void returnsParamsGET() {
        String parameters = headRequest;
        assertEquals("GET", RequestReader.getRequestMethod(parameters));
    }

    @Test
    public void returnsParamsOPTIONS() {
        String parameters = optionsRequest;
        assertEquals("OPTIONS", RequestReader.getRequestMethod(parameters));
    }

    @Test
    public void returnsParamsAddress() {
        String parameters = optionsRequest;
        assertEquals("/method_options2", RequestReader.getRequestAddress(parameters));
    }

    @Test
    public void returnsParams() {
        String expectedResponse = "POST /test HTTP/1.1";
        assertEquals(expectedResponse, RequestReader.getRequestParams(exampleInput));
    }

    @Test
    public void readsSingleLineBody() {
        String expectedResponse = "field1=value1&field2=value2";
        assertEquals(expectedResponse, RequestReader.getBody(exampleInput));
    }

    @Test
    public void getsAddressFromFullRequest() {
        String expectedResponse = "/test";
        assertEquals(expectedResponse, RequestReader.getRequestAddress(exampleInput));
    }

    @Test
    public void readsMultilineJSON() {
        String expectedResponse = "{\r\n" +
                "    \"id\": 25,\r\n" +
                "    \"name\": \"Pikachu\"\r\n" +
                "}";
        assertEquals(expectedResponse, RequestReader.getBody(postmanInput));
    }

    @Test
    public void getsPostmanAddress() {
        String expectedResponse = "/pokemon/id/25";
        assertEquals(expectedResponse, RequestReader.getRequestAddress(postmanInput));
    }
}
