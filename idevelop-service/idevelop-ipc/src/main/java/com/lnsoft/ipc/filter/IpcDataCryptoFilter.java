package com.lnsoft.ipc.filter;

import com.antherd.smcrypto.sm3.Sm3;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.core.tool.utils.crypto.sm3.SM3Util;
import com.lnsoft.core.tool.utils.crypto.sm4.SM4Util;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 仅解密请求体中data字段的SM4过滤器（ipc-service专属）
 */
public class  IpcDataCryptoFilter implements Filter {
    // Jackson JSON解析器（用于解析原始请求体）
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 仅处理POST/PUT/PATCH等带请求体的方法
        if (isNeedProcess(httpRequest.getMethod())) {
            // 包装请求：仅解密data字段，重构请求体
            CryptoRequestWrapper cryptoRequest = new CryptoRequestWrapper(httpRequest,response);
            // 包装响应：若需加密响应体，保留原逻辑；若无需加密，直接放行
            CryptoResponseWrapper cryptoResponse = new CryptoResponseWrapper(httpResponse);

            chain.doFilter(cryptoRequest, cryptoResponse);
            // 响应加密（若需）：按原逻辑处理，或删除（根据业务需求）
            writeEncryptedResponse(cryptoResponse, httpResponse);
        } else {
            chain.doFilter(request, response);
        }
    }

    // 判断是否需要处理（仅POST/PUT/PATCH）
    private boolean isNeedProcess(String method) {
        return "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method);
    }

    // ===================== 核心：请求体包装类（仅解密data字段）=====================
    static class CryptoRequestWrapper extends HttpServletRequestWrapper {
        private  String decryptedBody; // 解密data后，重构的完整请求体（明文）

        public CryptoRequestWrapper(HttpServletRequest request,ServletResponse response) throws IOException {
            super(request);


			// 1. 读取客户端原始请求体（JSON字符串，如{"method":"upload","data":"密文"}）
            String originalBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            System.out.println("客户端原始请求体：" + originalBody);

			String nonce = request.getHeader("nonce");
			String fingerprint = request.getHeader("fingerprint");
			String ts = request.getHeader("ts");
			String sign = request.getHeader("sign");
			String ip = SM4Util.decryptCBC(fingerprint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
			String signMy = ip + nonce + ts + originalBody;
			String resign = SM3Util.sm3Hash(signMy);
			if (!resign.equals(sign)) {
				sendErrorResponse(response,HttpServletResponse.SC_UNAUTHORIZED, "请求完整性校验失败");
				return;
			}
			// 2. 解析原始JSON，提取method和data（密文）
            JsonNode originalJson = objectMapper.readTree(originalBody);
            String method = originalJson.get("method").asText(); // 提取method（明文）
            String dataCipher = originalJson.get("data").asText(); // 提取data（SM4加密后的Base64密文）

            // 3. SM4解密data密文，得到明文data对象
            String dataPlain = SM4Util.decryptCBC(dataCipher,"3513416909a1ad8e2785577698badcfe","ad01c2b36421cc77ff120a0dec7b0e0f");
            System.out.println("data解密后明文：" + dataPlain);

            // 4. 重构请求体（method保留，data替换为解密后的明文）
            this.decryptedBody = String.format("{\"method\":\"%s\",\"data\":%s}", method, dataPlain);
            System.out.println("重构后的请求体（业务逻辑接收）：" + decryptedBody);
        }

        // 重写getInputStream：返回重构后的明文请求体
        @Override
        public ServletInputStream getInputStream() throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBody.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bis.read();
                }

                @Override
                public boolean isFinished() {
                    return bis.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {}
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
        }
    }

    // ===================== 响应体加密（按需保留/删除）=====================
    // 若响应体无需加密，可删除此部分，直接放行response
    static class CryptoResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final PrintWriter writer = new PrintWriter(outputStream);

        public CryptoResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }


                @Override
                public boolean isReady() {
                    return true;
                }

				@Override
				public void setWriteListener(WriteListener writeListener) {

				}
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return writer;
        }

        public String getResponseContent() throws UnsupportedEncodingException {
            writer.flush();
            return outputStream.toString(StandardCharsets.UTF_8.name());
        }
    }

    private void writeEncryptedResponse(CryptoResponseWrapper cryptoResponse, HttpServletResponse response)
            throws IOException {
        String plainResponse = cryptoResponse.getResponseContent();
		JsonNode originalJson = objectMapper.readTree(plainResponse);
		String code = originalJson.get("code").asText(); // 提取method（明文）
		JsonNode data = originalJson.get("data");// 提取data（SM4加密后的Base64密文）
		String dataCipher = objectMapper.writeValueAsString(data);
// 若响应体需加密，这里加密；若无需加密，直接返回plainResponse
		String encryptCBC = SM4Util.encryptCBC(dataCipher, "5f8d7e6c4b3a2918070654321fedcba9", "8a7b6c5d4e3f2a1b0c9d8e7f6a5b4c3d");
//        String encryptedResponse = CryptoUtils.encrypt(plainResponse);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
		if (StringUtil.isBlank(encryptCBC)){
			encryptCBC = "{}";
		}
		String result = String.format("{\"code\":%s,\"data\":\"%s\"}", code, encryptCBC);
        try (PrintWriter out = response.getWriter()) {
            out.write(result);
            out.flush();
        }
    }


    @Override
    public void destroy() {}
	/**
	 * 发送错误响应（核心方法）
	 * @param response HttpServletResponse
	 * @param status HTTP状态码（如401未授权、403禁止访问）
	 * @param message 错误提示信息
	 */
	static void sendErrorResponse(ServletResponse response, int status, String message) throws IOException {
		// 设置响应状态码和ContentType
		response.setContentType("application/json;charset=UTF-8");
		// 构建错误响应JSON
		String errorJson = String.format("{\"code\":\"%d\",\"message\":\"%s\"}", status, message);
		// 写入响应体（使用try-with-resources自动关闭流）
		try (PrintWriter out = response.getWriter()) {
			out.write(errorJson);
			out.flush();
		}
	}
}
