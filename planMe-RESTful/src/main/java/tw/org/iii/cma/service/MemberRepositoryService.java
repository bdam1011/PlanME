package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.RoleRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.RoleBean;
import tw.org.iii.cma.domain.WholeTravelBean;


@Service
@Transactional
public class MemberRepositoryService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private RoleRepository roleRepository;
	
	public List<MemberBean> select(MemberBean bean) {
		List<MemberBean> result = null;
		if(bean!=null && bean.getMBid()!=null && !bean.getMBid().equals(0)) {
			Optional<MemberBean> optional = memberRepository.findById(bean.getMBid());
			if(optional.isPresent()) {
				result = new ArrayList<MemberBean>();
				result.add(optional.get());
			}
		} else {
			result = memberRepository.findAll();
		}
		return result;
	}
	
	public List<MemberBean> selectName(String name) {
		List<MemberBean> result = null;
		if(!name.isEmpty()) {
			List<MemberBean> temp = memberRepository.findByMBnameContaining(name);
			if(!temp.isEmpty()) {
				result= temp;
			}
		}
		return result;
	}
	
	public List<MemberBean> selectSelf(String name) {
		List<MemberBean> result = null;
		if(!name.isEmpty()) {
			Optional<MemberBean> optional = memberRepository.findByMBnameOrMBemail(name,name);
			if(optional.isPresent()) {
				result = new ArrayList<MemberBean>();
				result.add(optional.get());
			}
		}
		return result;
	}
	
	// select by page 每頁20筆
	public List<MemberBean> selectPage(Integer page) {
		List<MemberBean> result = null;
		Page<MemberBean> pageResult = null;
		Pageable pageable = PageRequest.of(page, 20, Sort.by("MBid").ascending());
		pageResult = memberRepository.findAll(pageable);
		result = pageResult.getContent();
		return result;
	}
	
	public MemberBean insert(MemberBean bean) {
		MemberBean result = null;
		if(bean!=null && bean.getMBid()==null) {
			return memberRepository.save(bean);
		}
		return result;
				
	}
	
	//update ADMIN<->USER
	public MemberBean newAdmin(Integer MBid, String auth) {
		MemberBean result = null;
		if(MBid!=null) {
			MemberBean user = memberRepository.findByMBid(MBid);
			if(user!=null) {
				RoleBean roles = roleRepository.findByrolename(auth).get();
				user.setRoleBeans(Collections.singleton(roles));
//				return memberRepository.save(user);
				return user;
			}
		}
		return result;
	}
	
	//update password
	public MemberBean update(MemberBean bean) {
		MemberBean result = null;
		if(bean!=null && bean.getMBid()!=null) {
			boolean exist = memberRepository.existsById(bean.getMBid());
			if(exist) {
				bean.setMBpassword(passwordEncoder.encode(bean.getMBpassword()));
				return memberRepository.save(bean);
			}
		}
		return result;
	}
	
	//soft deleted
	public boolean softDelete(MemberBean bean) {
		boolean  result = false;
		if(bean!=null && bean.getMBid()!=null) {
			boolean exist = memberRepository.existsById(bean.getMBid());
			if(exist) {
				Optional<MemberBean> optional = memberRepository.findById(bean.getMBid());
				optional.get().setDeleted(true);
				memberRepository.save(optional.get());
				result = true;
			}
		}
		return result;
	}
	
	//hard delete
	public boolean delete(MemberBean bean) {
		boolean result = false;
		if(bean!=null && bean.getMBid()!=null && bean.getMBname()!=null) {
			boolean existInName = memberRepository.existsByMBidAndMBname(bean.getMBid(),bean.getMBname());
			if(existInName) {
				memberRepository.deleteById(bean.getMBid());
				result = true;
			}
		}
		return result;
	}
}
