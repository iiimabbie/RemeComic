package tw.com.remecomic.forum.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumGroupUsers")
public class ForumGroupUsers {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="groupUserId")
	private Integer groupUserId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser user;
	
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges"})
	@ManyToOne
	@JoinColumn(name="groupId")
	@JsonIgnore
	private ForumGroup group;
	
}
