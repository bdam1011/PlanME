package tw.org.iii.cma.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.RoleRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.RoleBean;
import tw.org.iii.cma.payload.JWTAuthResponse;
import tw.org.iii.cma.payload.SignUpDTO;
import tw.org.iii.cma.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth/login")
public class GoolgleVerifyController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@PostMapping(value = "/googlesignin")
	public ResponseEntity<JWTAuthResponse> tokenSignIn(@RequestParam("token") String idTokenString)
			throws IOException, GeneralSecurityException {

		GoogleIdToken.Payload payload = getPayload(idTokenString, clientId);
		// Get profile information from payload
		if (payload != null) {
			String email = payload.getEmail();
			String id = payload.getSubject();

			// boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, id));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			// get token form tokenProvider
			String token = tokenProvider.generateToken(authentication);

			return ResponseEntity.ok(new JWTAuthResponse(token));
		}
		return ResponseEntity.notFound().build();
	}

	// sign up
	@PostMapping("/googlesignup")
	public ResponseEntity<?> googlesignup(@RequestParam("token") String idTokenString) {

		GoogleIdToken.Payload payload = getPayload(idTokenString, clientId);
		if (payload != null) {
			String email = payload.getEmail();
			String id = payload.getSubject();
			// add check for username exists in a DB
			if (memberRepository.existsByMBname(email)) {
				return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
			}

			// add check for email exists in DB
			if (memberRepository.existsByMBemail(email)) {
				return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
			}
			// create user object
			MemberBean user = new MemberBean();
//	        user.setName(signUpDto.getName());
			user.setMBname(email);
			user.setMBemail(email);
			user.setMBpassword(passwordEncoder.encode(id));
			RoleBean roles = roleRepository.findByrolename("USER").get();
			user.setRoleBeans(Collections.singleton(roles));

			memberRepository.save(user);

			return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}

	public GoogleIdToken.Payload getPayload(String idTokenString, String clientId) {
		NetHttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList((clientId))).build();

		GoogleIdToken idToken = null;
		boolean tokenIsValid = false;
		try {
			idToken = GoogleIdToken.parse(verifier.getJsonFactory(), idTokenString);
			//???
			System.out.println(verifier.verify(idTokenString));
			tokenIsValid = (idToken != null);
		} catch (Exception e) {

			e.toString();
		}
		if (tokenIsValid) {
			GoogleIdToken.Payload payload = idToken.getPayload();
			return payload;
		}
		return null;

	}

}
