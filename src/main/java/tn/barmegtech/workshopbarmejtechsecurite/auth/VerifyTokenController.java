package tn.barmegtech.workshopbarmejtechsecurite.auth;

import java.io.UnsupportedEncodingException;
import static tn.barmegtech.workshopbarmejtechsecurite.auth.UserService.applicationUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response;
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.VerificationToken;
import tn.barmegtech.workshopbarmejtechsecurite.listner.RegistrationCompleteEventListener;




@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verify")
public class VerifyTokenController{

    private final VerificationTokenService tokenService;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping("/email")
    public ResponseEntity<Response> verifyEmail(String token) {
        return tokenService.verifyEmail(token);
    }

    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(
            @RequestParam("token") String oldToken,
            final HttpServletRequest request
    ) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = tokenService.generateNewVerificationToken(oldToken);
        resendVerificationTokenEmail(applicationUrl(request), verificationToken);
        return "A new verification link has been sent to your email, check to activate your verification";
    }

    private void resendVerificationTokenEmail(
            String applicationUrl,
            VerificationToken verificationToken
    ) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/verify/email?token="+verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
    }
}
