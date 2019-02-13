package com.h.jamil.api.framework.utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ELKLogger implements Logger {

    private static final ELKLogger log = new ELKLogger(ELKLogger.class);

    private Logger logger;
    private Marker markerJSON;
    private Marker markerAudit;
    private Marker markerTrace;

    public ELKLogger(Class callerClass) {
        logger  =  LoggerFactory.getLogger(callerClass);
        markerJSON = MarkerFactory.getMarker("_ELK_JSON");

        //Audit marker / Automatically append to JSON Marker
        markerAudit = MarkerFactory.getMarker("AUDIT_LOG");
        markerAudit.add(markerJSON);

        //Trace marker / Automatically append to JSON Marker
        markerTrace = MarkerFactory.getMarker("APP_TRACE");
        markerTrace.add(markerJSON);

    }

    private String formatMessage(String message)
    {
        //This is generate hook for processing log message
        return message;
    }


    public static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
            } catch (IOException e) {
                log.error("Error caught on extractPostRequestBody", e);
            }
        }
        return "";
    }


    public void audit(String message)
    {
        audit(null, message);
    }

    public void audit(Marker rootMarker, String message)
    {
        //Add JSON tag (Marker) , If there is no marker passed use markerJSON as root
        Marker newRootMarker = rootMarker;
        if(newRootMarker == null)
            newRootMarker = markerAudit;
        else
            newRootMarker.add(markerAudit);


        ELKMessage msgObj = new ELKMessage(message);
        msgObj.fillHttpContext();

        logger.info(newRootMarker,  msgObj.toString());

    }


    public void app_trace(String message)
    {
        app_trace(null, message);
    }

    public void app_trace( Marker rootMarker, String message)
    {
        //Add JSON tag (Marker) , If there is no marker passed use markerJSON as root
        Marker newRootMarker = rootMarker;
        if(newRootMarker == null)
            newRootMarker = markerTrace;
        else
            newRootMarker.add(markerTrace);


        ELKMessage msgObj = new ELKMessage(message);
        msgObj.fillHttpContext();

        logger.info(newRootMarker,  msgObj.toString());
    }


    /////////////////////////////////////////////////////////
    //Implementation of default logger interface start below
    /////////////////////////////////////////////////////////

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(formatMessage(s));
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(formatMessage(s), o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(formatMessage(s), o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(formatMessage(s), objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(formatMessage(s), throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String s) {
        logger.trace(marker, formatMessage(s));
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        logger.trace(marker, formatMessage(s), o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        logger.trace(marker, formatMessage(s), o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        logger.trace(marker, formatMessage(s), objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        logger.trace(marker, formatMessage(s), throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(formatMessage(s));
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(formatMessage(s), o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(formatMessage(s), o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(formatMessage(s), objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(formatMessage(s), throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        logger.debug(marker, formatMessage(s));
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        logger.debug(marker, formatMessage(s), o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, formatMessage(s), o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        logger.debug(marker, formatMessage(s), objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, formatMessage(s), throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(formatMessage(s));
    }

    @Override
    public void info(String s, Object o) {
        logger.info(formatMessage(s), o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.info(formatMessage(s), o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.info(formatMessage(s), objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(formatMessage(s), throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        logger.info(marker, formatMessage(s));
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        logger.info(marker, formatMessage(s), o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        logger.info(marker, formatMessage(s), o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        logger.info(marker, formatMessage(s), objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        logger.info(marker, formatMessage(s), throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(formatMessage(s));
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(formatMessage(s), o);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(formatMessage(s), objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(formatMessage(s), o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(formatMessage(s), throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String s) {
        logger.warn(marker, formatMessage(s));
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        logger.warn(marker, formatMessage(s), o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        logger.warn(marker, formatMessage(s), o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        logger.warn(marker, formatMessage(s), objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        logger.warn(marker, formatMessage(s), throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(formatMessage(s));
    }

    @Override
    public void error(String s, Object o) {
        logger.error(formatMessage(s), o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(formatMessage(s), o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(formatMessage(s), objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(formatMessage(s), throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        logger.error(marker, formatMessage(s));
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        logger.error(marker, formatMessage(s), o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        logger.error(marker, formatMessage(s), o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        logger.error(marker, formatMessage(s), objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        logger.error(marker, formatMessage(s), throwable);
    }

    private class ELKMessage
    {
        public String requestURI;
        public String requestMethod;
        public String requestQueryString;
        public Map<String, String> requestHeader;
        public String message;

        public ELKMessage(String message)
        {
            this.message = message;
        }

        public void fillHttpContext()
        {
            //Check for request context
            ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            if(requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();

                //Method
                requestMethod = request.getMethod();


                //Path
                requestURI = request.getRequestURI();


                //Query String
                requestQueryString = request.getQueryString();

                //Post body
                //  logger.info("PostBody: " + extractPostRequestBody(request));

                //Header
                requestHeader = new HashMap<String, String>();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = request.getHeader(headerName);
                        requestHeader.put(headerName, headerValue);
                    }
                }

            }
        }

        
        public String toString() {

            try {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                return ow.writeValueAsString(this);
            }catch (Exception ex)
            {
                return "";
            }
        }
    }

}
