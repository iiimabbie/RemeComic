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
public class ForumPostLikePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Integer likedPost;
	
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges","following","followers","posts","haveLikedPosts"})
	@JsonIgnore
	private Integer likeFromUser;

	@Override
	public int hashCode() {
		return Objects.hash(likeFromUser, likedPost);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumPostLikePK other = (ForumPostLikePK) obj;
		return Objects.equals(likeFromUser, other.likeFromUser) && Objects.equals(likedPost, other.likedPost);
	}
	
}
