package tw.org.iii.cma.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.ApiOperation;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.RoleRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.RoleBean;
import tw.org.iii.cma.payload.JWTAuthResponse;
import tw.org.iii.cma.payload.LoginDTO;
import tw.org.iii.cma.payload.SignUpDTO;
import tw.org.iii.cma.security.JwtTokenProvider;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/auth/login")
public class AuthController {
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

   // sign in
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    // sign up
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){

        // add check for username exists in a DB
        if(memberRepository.existsByMBname(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(memberRepository.existsByMBemail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        MemberBean user = new MemberBean();
//        user.setName(signUpDto.getName());
        user.setMBname(signUpDto.getUsername());
        user.setMBemail(signUpDto.getEmail());
        user.setMBpassword(passwordEncoder.encode(signUpDto.getPassword()));
        RoleBean roles = roleRepository.findByrolename("USER").get();
        user.setRoleBeans(Collections.singleton(roles));

        memberRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
