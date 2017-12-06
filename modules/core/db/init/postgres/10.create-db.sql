-- begin LIBRARY_AUTHOR
create table LIBRARY_AUTHOR (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FIRST_NAME varchar(50),
    MIDDLE_NAME varchar(50),
    LAST_NAME varchar(50) not null,
    --
    primary key (ID)
)^
-- end LIBRARY_AUTHOR
-- begin LIBRARY_TOWN
create table LIBRARY_TOWN (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(50) not null,
    --
    primary key (ID)
)^
-- end LIBRARY_TOWN
-- begin LIBRARY_PUBLISHER
create table LIBRARY_PUBLISHER (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(50) not null,
    --
    primary key (ID)
)^
-- end LIBRARY_PUBLISHER
-- begin LIBRARY_LITERATURE_TYPE
create table LIBRARY_LITERATURE_TYPE (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(50) not null,
    --
    primary key (ID)
)^
-- end LIBRARY_LITERATURE_TYPE
-- begin LIBRARY_LIBRARY_DEPARTMENT
create table LIBRARY_LIBRARY_DEPARTMENT (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(50) not null,
    --
    primary key (ID)
)^
-- end LIBRARY_LIBRARY_DEPARTMENT
-- begin LIBRARY_BOOK
create table LIBRARY_BOOK (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(100) not null,
    LITERATURE_TYPE_ID uuid not null,
    --
    primary key (ID)
)^
-- end LIBRARY_BOOK
-- begin LIBRARY_BOOK_PUBLICATION
create table LIBRARY_BOOK_PUBLICATION (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    YEAR_ integer not null,
    BOOK_ID uuid not null,
    PUBLISHER_ID uuid not null,
    TOWN_ID uuid,
    --
    primary key (ID)
)^
-- end LIBRARY_BOOK_PUBLICATION
-- begin LIBRARY_BOOK_INSTANCE
create table LIBRARY_BOOK_INSTANCE (
    ID uuid not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IS_REFERENCE boolean,
    INVENTORY_NUMBER bigint,
    BOOK_PUBLICATION_ID uuid,
    LIBRARY_DEPARTMENT_ID uuid,
    --
    primary key (ID)
)^
-- end LIBRARY_BOOK_INSTANCE
-- begin LIBRARY_BOOK_AUTHOR_LINK
create table LIBRARY_BOOK_AUTHOR_LINK (
    BOOK_ID uuid,
    AUTHOR_ID uuid,
    primary key (BOOK_ID, AUTHOR_ID) )^

-- end LIBRARY_BOOK_AUTHOR_LINK
