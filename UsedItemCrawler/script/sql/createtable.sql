drop sequence seq_used_item_pk;

create sequence seq_used_item_pk start with 1;

drop sequence seq_category_pk;

create sequence seq_category_pk start with 1;

create table used_item (
	item_unique_key		integer primary key default nextval( 'seq_used_item_pk' ),
	hashcode		integer default 0,
	title			varchar(512),
	keywords		text,
	image_url		varchar(512),
	org_price		varchar(50),
	cur_price		varchar(50),
	seller			varchar(512),
	update_datetime		timestamp without time zone,
	create_datetime		timestamp without time zone
);

create table used_item_category (
	category_unique_key	integer primary key default nextval( 'seq_category_pk' ),
	title			varchar(512),
	keywords		text,
	related_url		varchar(512),
	site			varchar(256),
	update_datetime		timestamp without time zone,
	create_datetime		timestamp without time zone
);


