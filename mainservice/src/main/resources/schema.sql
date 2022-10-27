create table if not exists USERS
(
    ID_USER   bigint generated by default as identity not null,
    NAME_USER varchar(255)                            not null,
    EMAIL     varchar(512)                            not null,
    constraint PK_USERS primary key (ID_USER),
    constraint UQ_USERS_EMAIL unique (EMAIL)
);

create table if not exists CATEGORIES
(
    ID_CATEGORY   bigint generated by default as identity not null,
    NAME_CATEGORY varchar(255)                            not null,
    constraint PK_CATEGORIES primary key (ID_CATEGORY),
    constraint UQ_CATEGORIES_NAME_CATEGORY unique (NAME_CATEGORY)
);

create table if not exists LOCATIONS
(
    ID_LOCATION   bigint generated by default as identity not null,
    LAT           float                                   not null,
    LON           float                                   not null,
    constraint PK_LOCATIONS primary key (ID_LOCATION)
);

create table if not exists EVENTS
(
    ID_EVENT           bigint generated by default as identity not null,
    ANNOTATION         varchar(2000)                           not null,
    ID_CATEGORY        bigint                                  not null,
    DESCRIPTION        varchar(7000)                           not null,
    EVENT_DATE         timestamp without time zone,
    CREATED            timestamp without time zone,
    PUBLISHED          timestamp without time zone,
    ID_INITIATOR       bigint                                  not null,
    ID_LOCATION        bigint                                  not null,
    PAID               boolean                                 not null,
    PARTICIPANT_LIMIT  int                                     not null,
    REQUEST_MODERATION boolean                                 not null,
    STATE              bigint                                  not null,
    TITLE              varchar(120)                            not null,
    VIEWS              bigint,
    constraint PK_EVENTS primary key (ID_EVENT),
    constraint FK_EVENT_CATEGORIES_ID_CATEGORY foreign key (ID_CATEGORY)
        references CATEGORIES on delete cascade,
    constraint FK_EVENT_LOCATIONS_ID_LOCATION foreign key (ID_LOCATION)
        references LOCATIONS on delete cascade
);

create table if not exists COMPILATIONS
(
    ID_COMPILATION  bigint generated by default as identity not null,
    PINNED          boolean                                 not null,
    TITLE           varchar(120)                            not null,
    constraint PK_COMPILATIONS primary key (ID_COMPILATION)
);

create table if not exists EVENT_COMPILATIONS
(
    ID_EVENT        bigint references EVENTS (ID_EVENT)             not null,
    ID_COMPILATION  bigint references COMPILATIONS (ID_COMPILATION) not null,
    constraint PK_COMPILATION_EVENTS primary key (ID_EVENT, ID_COMPILATION)
);

create table if not exists REQUESTS
(
    ID_REQUEST   bigint generated by default as identity not null,
    CREATED      timestamp without time zone             not null,
    ID_EVENT     bigint                                  not null,
    REQUESTER    bigint                                  not null,
    STATUS       bigint                                  not null,
    constraint PK_REQUESTS primary key (ID_REQUEST),
    constraint FK_REQUEST_EVENTS_ID_EVENT foreign key (ID_EVENT)
        references EVENTS on delete cascade,
    constraint FK_REQUEST_USERS_REQUESTER foreign key (REQUESTER)
        references USERS on delete cascade
);