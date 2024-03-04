package tw.com.remecomic.userA.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.userA.model.bean.UserABadge;

@Repository
@Component
public interface UserABadgeDao extends JpaRepository<UserABadge, Integer> {

}
