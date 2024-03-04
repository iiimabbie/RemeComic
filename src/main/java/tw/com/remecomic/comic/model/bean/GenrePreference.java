package tw.com.remecomic.comic.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenrePreference {
	private Integer userId;
	private Double romance;
	private Double action;
	private Double comedy;
	private Double thriller;
	

}
