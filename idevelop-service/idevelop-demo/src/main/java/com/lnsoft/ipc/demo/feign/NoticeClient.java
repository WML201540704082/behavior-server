
package com.lnsoft.ipc.demo.feign;

import com.lnsoft.ipc.demo.entity.Notice;
import com.lnsoft.ipc.demo.mapper.NoticeMapper;
import lombok.AllArgsConstructor;
import com.lnsoft.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Notice Feign
 *
 * @author guozhao
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	private NoticeMapper mapper;

	@Override
	@GetMapping(TOP)
	public R<List<Notice>> top(Integer number) {
		return R.data(mapper.topList(number));
	}

}
