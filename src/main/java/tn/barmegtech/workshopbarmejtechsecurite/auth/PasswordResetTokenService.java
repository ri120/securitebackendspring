package tn.barmegtech.workshopbarmejtechsecurite.auth;


import org.springframework.http.ResponseEntity;

import tn.barmegtech.workshopbarmejtechsecurite.Email.ChangePasswordResetRequest;








public interface PasswordResetTokenService {
   ResponseEntity<String> verifyEmail(String email);


   ResponseEntity<String> verifyOtp(Integer otp, String email);


   ResponseEntity<String> changePasswordHandler(
           ChangePasswordResetRequest changePasswordResetRequest,
           String email
   );


}

