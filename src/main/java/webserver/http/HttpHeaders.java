package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.MultiValueMap;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final Logger logger = LoggerFactory.getLogger(HttpHeaders.class);

    public static final String ACCEPT = "Accept";

    public static final String ACCEPT_CHARSET = "Accept-Charset";

    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    public static final String ACCEPT_RANGES = "Accept-Ranges";

    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    public static final String AGE = "Age";

    public static final String ALLOW = "Allow";

    public static final String AUTHORIZATION = "Authorization";

    public static final String CACHE_CONTROL = "Cache-Control";

    public static final String CONNECTION = "Connection";

    public static final String CONTENT_ENCODING = "Content-Encoding";

    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    public static final String CONTENT_LANGUAGE = "Content-Language";

    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String CONTENT_LOCATION = "Content-Location";

    public static final String CONTENT_RANGE = "Content-Range";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String COOKIE = "Cookie";

    public static final String DATE = "Date";

    public static final String ETAG = "ETag";

    public static final String EXPECT = "Expect";

    public static final String EXPIRES = "Expires";

    public static final String FROM = "From";

    public static final String HOST = "Host";

    public static final String IF_MATCH = "If-Match";

    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    public static final String IF_NONE_MATCH = "If-None-Match";

    public static final String IF_RANGE = "If-Range";

    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    public static final String LAST_MODIFIED = "Last-Modified";

    public static final String LINK = "Link";

    public static final String LOCATION = "Location";

    public static final String MAX_FORWARDS = "Max-Forwards";

    public static final String ORIGIN = "Origin";

    public static final String PRAGMA = "Pragma";

    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";

    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

    public static final String RANGE = "Range";

    public static final String REFERER = "Referer";

    public static final String RETRY_AFTER = "Retry-After";

    public static final String SERVER = "Server";

    public static final String SET_COOKIE = "Set-Cookie";

    public static final String SET_COOKIE2 = "Set-Cookie2";

    public static final String TE = "TE";

    public static final String TRAILER = "Trailer";

    public static final String TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String UPGRADE = "Upgrade";

    public static final String USER_AGENT = "User-Agent";

    public static final String VARY = "Vary";

    public static final String VIA = "Via";

    public static final String WARNING = "Warning";

    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    private static final String HEADER_LINE_SPLIT_SIGN = ":";

    private static final int HEADER_LINE_SPLIT_LIMIT = 2;

    private static final Pattern HEADER_LINE_PATTERN = Pattern.compile("^[^:\\s]+:.+");

    private final MultiValueMap<String, String> headers;

    public HttpHeaders(){
        headers = CollectionUtils.toMultiValueMap(new LinkedCaseInsensitiveMap<>(8, Locale.ENGLISH));
    }


    public void addHeaderLine(String headerLine) {
        String[] headerNameAndValue = parseHeaderLine(headerLine);
        setHeader(this.headers, headerNameAndValue[0], headerNameAndValue[1]);
    }

    public List<String> getHeaderValue(String name) {
        return get(name);
    }

    public String getHeaderValueFirst(String name) {
        return getFirst(name);
    }

    public String getContentType() {
        return getFirst(CONTENT_TYPE);
    }

    public long getContentLength() {
        String value = getFirst(CONTENT_LENGTH);
        return (value != null ? Long.parseLong(value) : -1);
    }

    public void setHeader(String name, String value) {
        setHeader(this.headers, name, value);
    }

    public List<String> getHeaderLines() {
        return this.headers.entrySet().stream()
                .flatMap(entry -> {
                    String name = entry.getKey();
                    return entry.getValue().stream()
                            .map(value -> this.getHeaderLine(name, value));
                })
                .collect(Collectors.toList());

    }

    private String getHeaderLine(String name, String value) {
        return String.join(HEADER_LINE_SPLIT_SIGN, name, value);
    }

    private void setHeader(MultiValueMap<String, String> headerValueByName, String name, String value) {

        if(StringUtils.isEmpty(value)) {
            return;
        }

        headerValueByName.computeIfAbsent(name, (key) -> new ArrayList<>())
                .add(value.trim());
    }

    private String[] parseHeaderLine(String headerLine) {

        if(!isValidHeaderLine(headerLine)) {
            throw new IllegalArgumentException("headerLine 이상함.");
        }

        return splitHeaderLine(headerLine);
    }

    private String[] splitHeaderLine (String headerLine) {
        return headerLine.split(HEADER_LINE_SPLIT_SIGN, HEADER_LINE_SPLIT_LIMIT);
    }

    private boolean isValidHeaderLine(String headerLine) {

        if(StringUtils.isEmpty(headerLine)) {
            return false;
        }

        if(!HEADER_LINE_PATTERN.matcher(headerLine).find()) {
            return false;
        }

        return true;
    }

    private List<String> get(String name){
        return this.headers.get(name);
    }

    private String getFirst(String name){
        return this.headers.getFirst(name);
    }



}
