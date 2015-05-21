create table operateRecord(
    id Integer not null  AUTO_INCREMENT,
	menu			varchar(16)		default '',
	nickName		varchar(10)		default '',
	opt             varchar(10)       default '',
	optTime		    varchar(60)		default '',
	optResult       varchar(4)      default '',
	optNumber		varchar(10)     default '',
	optName         varchar(16)     default '',
	beforeOpt       varchar(20)     default '',
	afterOpt        varchar(20)     default '',
	primary key (id)
);