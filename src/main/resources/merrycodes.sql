use merrycodes;

# 文章
CREATE TABLE article
(
    id              INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '文章id',
    title           VARCHAR(255)        NOT NULL COMMENT '文章标题',
    tags            VARCHAR(255)        NOT NULL COMMENT '文章标签',
    category        VARCHAR(255)        NOT NULL COMMENT '文章分类',
    html_content    MEDIUMTEXT          NOT NULL COMMENT '文章内容-HTML格式',
    md_content      MEDIUMTEXT          NOT NULL COMMENT '文章内容-Markdown格式',
    summary_content MEDIUMTEXT          NOT NULL COMMENT '文章概括内容-HTML格式',
    browse          INTEGER             NOT NULL DEFAULT 0 COMMENT '文章浏览量',
    status          INTEGER             NOT NULL DEFAULT 1 COMMENT '0表示草稿，1表示已发布',
    create_time     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章创建时间，默认为当前时间',
    update_time     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '文章更新时间，默认为当前时间',
    UNIQUE (title)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

# 标签
create table tags
(
    id          INTEGER PRIMARY KEY NOT NULL Auto_Increment COMMENT '标签id',
    name        VARCHAR(255)        NOT NULL COMMENT '文章标签',
    status      INTEGER             NOT NULL DEFAULT 1 comment '0表示失效，1表示生效',
    create_time TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章标签创建时间，默认为当前时间',
    update_time TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '文章标签更新时间，默认为当前时间',
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

# 分类
create table category
(
    id          INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '分类id',
    name        VARCHAR(255)        NOT NULL COMMENT '文章分类',
    status      INTEGER             NOT NULL DEFAULT 1 COMMENT '0表示失效，1表示生效',
    create_time TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章分类创建时间，默认为当前时间',
    update_time TIMESTAMP           NOT NULL Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '文章分类更新时间，默认为当前时间',
    UNIQUE (name)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci