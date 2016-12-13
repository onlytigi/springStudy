package com.onlytigi.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlytigi.springStudy.dao.SecurityDao;
import com.onlytigi.springStudy.model.User;

/**
 * db로 부터 읽어온 해당 유저 권한 체크 
 */
@Service
public class UserAuthenticationService implements UserDetailsService {
    
	@Autowired
	SecurityDao securityDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = securityDao.selectUserInfo(username);
		if(user == null) throw new UsernameNotFoundException(username);
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		gas.add(new SimpleGrantedAuthority(user.getAuthority()));
		return new UserDetail(user.getId(), user.getPassword(), "JOIN".equalsIgnoreCase(user.getStat()), true, true, true, gas, user.getName(), user.getIdNo());
	}
}
