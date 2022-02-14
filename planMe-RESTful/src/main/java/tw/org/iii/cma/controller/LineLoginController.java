package tw.org.iii.cma.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.RoleRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.RoleBean;
import tw.org.iii.cma.payload.JWTAuthResponse;
import tw.org.iii.cma.security.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/auth/login")
public class LineLoginController {

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

	@Value("${line.login.redirect_uri}")
	private String redirect_uri;

	@Value("${line.login.client_id}")
	private String client_id;

	@Value("${line.login.client_secret}")
	private String client_secret;

	@GetMapping(value = "/linesign")
	public ResponseEntity<?> tokenSign(@RequestParam String code) {

		Map<String, String> temp = parseLineToken(code);
		if (!temp.isEmpty()) {
			String name = temp.get("name");
			String email = temp.get("email");
			String picture = temp.get("picture");
			String userid = temp.get("userid");
			if (memberRepository.existsByMBname(name) || memberRepository.existsByMBemail(email)) {

				return ResponseEntity.ok(JWTToken(email, userid));
			}

			// create user object
			MemberBean user = new MemberBean();
			user.setMBname(name);
			user.setMBemail(email);
			user.setMBpassword(passwordEncoder.encode(userid));
			RoleBean roles = roleRepository.findByrolename("USER").get();
			user.setRoleBeans(Collections.singleton(roles));

			memberRepository.save(user);

			return ResponseEntity.ok(JWTToken(email, userid));
		}
		return new ResponseEntity<>("Had not get any data from Line!", HttpStatus.BAD_REQUEST);
	}

	// parseLineToken
	private Map<String, String> parseLineToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "authorization_code");
		map.add("code", code);
		map.add("redirect_uri", redirect_uri);
		map.add("client_id", client_id);
		map.add("client_secret", client_secret);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("https://api.line.me/oauth2/v2.1/token", request,
				String.class);
		String data = response.getBody();
		Map<String, String> result = new HashMap<String, String>();

		if (!data.isEmpty()) {
			JSONObject obj = new JSONObject(data);
			String id_token = (String) obj.get("id_token");
			DecodedJWT jwt = JWT.decode(id_token);

			String userid = jwt.getSubject();
			// Key is the Claim name
			Map<String, Claim> claims = jwt.getClaims();
			String name = claims.get("name").toString().replace("\"", "");
			String email = claims.get("email").toString().replace("\"", "");
			String picture = claims.get("picture").toString().replace("\"", "");
			result.put("userid", userid);
			result.put("name", name);
			result.put("email", email);
			result.put("picture", picture);
		}

		return result;

	}

	// sign in return JWTToken
	private JWTAuthResponse JWTToken(String email, String userid) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, userid));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get token form tokenProvider
		String token = tokenProvider.generateToken(authentication);

		return new JWTAuthResponse(token);

	}
}
