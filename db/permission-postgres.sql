DROP TABLE IF EXISTS "permission"."sys_user_role";

DROP TABLE IF EXISTS "permission"."sys_user";

DROP TABLE IF EXISTS "permission"."sys_role_permission";

DROP TABLE IF EXISTS "permission"."sys_role";

DROP TABLE IF EXISTS "permission"."sys_permission_operation";

DROP TABLE IF EXISTS "permission"."sys_permission";

DROP TABLE IF EXISTS "permission"."sys_operation";

DROP TABLE IF EXISTS "permission"."sys_menu";

CREATE TABLE "permission"."sys_menu" (
  "menuId" bigserial NOT NULL,
  "parentId" integer,
  "menuName" varchar(100),
  "menuIcon" varchar(30),
  "menuUrl" varchar(100),
  "menuType" varchar(10),
  "menuOrder" varchar(10),
  "menuStatus" varchar(10),
  CONSTRAINT "pk_menuId" PRIMARY KEY ("menuId")
);
COMMENT ON COLUMN "permission"."sys_menu"."menuId" IS '菜单Id';
COMMENT ON COLUMN "permission"."sys_menu"."parentId" IS '上级Id';
COMMENT ON COLUMN "permission"."sys_menu"."menuName" IS '菜单名称';
COMMENT ON COLUMN "permission"."sys_menu"."menuIcon" IS '菜单图标';
COMMENT ON COLUMN "permission"."sys_menu"."menuUrl" IS '菜单链接';
COMMENT ON COLUMN "permission"."sys_menu"."menuType" IS '菜单类型';
COMMENT ON COLUMN "permission"."sys_menu"."menuOrder" IS '菜单排序';
COMMENT ON COLUMN "permission"."sys_menu"."menuStatus" IS '菜单状态';


/*Data for the table "sys_menu" */

insert  into "permission"."sys_menu"("menuId","parentId","menuName","menuIcon","menuUrl","menuType","menuOrder","menuStatus") values (1,0,'用户管理','&#xe610','#','1','1','1'),(2,1,'用户管理','&#xe604','user/queryAll','2','2','1'),(3,1,'用户统计','&#xe604','#','2','3','1'),(4,0,'在线管理','&#xe610','#','1','4','1'),(5,4,'在线情况','&#xe604','#','2','5','1'),(6,4,'在线聊天','&#xe604','live/chat/list','2','6','1'),(7,0,'系统管理','&#xe610','#','1','7','1'),(8,7,'角色管理','&#xe604','role/queryAll','2','8','1'),(9,7,'权限管理','&#xe604','permission/queryAll','2','9','1'),(10,7,'菜单管理','&#xe604','menu/getMenus','2','10','1'),(11,0,'系统监控','&#xe610','druid/index.html','1','11','1'),(12,11,'Druid监控','&#xe610','druid/index.html','1','12','1'),(13,11,'SwaggerUI','&#xe610','swagger-ui.html','1','13','1'),(14,1,'个人信息','&#xe610','#','1','14','1');

/*Table structure for table "sys_operation" */

CREATE TABLE "permission"."sys_operation" (
  "id" bigserial NOT NULL,
  "odesc" varchar(100),
  "name" varchar(100),
  "operation" varchar(100),
	"menuId" integer,
	CONSTRAINT "pk_operation_id" PRIMARY KEY ("id"),
	CONSTRAINT "uk_operation_operation" UNIQUE ("operation")
);
COMMENT ON COLUMN "permission"."sys_operation"."id" IS '操作Id，主键';
COMMENT ON COLUMN "permission"."sys_operation"."odesc" IS '操作描述';
COMMENT ON COLUMN "permission"."sys_operation"."name" IS '操作名称';
COMMENT ON COLUMN "permission"."sys_operation"."operation" IS '操作标志';

/*Data for the table "sys_operation" */

insert  into "permission"."sys_operation"("id","odesc","name","operation") values (1,'创建操作','创建','create'),(2,'编辑权限','编辑','edit'),(3,'删除权限','删除','delete'),(4,'浏览权限','浏览','view');

/*Table structure for table "sys_permission" */

