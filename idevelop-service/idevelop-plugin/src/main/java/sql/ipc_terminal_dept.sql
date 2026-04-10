-- 为llq_terminal表添加dept_id字段
ALTER TABLE llq_terminal ADD COLUMN dept_id VARCHAR(32) DEFAULT NULL COMMENT '部门id' AFTER gateway;

-- 创建索引以优化查询性能
CREATE INDEX idx_dept_id ON llq_terminal(dept_id);

-- 查询语句：连接idevelop_dept表获取deptName和fullName
SELECT 
    t.id,
    t.ip,
    t.mac,
    t.gateway,
    t.dept_id,
    d.dept_name AS deptName,
    d.full_name AS fullName,
    t.create_time,
    t.update_time,
    t.create_user,
    t.update_user
FROM llq_terminal t
LEFT JOIN idevelop_dept d ON t.dept_id = d.id;

-- 如果需要创建视图（可选）
CREATE VIEW v_llq_terminal_dept AS
SELECT 
    t.id,
    t.ip,
    t.mac,
    t.gateway,
    t.dept_id,
    d.dept_name AS deptName,
    d.full_name AS fullName,
    t.create_time,
    t.update_time,
    t.create_user,
    t.update_user
FROM llq_terminal t
LEFT JOIN idevelop_dept d ON t.dept_id = d.id;
