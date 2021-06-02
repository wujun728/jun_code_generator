package demo.jpa.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.slyak.spring.jpa.TemplateQuery;

import demo.jpa.pojo.User;
import klg.common.dataaccess.BaseRepository;

public interface UserDAO extends BaseRepository<User, java.lang.Integer> {
	public List findByCriteria();
	
	public List testEm();
	
	public void testL1Cache();
	
	@TemplateQuery
	public Page<User> tetFindPage(@Param("account") String account ,Pageable pageable);
	
}
