create schema if not exists merrycodes collate utf8mb4_unicode_ci;

use merrycodes;

create table if not exists article
(
    id              int auto_increment comment '文章id'
        primary key,
    title           varchar(255)                         not null comment '文章标题',
    tags            varchar(255)                         not null comment '文章标签',
    category        varchar(255)                         not null comment '文章分类',
    html_content    longtext                             not null comment '文章内容-HTML格式',
    md_content      longtext                             not null comment '文章内容-Markdown格式',
    summary_content longtext                             not null comment '文章概括内容-HTML格式',
    browse          int        default 0                 not null comment '文章浏览量',
    status          tinyint(3) default 1                 not null comment '0表示草稿，1表示已发布，2表示取消发布',
    allow_comment   tinyint(1) default 1                 not null comment '文章是否可以评论，默认为true',
    create_time     timestamp  default CURRENT_TIMESTAMP not null comment '文章创建时间，默认为当前时间',
    update_time     timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '文章更新时间，默认为当前时间',
    constraint title
        unique (title)
);

create table if not exists category
(
    id          int auto_increment comment '分类id'
        primary key,
    name        varchar(255)                         not null comment '文章分类',
    status      tinyint(2) default 1                 not null comment '0表示失效，1表示生效',
    create_time timestamp  default CURRENT_TIMESTAMP not null comment '文章分类创建时间，默认为当前时间',
    update_time timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '文章分类更新时间，默认为当前时间',
    constraint name
        unique (name)
);

create table if not exists role
(
    id          int auto_increment comment '角色id'
        primary key,
    name        varchar(255)                           not null comment '角色名称',
    description varchar(255)                           not null comment '角色描述',
    create_by   varchar(255) default 'SYSTEM'          not null comment '角色创建者',
    update_by   varchar(255) default 'SYSTEM'          not null comment '角色更新者',
    create_time timestamp    default CURRENT_TIMESTAMP not null comment '角色创建时间，默认为当前时间',
    update_time timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '角色更新时间，默认为当前时间',
    constraint name
        unique (name)
);

create table if not exists setting
(
    id            int auto_increment comment '网站设置id'
        primary key,
    setting_key   varchar(255)                        not null comment '网站设置key',
    setting_value varchar(255)                        not null comment '网站设置value',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '网站设置创建时间，默认为当前时间',
    update_time   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '网站设置更新时间，默认为当前时间',
    constraint option_key
        unique (setting_key)
);

create table if not exists tags
(
    id          int auto_increment comment '标签id'
        primary key,
    name        varchar(255)                         not null comment '文章标签',
    status      tinyint(2) default 1                 not null comment '0表示失效，1表示生效',
    create_time timestamp  default CURRENT_TIMESTAMP not null comment '文章标签创建时间，默认为当前时间',
    update_time timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '文章标签更新时间，默认为当前时间',
    constraint name
        unique (name)
);

create table if not exists user
(
    id              int auto_increment comment '用户id'
        primary key,
    username        varchar(255)                           not null comment '用户名',
    password        varchar(255)                           not null comment '用户密码',
    enabled         tinyint(1)   default 1                 not null comment '用户账号是否可用',
    create_by       varchar(255) default 'SYSTEM'          not null comment '用户创建者',
    update_by       varchar(255) default 'SYSTEM'          not null comment '用户更新者',
    last_login_time timestamp                              null comment '用户最后一次登录时间',
    create_time     timestamp    default CURRENT_TIMESTAMP not null comment '用户创建时间，默认为当前时间',
    update_time     timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '用户更新时间，默认为当前时间',
    constraint username
        unique (username)
);

create table if not exists user_role
(
    id          int auto_increment comment '用户角色id'
        primary key,
    user_id     int                                    not null comment '用户id',
    role_id     int                                    not null comment '角色id',
    create_by   varchar(255) default 'SYSTEM'          not null comment '用户角色创建者',
    update_by   varchar(255) default 'SYSTEM'          not null comment '用户角色更新者',
    create_time timestamp    default CURRENT_TIMESTAMP not null comment '用户角色创建时间，默认为当前时间',
    update_time timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '用户角色更新时间，默认为当前时间'
);

