package tw.org.iii.cma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
public class LabbootRestfulApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(LabbootRestfulApplication.class, args);

	}
//	@Autowired
//	private RoleRepository roleRepository  ;

//	@Override
//	public void run(String... args) throws Exception {
//
//		RoleBean adminRole = new RoleBean();
//		adminRole.setRolename("ROLE_ADMIN");
//		roleRepository.save(adminRole);
//		
//		RoleBean userRole = new RoleBean();
//		userRole.setRolename("ROLE_USER");
//		roleRepository.save(userRole);
//
//	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:8081").allowedMethods("GET", "PUT", "POST",
				"DELETE", "PATCH", "OPTIONS");
	}
}
