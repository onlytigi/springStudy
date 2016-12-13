package com.onlytigi.springStudy.dao;

import java.util.HashMap;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.onlytigi.springStudy.model.User;

/**
 * 유저 권한 정보를 db로 부터 읽어 올 DAO
 */
@Repository
public class SecurityDao {

    @Qualifier("sqlSession")
    @Autowired
    private SqlSessionTemplate sqlSession;
    
    public static final String MAPPER_NAMESPACE = "mapper.security.dao.";
        
    public User selectUserInfo(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
    	return sqlSession.selectOne(MAPPER_NAMESPACE + "selectUserInfo", param);
    }
}
