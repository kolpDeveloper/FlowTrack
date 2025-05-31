package by.kolp.api.model.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class CustomErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    ErrorAttributes errorAttributes;


    @RequestMapping(CustomErrorController.ERROR_PATH)
    public ResponseEntity<ErrorDto> error(HttpServletRequest request, WebRequest webRequest) {


        Map<String, Object> atrributes = errorAttributes.getErrorAttributes(
                webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );


        return ResponseEntity
                .status((Integer) atrributes
                        .get("status"))
                .body(ErrorDto.builder()
                        .error((String) atrributes.get("error"))
                        .errorDescription((String) atrributes.get("message"))
                        .build());
    }
    //todo .status throws nullpointer exception
}
