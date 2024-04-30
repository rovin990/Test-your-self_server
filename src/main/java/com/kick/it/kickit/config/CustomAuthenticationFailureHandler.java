package com.kick.it.kickit.config;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomAuthenticationFailureHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex){
        ProblemDetail errorDetail=null;

        if( ex instanceof BadCredentialsException){
            errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Authentication failure");
        }
        if( ex instanceof AccessDeniedException){
            errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Don't have permission");
        }

        if(ex instanceof SignatureException){
            errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","Signature is not correct");
        }



        return errorDetail;

    }

}


//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void onAuthenticationFailure(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException exception)
//            throws IOException, ServletException {
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        Map<String, Object> data = new HashMap<>();
//        data.put(
//                "timestamp",
//                Calendar.getInstance().getTime());
//        data.put(
//                "exception",
//                exception.getMessage());
//
//        response.getOutputStream()
//                .println(objectMapper.writeValueAsString(data));
//    }
