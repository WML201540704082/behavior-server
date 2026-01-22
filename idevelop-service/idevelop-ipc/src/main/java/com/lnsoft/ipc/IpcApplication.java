package com.lnsoft.ipc;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;
import com.lnsoft.core.launch.constant.AppConstant;
import org.springframework.boot.SpringApplication;

@IdevelopCloudApplication
public class IpcApplication {
    public static void main(String[] args) {
		IdevelopApplication.run("idevelop-ipc", IpcApplication.class, args);
    }
}
