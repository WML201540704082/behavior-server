-- 为llq_app_list表添加dept_id字段
ALTER TABLE llq_app_list ADD COLUMN dept_id VARCHAR(32) DEFAULT NULL COMMENT '部门id' AFTER icon;

-- 创建索引以优化查询性能
CREATE INDEX idx_dept_id ON llq_app_list(dept_id);

-- 查询语句：连接idevelop_dept表获取deptName和fullName
SELECT 
    b.id,
    b.url,
    b.app_name AS appName,
    b.icon,
    b.dept_id,
    d.dept_name AS deptName,
    d.full_name AS fullName,
    b.create_time,
    b.update_time,
    b.create_user,
    b.update_user
FROM llq_app_list b
LEFT JOIN idevelop_dept d ON b.dept_id = d.id AND d.is_deleted = 0
WHERE b.is_deleted = 0;

-- 如果需要创建视图（可选）
CREATE VIEW v_llq_app_list_dept AS
SELECT 
    b.id,
    b.url,
    b.app_name AS appName,
    b.icon,
    b.dept_id,
    d.dept_name AS deptName,
    d.full_name AS fullName,
    b.create_time,
    b.update_time,
    b.create_user,
    b.update_user
FROM llq_app_list b
LEFT JOIN idevelop_dept d ON b.dept_id = d.id AND d.is_deleted = 0
WHERE b.is_deleted = 0;
