package tw.com.remecomic.userA.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.userA.model.bean.UserA;

@Repository
@Component
public interface UserADao extends JpaRepository<UserA, Integer> {

	List<UserA> findUserByNameLike(String name);

	Optional<UserA> findById(Integer userId);

	UserA findByEmail(String email);

	@Query("SELECT userId FROM UserA")
	List<Integer> findAllUserId();

//	@Query(value = "SELECT u.name,u.gender, u.birthDate, u.registerDate,u.userPhoto,u.email,u.statusId,s.statusName, "
//			+ "b.badgeId,b.badgeName " + "FROM UserA u JOIN UserAStatus s on u.statusId= s.statusId "
//			+ "JOIN UserABadgeUser ubu ON u.userId=ubu.userId "
//			+ "JOIN UserABadge b ON ubu.badgeId=b.badgeId ", nativeQuery = true)
//	List<Map<String, Object>> findAllSimple();

	@Query("SELECT userAtt FROM UserA WHERE userId=:userId")
	Integer findUserAttDays(Integer userId);
}
