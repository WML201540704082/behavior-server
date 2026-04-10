package com.lnsoft.ipc;

import com.lnsoft.core.cloud.client.IdevelopCloudApplication;
import com.lnsoft.core.launch.IdevelopApplication;

@IdevelopCloudApplication
public class PluginApplication {
    public static void main(String[] args) {
		IdevelopApplication.run("idevelop-plugin", PluginApplication.class, args);
    }
}
