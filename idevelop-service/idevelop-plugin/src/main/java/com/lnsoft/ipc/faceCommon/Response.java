package com.lnsoft.ipc.faceCommon;

import com.lnsoft.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
	private static final long serialVersionUID = 1L;
	private int code;
	private T data;

	public static <T> Response<T> data(T data) {
		return new Response(200, data);
	}
	public static <T> Response<T> code(int code) {
		return new Response(code, null);
	}
}
