CREATE DATABASE IF NOT EXISTS book_catalog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE book_catalog;

-- 分类表
CREATE TABLE IF NOT EXISTS category (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name        VARCHAR(100) NOT NULL COMMENT '分类名称',
    description VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    sort_order  INT          DEFAULT 0 COMMENT '排序号',
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书分类表';

-- 图书表
CREATE TABLE IF NOT EXISTS book (
    id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    title         VARCHAR(200)  NOT NULL COMMENT '书名',
    author        VARCHAR(100)  DEFAULT NULL COMMENT '作者',
    isbn          VARCHAR(20)   DEFAULT NULL COMMENT 'ISBN',
    publisher     VARCHAR(100)  DEFAULT NULL COMMENT '出版社',
    publish_date  DATE          DEFAULT NULL COMMENT '出版日期',
    price         DECIMAL(10,2) DEFAULT NULL COMMENT '价格',
    stock         INT           DEFAULT 0 COMMENT '库存',
    description   TEXT          DEFAULT NULL COMMENT '简介',
    category_id   BIGINT        NOT NULL COMMENT '分类ID',
    created_at    DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted       TINYINT       DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
    PRIMARY KEY (id),
    KEY idx_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书表';

-- 示例数据
INSERT INTO category (name, description, sort_order) VALUES
('文学小说', '包括中外文学名著、小说等', 1),
('科技计算机', '计算机技术、编程、人工智能等', 2),
('历史人文', '历史、哲学、社会科学等', 3),
('经济管理', '经济学、管理学、商业等', 4),
('教育学习', '教材、教辅、语言学习等', 5);

INSERT INTO book (title, author, isbn, publisher, publish_date, price, stock, description, category_id) VALUES
('百年孤独', '加西亚·马尔克斯', '978-7-5442-4528-0', '南海出版公司', '2011-06-01', 39.50, 100, '魔幻现实主义文学代表作', 1),
('活着', '余华', '978-7-5063-6511-8', '作家出版社', '2012-08-01', 20.00, 200, '一个人一生的故事', 1),
('Java核心技术', 'Cay S. Horstmann', '978-7-111-54742-6', '机械工业出版社', '2016-09-01', 119.00, 50, 'Java编程经典入门书', 2),
('算法导论', 'Thomas H. Cormen 等', '978-7-111-40701-0', '机械工业出版社', '2012-12-01', 128.00, 30, '计算机科学经典教材', 2),
('人类简史', '尤瓦尔·赫拉利', '978-7-5086-4478-7', '中信出版社', '2014-11-01', 68.00, 80, '从动物到上帝的演化史', 3),
('万历十五年', '黄仁宇', '978-7-108-00982-1', '生活·读书·新知三联书店', '1997-05-01', 18.00, 120, '明史研究经典著作', 3);
