create table if not exists HITS
(
    ID_HIT   bigint generated by default as identity not null,
    APP       varchar(255)                            not null,
    URI       varchar(512)                            not null,
    IP        varchar(512)                            not null,
    TIMESTAMP timestamp without time zone,
    constraint PK_USERS primary key (ID_HIT)
);