CREATE TABLE "permission"."sys_permission" (
  "id" bigserial NOT NULL,
  "pdesc" varchar(100),
  "name" varchar(100),
  "menuId" integer,
  CONSTRAINT "pk_permission_id" PRIMARY KEY ("id"),
	CONSTRAINT "fk_permission_menuId" FOREIGN KEY ("menuId") REFERENCES "permission"."sys_menu" ("menuId")
);
COMMENT ON COLUMN "permission"."sys_permission"."id" IS '权限Id';
COMMENT ON COLUMN "permission"."sys_permission"."pdesc" IS '权限描述';
COMMENT ON COLUMN "permission"."sys_permission"."name" IS '权限名称';
COMMENT ON COLUMN "permission"."sys_permission"."menuId" IS '菜单Id';

/*Data for the table "sys_permission" */

insert  into "permission"."sys_permission"("id","pdesc","name","menuId") values (1,'用户管理的权限','用户管理',1),(2,'管理员管理的权限','管理员管理',2),(3,'用户统计的权限','用户统计',3),(4,'在线管理的权限','在线管理',4),(5,'在线情况的权限','在线情况',5),(6,'在线聊天的权限','在线聊天',6),(7,'系统管理的权限','系统管理',7),(8,'角色管理的权限','角色管理',8),(9,'权限管理的权限','权限管理',9),(10,'菜单管理的权限','菜单管理',10),(11,'平台资料的权限','平台资料',11),(12,'Druid监控的权限','Druid监控的权限',12),(13,'SwaggerUI','SwaggerUI',13),(14,'个人信息的权限','个人信息的权限',14);

/*Table structure for table "sys_permission_operation" */

CREATE TABLE "permission"."sys_permission_operation" (
  "permissionId" integer NOT NULL,
  "operationId" integer NOT NULL,
	CONSTRAINT "pk_permission_operation_permissionId" PRIMARY KEY ("permissionId"),
  CONSTRAINT "fk_permission_operation_operationId" FOREIGN KEY ("operationId") REFERENCES "permission"."sys_operation" ("id"),
  CONSTRAINT "fk_permission_operation_permissionId" FOREIGN KEY ("permissionId") REFERENCES "permission"."sys_permission" ("id")
);
COMMENT ON COLUMN "permission"."sys_permission_operation"."permissionId" IS '权限Id';
COMMENT ON COLUMN "permission"."sys_permission_operation"."operationId" IS '操作Id';


/*Data for the table "sys_permission_operation" */

insert  into "permission"."sys_permission_operation"("permissionId","operationId") values (2,2),(3,3);

/*Table structure for table "sys_role" */

CREATE TABLE "permission"."sys_role" (
  "roleId" bigserial,
  "roleName" varchar(100),
  "roleDesc" varchar(100),
  "role" varchar(100),
	CONSTRAINT "pk_role_roleId" PRIMARY KEY ("roleId")
);
COMMENT ON COLUMN "permission"."sys_role"."roleId" IS '角色Id';
COMMENT ON COLUMN "permission"."sys_role"."roleName" IS '角色名称';
COMMENT ON COLUMN "permission"."sys_role"."roleDesc" IS '角色描述';
COMMENT ON COLUMN "permission"."sys_role"."role" IS '角色标志';


/*Data for the table "sys_role" */

insert  into "permission"."sys_role"("roleId","roleName","roleDesc","role") values (1,'超级管理员','超级管理员拥有所有权限','admin'),(2,'用户管理员','用户管理权限','user'),(3,'角色管理员','角色管理权限','role'),(4,'资源管理员','资源管理权限','role'),(6,'操作权限管理员','操作权限管理','role'),(7,'查看员','查看系统权限','role'),(9,'用户','可以查看','role');

/*Table structure for table "sys_role_permission" */

CREATE TABLE "permission"."sys_role_permission" (
  "rpId" varchar(12) NOT NULL,
  "roleId" integer NOT NULL,
  "permissionId" integer NOT NULL,
	CONSTRAINT "pk_role_permission_rpId" PRIMARY KEY ("rpId"),
  CONSTRAINT "fk_role_permission_roleId" FOREIGN KEY ("roleId") REFERENCES "permission"."sys_role" ("roleId"),
  CONSTRAINT "fk_role_permission_permissionId" FOREIGN KEY ("permissionId") REFERENCES "permission"."sys_permission" ("id")
);
COMMENT ON COLUMN "permission"."sys_role_permission"."rpId" IS '表Id';
COMMENT ON COLUMN "permission"."sys_role_permission"."roleId" IS '角色Id';
COMMENT ON COLUMN "permission"."sys_role_permission"."permissionId" IS '权限Id';

/*Data for the table "sys_role_permission" */

