package tw.com.remecomic.orgcomic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgComicCommentPK implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
		
		@JsonBackReference(value="comic-orgcomic")
		private Integer comicId;
		
		@JsonBackReference(value="user-userA")
		private Integer userId;


		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			OrgComicCommentPK pk = (OrgComicCommentPK) o;
			 return Objects.equals(comicId, pk.comicId) &&
		                Objects.equals(userId, pk.userId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(comicId, userId);
		}

	

	}

