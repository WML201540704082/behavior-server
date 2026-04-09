-- 修改ipc_business_system表结构
-- 1. 添加app_name字段
ALTER TABLE ipc_business_system ADD COLUMN app_name VARCHAR(255) DEFAULT NULL COMMENT '应用名称' AFTER url;

-- 2. 添加icon字段
ALTER TABLE ipc_business_system ADD COLUMN icon VARCHAR(255) DEFAULT NULL COMMENT '图标' AFTER app_name;

-- 3. 删除business_name字段
ALTER TABLE ipc_business_system DROP COLUMN business_name;

-- 4. 删除domain_name字段
ALTER TABLE ipc_business_system DROP COLUMN domain_name;
