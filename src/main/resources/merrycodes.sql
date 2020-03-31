use merrycodes;

create table article
(
    id              INTEGER primary key not null auto_increment comment '文章id',
    title           varchar(255)    not null comment '文章标题',
    tags            varchar(255)    not null comment '文章标签',
    category        varchar(255)    not null comment '文章分类',
    html_content    mediumtext      not null comment '文章内容-HTML格式',
    md_content      mediumtext      not null comment '文章内容-Markdown格式',
    summary_content mediumtext      not null comment '文章概括内容-HTML格式',
    browse          INTEGER             not null default 0 comment '文章浏览量',
    status          INTEGER         not null default 1 comment '0表示草稿，1表示已发布，2表示取消发布',
    create_time     timestamp       not null default current_timestamp comment '文章创建时间，默认为当前时间',
    update_time     timestamp       not null default current_timestamp on update current_timestamp comment '文章更新时间，默认为当前时间',
    unique (title)
) engine = InnoDB
  default charset = utf8