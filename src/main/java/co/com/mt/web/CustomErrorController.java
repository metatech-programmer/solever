/* package co.com.mt.web;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Obtener el código de estado del error
        int statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Math.log(statusCode);
       
        // Redirigir a la página de error correspondiente
        if (statusCode == HttpStatus.NOT_FOUND_404.getStatusCode()) {
            return "/errores/404";
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode()) {
            return "/errores/500";
        } else if (statusCode == HttpStatus.FORBIDDEN_403.getStatusCode()) {
            return "/errores/403";
        }
        return "error";
    }
 

    public String getErrorPath() {
        return "/error";
    }
}
 */