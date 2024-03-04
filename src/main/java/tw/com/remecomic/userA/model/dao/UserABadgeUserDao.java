package tw.com.remecomic.userA.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tw.com.remecomic.userA.model.bean.UserABadgeUser;

@Repository
@Component
public interface UserABadgeUserDao extends JpaRepository<UserABadgeUser, Integer> {

	@Query("FROM UserABadgeUser WHERE user.userId=:userId")
	public List<UserABadgeUser> findUser(@Param("userId") Integer userId);

	@Transactional
	@Modifying
	@Query("DELETE UserABadgeUser WHERE user.userId=:userId")
	public void deleteBadge(@Param("userId") Integer userId);
}
