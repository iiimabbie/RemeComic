package tw.com.remecomic.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUtil {
	private Integer totalPages;
	private Long totalElements;
	private Integer currentPage;


}
