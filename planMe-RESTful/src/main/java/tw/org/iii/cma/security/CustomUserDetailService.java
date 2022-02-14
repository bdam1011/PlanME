package tw.org.iii.cma.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.RoleBean;
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private MemberRepository memberRepository;
	
	public CustomUserDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String MBnameOrMBemail) throws UsernameNotFoundException {
		MemberBean member = memberRepository.findByMBnameOrMBemail(MBnameOrMBemail, MBnameOrMBemail)
	               .orElseThrow(() ->
	                       new UsernameNotFoundException("Member not found with MBname or MBemail:" + MBnameOrMBemail));
	        return new org.springframework.security.core.userdetails.User(member.getMBemail(),
	                member.getMBpassword(), mapRolesToAuthorities(member.getRoleBeans()));
	}
	
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<RoleBean> roleBeans){
        return roleBeans.stream().map(roleBean -> new SimpleGrantedAuthority(roleBean.getRolename())).collect(Collectors.toList());
    }

}
