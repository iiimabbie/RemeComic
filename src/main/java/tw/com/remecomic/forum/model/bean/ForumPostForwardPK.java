package tw.com.remecomic.forum.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPostForwardPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Integer forwardedPost;
	
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges","following","followers","posts","haveLikedPosts"})
	private Integer forwardFromUser;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumPostForwardPK other = (ForumPostForwardPK) obj;
		return Objects.equals(forwardFromUser, other.forwardFromUser)
				&& Objects.equals(forwardedPost, other.forwardedPost);
	}

	@Override
	public int hashCode() {
		return Objects.hash(forwardFromUser, forwardedPost);
	}

	
	
}