insert  into "permission"."sys_role_permission"("rpId","roleId","permissionId") values ('02a97146f6f4',2,1),('346def9fe9d6',1,12),('3bdce30473ca',1,10),('47c998f93395',1,6),('4a1834ad6e17',1,7),('4b76f155fd74',9,1),('4ccc2dda4892',1,8),('547008dd157a',1,2),('55eb164457e2',9,2),('59084a9f6914',2,2),('5eb035bc97b2',1,11),('69171de938a6',1,5),('6a93a8e6e94d',1,3),('6dde43386281',1,4),('8adc5d180670',1,13),('9fa9725142c1',2,3),('b1729374cea8',1,9),('ccb535546f97',1,1);

/*Table structure for table "sys_user" */

CREATE TABLE "permission"."sys_user" (
  "id" bigserial NOT NULL,
  "username" varchar(100) NOT NULL,
  "password" varchar(100) NOT NULL,
  "phone" varchar(11),
  "sex" varchar(6),
  "email" varchar(100),
  "mark" varchar(100),
  "rank" varchar(10),
  "lastLogin" date,
  "loginIp" varchar(30),
  "imageUrl" varchar(100),
  "regTime" date NOT NULL,
  "locked" bool,
  "rights" varchar(100),
  CONSTRAINT "pk_user_id" PRIMARY KEY ("id"),
	CONSTRAINT "uk_user_username" UNIQUE ("username")
);
COMMENT ON COLUMN "permission"."sys_user"."id" IS '用户Id';
COMMENT ON COLUMN "permission"."sys_user"."username" IS '用户名';
COMMENT ON COLUMN "permission"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "permission"."sys_user"."phone" IS '手机';
COMMENT ON COLUMN "permission"."sys_user"."sex" IS '性别';
COMMENT ON COLUMN "permission"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "permission"."sys_user"."mark" IS '备注';
COMMENT ON COLUMN "permission"."sys_user"."rank" IS '账号等级';
COMMENT ON COLUMN "permission"."sys_user"."lastLogin" IS '最后一次登录时间';
COMMENT ON COLUMN "permission"."sys_user"."loginIp" IS '登录ip';
COMMENT ON COLUMN "permission"."sys_user"."imageUrl" IS '头像图片路径';
COMMENT ON COLUMN "permission"."sys_user"."regTime" IS '注册时间';
COMMENT ON COLUMN "permission"."sys_user"."locked" IS '账号是否被锁定';
COMMENT ON COLUMN "permission"."sys_user"."rights" IS '权限（没有使用）';

/*Data for the table "sys_user" */

insert  into "permission"."sys_user"("id","username","password","phone","sex","email","mark","rank","lastLogin","loginIp","imageUrl","regTime","locked","rights") values (1,'admin','28dca2a7b33b7413ad3bce1d58c26dd679c799f1','1552323312','男','313222@foxmail.com','超级管理员','admin','2017-08-12','0:0:0:0:0:0:0:1','/static/images/','2017-03-15','t',NULL),(2,'sys','e68feeafe796b666a2e21089eb7aae9c678bf82d','1552323312','男','313222@foxmail.com','系统管理员','sys','2017-08-25','127.0.0.1','/static/images/','2017-03-15','t',NULL),(3,'user','adf8e0d0828bde6e90c2bab72e7a2a763d88a0de','1552323312','男','313222@foxmail.com','用户','user','2017-08-18','127.0.0.1','/static/images/','2017-03-15','t',NULL),(4,'test','123','12332233212','保密','2312@qq.com','没有备注','user','2017-11-25','127.0.0.1',NULL,'2017-11-25','t',NULL);

/*Table structure for table "sys_user_role" */


CREATE TABLE "permission"."sys_user_role" (
  "userId" integer,
  "roleId" integer,
	CONSTRAINT "pk_userId_roleId" PRIMARY KEY ("userId","roleId"),
  CONSTRAINT "fk_user_role_userId" FOREIGN KEY ("userId") REFERENCES "permission"."sys_user" ("id"),
  CONSTRAINT "fk_user_role_roleId" FOREIGN KEY ("roleId") REFERENCES "permission"."sys_role" ("roleId")
);
COMMENT ON COLUMN "permission"."sys_user_role"."userId" IS '用户Id,联合主键';
COMMENT ON COLUMN "permission"."sys_user_role"."roleId" IS '角色Id，联合主键';

/*Data for the table "sys_user_role" */

insert  into "permission"."sys_user_role"("userId","roleId") values (1,1),(1,2),(2,2),(1,3),(2,3),(3,3),(1,4),(3,4),(1,6),(1,7),(3,7),(1,9),(4,9);
